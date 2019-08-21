package io.renren.modules.group.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

import io.renren.common.utils.OrderUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.utils.SendSms;
import io.renren.modules.group.entity.ClassGroupLogEntity;
import io.renren.modules.group.service.ClassGroupLogConfirmedService;
import io.renren.modules.setting.entity.BaseTurnEntity;
import io.renren.modules.setting.service.BaseTurnService;
import io.renren.modules.sys.entity.NewsEntity;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.DeviceExceptionService;
import io.renren.modules.sys.service.NewsService;
import io.renren.modules.sys.service.SysDeptService;
import io.renren.modules.sys.service.SysUserService;

/**
 * 班组日志(待确认)
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-05-05 16:24:44
 */
@RestController
@RequestMapping("group/classgrouplogconfirmed")
public class ClassGroupLogConfirmedController {
	
	@Autowired
	private NewsService newsService;
	
	@Autowired
	private ClassGroupLogConfirmedService classGroupLogConfirmedService;
	
	@Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private BaseTurnService baseTurnService;
    
    @Autowired
	private SysUserService sysUserService;
	
	@Autowired
    private DeviceExceptionService deviceExceptionService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("group:classgrouplogconfirmed:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = classGroupLogConfirmedService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{classId}")
    @RequiresPermissions("group:classgrouplogconfirmed:info")
    public R info(@PathVariable("classId") Integer classId){
		ClassGroupLogEntity classGroupLog = classGroupLogConfirmedService.selectById(classId);
		// 获取部门(车间工段) 名称
		SysDeptEntity sysDeptEntity = sysDeptService.selectById(classGroupLog.getDeptId());
		classGroupLog.setDeptName(sysDeptEntity.getName());

		BaseTurnEntity baseTurn = baseTurnService.selectById(classGroupLog.getBaseTurnId());
		classGroupLog.setBaseTurnName(baseTurn.getName());
		
        return R.ok().put("classgrouplog", classGroupLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("group:classgrouplogconfirmed:save")
    public R save(@RequestBody ClassGroupLogEntity classGroupLog){
    	classGroupLogConfirmedService.insert(classGroupLog);
    	
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("group:classgrouplogconfirmed:update")
    public R update(@RequestBody ClassGroupLogEntity classGroupLog){
    	if(classGroupLog.getLogType().equals("1")) { // 班长日志
    		if(classGroupLog.getLogStatus().equals("3")) { // 已确认
        		NewsEntity newEntity = new NewsEntity();
        		newEntity.setNewsName("您有一条待确认班长日志");
        		newEntity.setNewsNumber(classGroupLog.getLogNumber());
        		newEntity.setNewsType(0);
        		newEntity.setUserId(classGroupLog.getSuccessorId()); 
        		newEntity.setUpdateTime(new Date());
        		newsService.update(newEntity, new EntityWrapper<NewsEntity>().eq("news_number", classGroupLog.getLogNumber()));
        		
        		SysUserEntity userEntity = sysUserService.selectById(classGroupLog.getHandoverPersonId());
	    		if(!"".equals(userEntity.getMobile())) {
	    			
	    			JSONObject returnJson = new JSONObject();
	    			returnJson.put("news_name", "您有一条已确认的班长日志");
	    			returnJson.put("news_number", classGroupLog.getLogNumber());
	    			
	    			String isOk = SendSms.ordersend(userEntity.getMobile(), returnJson);
	    			if(isOk.equals("ok")) { //发生成功
	    				HashMap<String,Object> map = new HashMap<String,Object>(); 
	    				map.put("isOk", 1);
	    				map.put("phone", userEntity.getMobile());
	    				map.put("content", "您有一条已确认的班长日志");
	    				map.put("type", 3);
	    				map.put("createTiem", new Date());
	    				deviceExceptionService.insertSms(map); // 发送短信记录
	    			}else {
	    				HashMap<String,Object> map = new HashMap<String,Object>(); 
	    				map.put("isOk", 0);
	    				map.put("phone", userEntity.getMobile());
	    				map.put("content", "您有一条已确认的班长日志");
	    				map.put("type", 3);
	    				map.put("createTiem", new Date());
	    				deviceExceptionService.insertSms(map); // 发送短信记录
	    			}
	    			
	    		}
        		
    		}else if(classGroupLog.getLogStatus().equals("4")) { // 已驳回
        		// 查询之前是否有 驳回记录  ，没有就添加，有就修改 并且把 待确认状态的 通知 改为 无效状态
        		NewsEntity news= newsService.selectOne(new EntityWrapper<NewsEntity>()
    					.eq("news_type", 2)
    					.eq("news_name", "您有一条驳回的班长日志")
    					.eq("user_id", classGroupLog.getHandoverPersonId())
    					.eq("news_number", classGroupLog.getLogNumber()));
        		if(news == null) {
        			NewsEntity newEntity = new NewsEntity();
            		newEntity.setNewsName("您有一条驳回的班长日志");
            		newEntity.setNewsNumber(classGroupLog.getLogNumber());
            		newEntity.setNewsType(2);
            		newEntity.setUserId(classGroupLog.getHandoverPersonId()); 
            		newEntity.setCreateTime(new Date());
            		newEntity.setUpdateTime(new Date()); 
            		newsService.insertOrUpdate(newEntity);

        		}else {
        			NewsEntity newEntity = new NewsEntity();
        			newEntity.setUpdateTime(new Date()); 
        			newsService.update(newEntity, new EntityWrapper<NewsEntity>()
        					.eq("news_type", 2)
        					.eq("news_name", "您有一条驳回的班长日志")
        					.eq("user_id", classGroupLog.getHandoverPersonId())
        					.eq("news_number", classGroupLog.getLogNumber()));
        		}

        		NewsEntity newEntity = new NewsEntity();
				newEntity.setNewsType(0);
				newEntity.setUpdateTime(new Date());
				newsService.update(newEntity,
						new EntityWrapper<NewsEntity>()
						        .eq("news_type", 1)
								.eq("news_number", classGroupLog.getLogNumber())
								.eq("user_id", classGroupLog.getSuccessorId())); 
				
				SysUserEntity userEntity = sysUserService.selectById(classGroupLog.getHandoverPersonId()); 
	    		if(!"".equals(userEntity.getMobile())) {
	    			
	    			JSONObject returnJson = new JSONObject();
	    			returnJson.put("news_name", "您有一条驳回的班长日志");
	    			returnJson.put("news_number", classGroupLog.getLogNumber());
	    			
	    			String isOk = SendSms.ordersend(userEntity.getMobile(), returnJson);
	    			if(isOk.equals("ok")) { //发生成功
	    				HashMap<String,Object> map = new HashMap<String,Object>(); 
	    				map.put("isOk", 1);
	    				map.put("phone", userEntity.getMobile());
	    				map.put("content", "您有一条驳回的班长日志");
	    				map.put("type", 3);
	    				map.put("createTiem", new Date());
	    				deviceExceptionService.insertSms(map); // 发送短信记录
	    			}else {
	    				HashMap<String,Object> map = new HashMap<String,Object>(); 
	    				map.put("isOk", 0);
	    				map.put("phone", userEntity.getMobile());
	    				map.put("content", "您有一条驳回的班长日志");
	    				map.put("type", 3);
	    				map.put("createTiem", new Date());
	    				deviceExceptionService.insertSms(map); // 发送短信记录
	    			}
	    			
	    		}
        	}
    	}else if(classGroupLog.getLogType().equals("2")) { // 班前日志
    		if(classGroupLog.getLogStatus().equals("3")) { // 已确认   都确认完的情况下 才能进行修改
    			List<NewsEntity> list = newsService.selectList(new EntityWrapper<NewsEntity>().eq("news_number", classGroupLog.getLogNumber()).eq("news_type", "1"));
    			if(list.size()>1) { // 没有确认完，还是 待确认状态
    				classGroupLog.setLogStatus("2");
    			}else {
    				// 有驳回的情况出现
        			List<NewsEntity> listSize = newsService.selectList(new EntityWrapper<NewsEntity>().eq("news_number", classGroupLog.getLogNumber()).eq("news_type", "13"));
        			if(listSize.size() > 0) {
        				classGroupLog.setLogStatus("2"); // 还是待确认状态
        			}else {
        				classGroupLog.setLogUserStatus("3");
        				// 清理 已生成的 驳回通知
        				NewsEntity newEntity = new NewsEntity();
                		newEntity.setNewsNumber(classGroupLog.getLogNumber());
                		newEntity.setNewsType(0);
                		newEntity.setUpdateTime(new Date());
                		newsService.update(newEntity, 
                				new EntityWrapper<NewsEntity>()
                				.eq("news_number", classGroupLog.getLogNumber())
                				.eq("user_id", classGroupLog.getHandoverPersonId())
                				.eq("news_type", "2")
                				); 
                		SysUserEntity userEntity = sysUserService.selectById(classGroupLog.getHandoverPersonId());
        	    		if(!"".equals(userEntity.getMobile())) {
        	    			
        	    			JSONObject returnJson = new JSONObject();
        	    			returnJson.put("news_name", "您有一条已确认的班前日志");
        	    			returnJson.put("news_number", classGroupLog.getLogNumber());
        	    			
        	    			String isOk = SendSms.ordersend(userEntity.getMobile(), returnJson);
        	    			if(isOk.equals("ok")) { //发生成功
        	    				HashMap<String,Object> map = new HashMap<String,Object>(); 
        	    				map.put("isOk", 1);
        	    				map.put("phone", userEntity.getMobile());
        	    				map.put("content", "您有一条已确认的班前日志");
        	    				map.put("type", 3);
        	    				map.put("createTiem", new Date());
        	    				deviceExceptionService.insertSms(map); // 发送短信记录
        	    			}else {
        	    				HashMap<String,Object> map = new HashMap<String,Object>(); 
        	    				map.put("isOk", 0);
        	    				map.put("phone", userEntity.getMobile());
        	    				map.put("content", "您有一条已确认的班前日志");
        	    				map.put("type", 3);
        	    				map.put("createTiem", new Date());
        	    				deviceExceptionService.insertSms(map); // 发送短信记录
        	    			}
        	    			
        	    		}
                		
                		
        			}
    			}
    			
        		NewsEntity newEntity = new NewsEntity();
        		//newEntity.setNewsName("您有一条待确认班前日志");
        		newEntity.setNewsNumber(classGroupLog.getLogNumber());
        		newEntity.setNewsType(0);
        		//newEntity.setUserId(classGroupLog.getHandoverPersonId()); 
        		newEntity.setUpdateTime(new Date());
        		newsService.update(newEntity, 
        				new EntityWrapper<NewsEntity>()
        				.eq("news_type", "1")
        				.eq("news_number", classGroupLog.getLogNumber())
        				.eq("user_id", classGroupLog.getSuccessorId())); 
        		
        	}else if(classGroupLog.getLogStatus().equals("4")) { // 已驳回
        		
        		List<NewsEntity> list = newsService.selectList(new EntityWrapper<NewsEntity>().eq("news_number", classGroupLog.getLogNumber()).eq("news_type", "1"));
    			if(list.size()>1) { // 班组成员没有确认完，还是 待确认状态
    				classGroupLog.setLogStatus("2"); 
    			}else {
    				classGroupLog.setLogStatus("4"); 
    			}
        		// 通知 交底人 消息被 驳回  (存在就修改 ，不存在 就新增)
    			NewsEntity newsEntity = newsService.selectOne(new EntityWrapper<NewsEntity>()
    					.eq("user_id", classGroupLog.getHandoverPersonId())
    					.eq("news_name", "您有一条被驳回的班前日志")
    					.eq("news_number", classGroupLog.getLogNumber()));
    			if(newsEntity !=null) {
    				newsEntity.setNewsType(2); 
    				newsEntity.setUpdateTime(new Date()); 
    				newsService.updateById(newsEntity);
    			}else {
    				NewsEntity newEntity = new NewsEntity();
            		newEntity.setUserId(classGroupLog.getHandoverPersonId()); 
            		newEntity.setNewsType(2); // 被驳回
            		newEntity.setNewsName("您有一条被驳回的班前日志");
            		newEntity.setNewsNumber(classGroupLog.getLogNumber()); 
            		newEntity.setCreateTime(new Date());
            		newEntity.setUpdateTime(new Date());
            		newsService.insert(newEntity);
    			}
    			NewsEntity Entity = new NewsEntity();
    			Entity.setNewsType(13);
    			Entity.setUpdateTime(new Date());
				newsService.update(Entity,
						new EntityWrapper<NewsEntity>()
						.eq("news_type", "1")
						.eq("news_number", classGroupLog.getLogNumber())
						.eq("user_id", classGroupLog.getSuccessorId()));
				
				SysUserEntity userEntity = sysUserService.selectById(classGroupLog.getHandoverPersonId());
	    		if(!"".equals(userEntity.getMobile())) {
	    			
	    			JSONObject returnJson = new JSONObject();
	    			returnJson.put("news_name", "您有一条被驳回的班前日志");
	    			returnJson.put("news_number", classGroupLog.getLogNumber());
	    			
	    			String isOk = SendSms.ordersend(userEntity.getMobile(), returnJson);
	    			if(isOk.equals("ok")) { //发生成功
	    				HashMap<String,Object> map = new HashMap<String,Object>(); 
	    				map.put("isOk", 1);
	    				map.put("phone", userEntity.getMobile());
	    				map.put("content", "您有一条被驳回的班前日志");
	    				map.put("type", 3);
	    				map.put("createTiem", new Date());
	    				deviceExceptionService.insertSms(map); // 发送短信记录
	    			}else {
	    				HashMap<String,Object> map = new HashMap<String,Object>(); 
	    				map.put("isOk", 0);
	    				map.put("phone", userEntity.getMobile());
	    				map.put("content", "您有一条被驳回的班前日志");
	    				map.put("type", 3);
	    				map.put("createTiem", new Date());
	    				deviceExceptionService.insertSms(map); // 发送短信记录
	    			}
	    			
	    		}
        		
        	}
    	}else if(classGroupLog.getLogType().equals("3")) { // 班后日志
    		if(classGroupLog.getLogStatus().equals("3")){
    			List<NewsEntity> list = newsService.selectList(new EntityWrapper<NewsEntity>().eq("news_number", classGroupLog.getLogNumber()).eq("news_type", "1"));
    			if(list.size()>1) { // 没有确认完，还是 待确认状态
    				classGroupLog.setLogStatus("2");
    			}else {
        			List<NewsEntity> listSize = newsService.selectList(new EntityWrapper<NewsEntity>().eq("news_number", classGroupLog.getLogNumber()).eq("news_type", "13"));
                    if(listSize.size() > 0) { // 有其他人驳回的情况
                    	classGroupLog.setLogStatus("2"); // 还是待确认状态
                    }else {
                    	classGroupLog.setLogUserStatus("3");
        				// 清理 已生成的 驳回通知
        				NewsEntity newEntity = new NewsEntity();
                		newEntity.setNewsNumber(classGroupLog.getLogNumber());
                		newEntity.setNewsType(0);
                		newEntity.setUpdateTime(new Date());
                		newsService.update(newEntity, 
                				new EntityWrapper<NewsEntity>()
                				.eq("news_number", classGroupLog.getLogNumber())
                				.eq("user_id", classGroupLog.getHandoverPersonId())
                				.eq("news_type", "2")
                				); 
                		SysUserEntity userEntity = sysUserService.selectById(classGroupLog.getHandoverPersonId());
        	    		if(!"".equals(userEntity.getMobile())) {
        	    			
        	    			JSONObject returnJson = new JSONObject();
        	    			returnJson.put("news_name", "您有一条已确认的班后日志");
        	    			returnJson.put("news_number", classGroupLog.getLogNumber());
        	    			
        	    			String isOk = SendSms.ordersend(userEntity.getMobile(), returnJson);
        	    			if(isOk.equals("ok")) { //发生成功
        	    				HashMap<String,Object> map = new HashMap<String,Object>(); 
        	    				map.put("isOk", 1);
        	    				map.put("phone", userEntity.getMobile());
        	    				map.put("content", "您有一条已确认的班后日志");
        	    				map.put("type", 3);
        	    				map.put("createTiem", new Date());
        	    				deviceExceptionService.insertSms(map); // 发送短信记录
        	    			}else {
        	    				HashMap<String,Object> map = new HashMap<String,Object>(); 
        	    				map.put("isOk", 0);
        	    				map.put("phone", userEntity.getMobile());
        	    				map.put("content", "您有一条已确认的班后日志");
        	    				map.put("type", 3);
        	    				map.put("createTiem", new Date());
        	    				deviceExceptionService.insertSms(map); // 发送短信记录
        	    			}
        	    			
        	    		}
                    }
    			}
    			NewsEntity newEntity = new NewsEntity();
    			newEntity.setNewsNumber(classGroupLog.getLogNumber());
        		newEntity.setNewsType(0);
        		newEntity.setUpdateTime(new Date());
        		newsService.update(newEntity, new EntityWrapper<NewsEntity>()
        				.eq("news_type", "1")
        				.eq("news_number", classGroupLog.getLogNumber())
        				.eq("user_id", classGroupLog.getSuccessorId()));
    		}else if(classGroupLog.getLogStatus().equals("4")) { // 已驳回
    			
    			List<NewsEntity> list = newsService.selectList(new EntityWrapper<NewsEntity>().eq("news_number", classGroupLog.getLogNumber()).eq("news_type", 1));
    			if(list.size()>1) { // 班组成员没有确认完，还是 待确认状态
    				classGroupLog.setLogStatus("2"); 
    			}else {
    				classGroupLog.setLogStatus("4"); 
    			}
    			
    			// 通知 交班人 消息被 驳回  (存在就修改 ，不存在 就新增)
    			NewsEntity newsEntity = newsService.selectOne(new EntityWrapper<NewsEntity>()
    					.eq("user_id", classGroupLog.getHandoverPersonId())
    					.eq("news_name", "您有一条被驳回的班后日志")
    					.eq("news_number", classGroupLog.getLogNumber()));
    			if(newsEntity !=null) {
    				newsEntity.setNewsType(2); 
    				newsEntity.setUpdateTime(new Date()); 
    				newsService.updateById(newsEntity);
    			}else {
    				NewsEntity newEntity = new NewsEntity();
            		newEntity.setUserId(classGroupLog.getHandoverPersonId()); 
            		newEntity.setNewsType(2); // 被驳回
            		newEntity.setNewsName("您有一条被驳回的班后日志");
            		newEntity.setNewsNumber(classGroupLog.getLogNumber()); 
            		newEntity.setCreateTime(new Date());
            		newEntity.setUpdateTime(new Date());
            		newsService.insert(newEntity);
    			}
    			// 修改 之前的通知 为 无效状态
    			NewsEntity Entity = new NewsEntity();
    			Entity.setNewsType(13);
    			Entity.setUpdateTime(new Date());
				newsService.update(Entity,
						new EntityWrapper<NewsEntity>()
						.eq("news_type", "1")
						.eq("news_number", classGroupLog.getLogNumber())
						.eq("user_id", classGroupLog.getSuccessorId()));
				
				SysUserEntity userEntity = sysUserService.selectById(classGroupLog.getHandoverPersonId());
	    		if(!"".equals(userEntity.getMobile())) {
	    			
	    			JSONObject returnJson = new JSONObject();
	    			returnJson.put("news_name", "您有一条被驳回的班后日志");
	    			returnJson.put("news_number", classGroupLog.getLogNumber());
	    			
	    			String isOk = SendSms.ordersend(userEntity.getMobile(), returnJson);
	    			if(isOk.equals("ok")) { //发生成功
	    				HashMap<String,Object> map = new HashMap<String,Object>(); 
	    				map.put("isOk", 1);
	    				map.put("phone", userEntity.getMobile());
	    				map.put("content", "您有一条被驳回的班后日志");
	    				map.put("type", 3);
	    				map.put("createTiem", new Date());
	    				deviceExceptionService.insertSms(map); // 发送短信记录
	    			}else {
	    				HashMap<String,Object> map = new HashMap<String,Object>(); 
	    				map.put("isOk", 0);
	    				map.put("phone", userEntity.getMobile());
	    				map.put("content", "您有一条被驳回的班后日志");
	    				map.put("type", 3);
	    				map.put("createTiem", new Date());
	    				deviceExceptionService.insertSms(map); // 发送短信记录
	    			}
	    			
	    		}
    			
        	}
    	}
    	classGroupLogConfirmedService.updateById(classGroupLog);
    	
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("group:classgrouplogconfirmed:delete")
    public R delete(@RequestBody Integer[] classIds){
    	classGroupLogConfirmedService.deleteBatchIds(Arrays.asList(classIds));

        return R.ok();
    }
    
    /**
     * 日志编码
     */
    @RequestMapping("/logNumber")
    @RequiresPermissions("group:classgrouplogconfirmed:logNumber")
    public R dataNumber() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyMMdd");
		String newDate=sdf.format(new Date());

		List<ClassGroupLogEntity> list = classGroupLogConfirmedService.selectList(new EntityWrapper<ClassGroupLogEntity>().like("log_number",newDate));

		String logNumber = OrderUtils.orderNumber(list.size());
        
    	return R.ok().put("logNumber", logNumber);
    }
	
	
    /**
     * 去确认 确定
     */
    @RequestMapping("/confirmed")
    @RequiresPermissions("group:classgrouplogconfirmed:confirmed")
	public R conFirmed(@RequestParam Map<String, Object> params) {
		
    	String user_id = params.get("user_id").toString();
    	String news_number = params.get("log_number").toString();
    	 
    	List<NewsEntity> selectList = newsService.selectList(
    			new EntityWrapper<NewsEntity>()
    			.eq("user_id", Integer.parseInt(user_id))
    			.eq("news_number", news_number)
    			.eq("news_type", 1));
    	
    	return R.ok().put("newsCounts", selectList.size()); 
	}
	
}
