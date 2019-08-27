package io.renren.modules.group.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysDeptService;
import io.renren.modules.sys.service.SysUserService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.modules.group.entity.ClassGroupLogEntity;
import io.renren.modules.group.service.ClassGroupLogService;
import io.renren.modules.setting.entity.BaseTurnEntity;
import io.renren.modules.setting.service.BaseTurnService;
import io.renren.modules.sys.entity.NewsEntity;
import io.renren.modules.sys.service.DeviceExceptionService;
import io.renren.modules.sys.service.NewsService;
import io.renren.common.utils.OrderUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.utils.SendSms;
import io.renren.common.utils.TestMessage;



/**
 * 班组日志表
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-05-05 16:24:44
 */
@RestController
@RequestMapping("group/classgrouplog")
public class ClassGroupLogController {
    @Autowired
    private ClassGroupLogService classGroupLogService;
    
    @Autowired
	private NewsService newsService;

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
    @RequiresPermissions("group:classgrouplog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = classGroupLogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{classId}")
    @RequiresPermissions("group:classgrouplog:info")
    public R info(@PathVariable("classId") Integer classId){
			ClassGroupLogEntity classGroupLog = classGroupLogService.selectById(classId);
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
    @RequiresPermissions("group:classgrouplog:save")
    public R save(@RequestBody ClassGroupLogEntity classGroupLog){
    	String logNumber = classGroupLog.getLogNumber();
    	List<ClassGroupLogEntity> list = classGroupLogService.selectList(new EntityWrapper<ClassGroupLogEntity>().eq("log_number", logNumber));
    	if(list.size()>0) { // 不能出现两个 相同的 班组编号
    		logNumber = this.dataNumber().get("logNumber").toString();
            classGroupLog.setLogNumber(logNumber); 
    	}
    	classGroupLogService.insert(classGroupLog);
		if(classGroupLog.getLogType().equals("1") && classGroupLog.getLogStatus().equals("2")) { //班长日志
    		NewsEntity newEntity = new NewsEntity();
    		newEntity.setNewsName("您有一条待确认班长日志");
    		newEntity.setNewsNumber(classGroupLog.getLogNumber());
    		newEntity.setNewsType(1);
    		newEntity.setUserId(classGroupLog.getSuccessorId()); 
    		newEntity.setUpdateTime(new Date());
    		newEntity.setCreateTime(new Date());
    		newsService.insert(newEntity);
    		
    		SysUserEntity userEntity = sysUserService.selectById(classGroupLog.getSuccessorId());
    		if(!"".equals(userEntity.getMobile())) {
    			
    			JSONObject returnJson = new JSONObject();
    			returnJson.put("news_name", "您有一条待确认班长日志");
    			returnJson.put("news_number", classGroupLog.getLogNumber());
    			
    			String isOk = SendSms.ordersend(userEntity.getMobile(), returnJson);
    			if(isOk.equals("ok")) { //发生成功
    				HashMap<String,Object> map = new HashMap<String,Object>(); 
    				map.put("isOk", 1);
    				map.put("phone", userEntity.getMobile());
    				map.put("content", "您有一条待确认班长日志");
    				map.put("type", 3);
    				map.put("createTiem", new Date());
    				deviceExceptionService.insertSms(map); // 发送短信记录
    			}else {
    				HashMap<String,Object> map = new HashMap<String,Object>(); 
    				map.put("isOk", 0);
    				map.put("phone", userEntity.getMobile());
    				map.put("content", "您有一条待确认班长日志");
    				map.put("type", 3);
    				map.put("createTiem", new Date());
    				deviceExceptionService.insertSms(map); // 发送短信记录
    			}
    			
    		}
    		String wechat = userEntity.getWechat();
    		if(!"".equals(wechat)) { 
    			TestMessage.ordersend(wechat, "您有一条待确认班长日志", classGroupLog.getLogNumber()); 
    		}
    		
    	}	
		if(classGroupLog.getLogType().equals("2") && classGroupLog.getLogStatus().equals("2")) { // 班前日志
			String teamMembersIds = classGroupLog.getTeamMembersIds();
			String[] split = teamMembersIds.split(","); // 班组成员用户id
			for(String user_id: split) {
				NewsEntity newEntity = new NewsEntity();
	    		newEntity.setNewsName("您有一条待确认班前日志");
	    		newEntity.setNewsNumber(classGroupLog.getLogNumber());
	    		newEntity.setNewsType(1);
	    		newEntity.setUserId(Integer.parseInt(user_id)); 
	    		newEntity.setUpdateTime(new Date());
	    		newEntity.setCreateTime(new Date());
	    		newsService.insert(newEntity);
	    		
	    		SysUserEntity userEntity = sysUserService.selectById(Integer.parseInt(user_id));
	    		if(!"".equals(userEntity.getMobile())) {
	    			
	    			JSONObject returnJson = new JSONObject();
	    			returnJson.put("news_name", "您有一条待确认班前日志");
	    			returnJson.put("news_number", classGroupLog.getLogNumber());
	    			
	    			String isOk = SendSms.ordersend(userEntity.getMobile(), returnJson);
	    			if(isOk.equals("ok")) { //发生成功
	    				HashMap<String,Object> map = new HashMap<String,Object>(); 
	    				map.put("isOk", 1);
	    				map.put("phone", userEntity.getMobile());
	    				map.put("content", "您有一条待确认班前日志");
	    				map.put("type", 3);
	    				map.put("createTiem", new Date());
	    				deviceExceptionService.insertSms(map); // 发送短信记录
	    			}else {
	    				HashMap<String,Object> map = new HashMap<String,Object>(); 
	    				map.put("isOk", 0);
	    				map.put("phone", userEntity.getMobile());
	    				map.put("content", "您有一条待确认班前日志");
	    				map.put("type", 3);
	    				map.put("createTiem", new Date());
	    				deviceExceptionService.insertSms(map); // 发送短信记录
	    			}
	    			
	    		}
	    		String wechat = userEntity.getWechat();
	    		if(!"".equals(wechat)) { 
	    			TestMessage.ordersend(wechat, "您有一条待确认班前日志", classGroupLog.getLogNumber()); 
	    		}
			}
		} 
		if(classGroupLog.getLogType().equals("3") && classGroupLog.getLogStatus().equals("2")) {  // 班后日志
			String teamMembersIds = classGroupLog.getTeamMembersIds();
			String[] split = teamMembersIds.split(","); // 班组成员用户id
			for(String user_id: split) {
				NewsEntity newEntity = new NewsEntity();
	    		newEntity.setNewsName("您有一条待确认班后日志");
	    		newEntity.setNewsNumber(classGroupLog.getLogNumber());
	    		newEntity.setNewsType(1);
	    		newEntity.setUserId(Integer.parseInt(user_id)); 
	    		newEntity.setUpdateTime(new Date());
	    		newEntity.setCreateTime(new Date());
	    		newsService.insert(newEntity);
	    		
	    		SysUserEntity userEntity = sysUserService.selectById(Integer.parseInt(user_id));
	    		if(!"".equals(userEntity.getMobile())) {
	    			
	    			JSONObject returnJson = new JSONObject();
	    			returnJson.put("news_name", "您有一条待确认班后日志");
	    			returnJson.put("news_number", classGroupLog.getLogNumber());
	    			
	    			String isOk = SendSms.ordersend(userEntity.getMobile(), returnJson);
	    			if(isOk.equals("ok")) { //发生成功
	    				HashMap<String,Object> map = new HashMap<String,Object>(); 
	    				map.put("isOk", 1);
	    				map.put("phone", userEntity.getMobile());
	    				map.put("content", "您有一条待确认班后日志");
	    				map.put("type", 3);
	    				map.put("createTiem", new Date());
	    				deviceExceptionService.insertSms(map); // 发送短信记录
	    			}else {
	    				HashMap<String,Object> map = new HashMap<String,Object>(); 
	    				map.put("isOk", 0);
	    				map.put("phone", userEntity.getMobile());
	    				map.put("content", "您有一条待确认班后日志");
	    				map.put("type", 3);
	    				map.put("createTiem", new Date());
	    				deviceExceptionService.insertSms(map); // 发送短信记录
	    			}
	    			
	    		}
	    		String wechat = userEntity.getWechat();
	    		if(!"".equals(wechat)) { 
	    			TestMessage.ordersend(wechat, "您有一条待确认班后日志", classGroupLog.getLogNumber()); 
	    		}
			}
		}
		
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("group:classgrouplog:update")
    public R update(@RequestBody ClassGroupLogEntity classGroupLog){
		classGroupLogService.updateById(classGroupLog);
		NewsEntity newsEntity = newsService.selectOne(new EntityWrapper<NewsEntity>().eq("news_number", classGroupLog.getLogNumber()).eq("news_type", 1));
		if(classGroupLog.getLogType().equals("1")&& classGroupLog.getLogStatus().equals("2")) { //班长日志
			// 之前没有通知记录，处于 拟制状态 改为 待处理状态
            if(newsEntity !=null){
				
			}else{
				NewsEntity newEntity = new NewsEntity();
				newEntity.setNewsName("您有一条待确认班长日志");
				newEntity.setNewsNumber(classGroupLog.getLogNumber());
				newEntity.setNewsType(1);
				newEntity.setUserId(classGroupLog.getSuccessorId());
				newEntity.setUpdateTime(new Date());
				newEntity.setCreateTime(new Date());
				newsService.insert(newEntity);
				
				SysUserEntity userEntity = sysUserService.selectById(classGroupLog.getSuccessorId());
	    		if(!"".equals(userEntity.getMobile())) {
	    			
	    			JSONObject returnJson = new JSONObject();
	    			returnJson.put("news_name", "您有一条待确认班长日志");
	    			returnJson.put("news_number", classGroupLog.getLogNumber());
	    			
	    			String isOk = SendSms.ordersend(userEntity.getMobile(), returnJson);
	    			if(isOk.equals("ok")) { //发生成功
	    				HashMap<String,Object> map = new HashMap<String,Object>(); 
	    				map.put("isOk", 1);
	    				map.put("phone", userEntity.getMobile());
	    				map.put("content", "您有一条待确认班长日志");
	    				map.put("type", 3);
	    				map.put("createTiem", new Date());
	    				deviceExceptionService.insertSms(map); // 发送短信记录
	    			}else {
	    				HashMap<String,Object> map = new HashMap<String,Object>(); 
	    				map.put("isOk", 0);
	    				map.put("phone", userEntity.getMobile());
	    				map.put("content", "您有一条待确认班长日志");
	    				map.put("type", 3);
	    				map.put("createTiem", new Date());
	    				deviceExceptionService.insertSms(map); // 发送短信记录
	    			}
	    			
	    		}
	    		String wechat = userEntity.getWechat();
	    		if(!"".equals(wechat)) { 
	    			TestMessage.ordersend(wechat, "您有一条待确认班长日志", classGroupLog.getLogNumber()); 
	    		}
				
			}
		}else if(classGroupLog.getLogType().equals("2")&& classGroupLog.getLogStatus().equals("2")) { // 班前日志
			// 之前没有通知记录，处于 拟制状态 改为 待处理状态
            if(newsEntity !=null){
				
			}else{
				String teamMembersIds = classGroupLog.getTeamMembersIds();
				String[] split = teamMembersIds.split(","); // 班组成员用户id
				for(String user_id: split) {
					NewsEntity newEntity = new NewsEntity();
					newEntity.setNewsName("您有一条待确认班前日志");
					newEntity.setNewsNumber(classGroupLog.getLogNumber());
					newEntity.setNewsType(1);
					newEntity.setUserId(Integer.parseInt(user_id));
					newEntity.setUpdateTime(new Date());
					newEntity.setCreateTime(new Date());
					newsService.insert(newEntity);
					
					SysUserEntity userEntity = sysUserService.selectById(Integer.parseInt(user_id));
		    		if(!"".equals(userEntity.getMobile())) {
		    			
		    			JSONObject returnJson = new JSONObject();
		    			returnJson.put("news_name", "您有一条待确认班前日志");
		    			returnJson.put("news_number", classGroupLog.getLogNumber());
		    			
		    			String isOk = SendSms.ordersend(userEntity.getMobile(), returnJson);
		    			if(isOk.equals("ok")) { //发生成功
		    				HashMap<String,Object> map = new HashMap<String,Object>(); 
		    				map.put("isOk", 1);
		    				map.put("phone", userEntity.getMobile());
		    				map.put("content", "您有一条待确认班前日志");
		    				map.put("type", 3);
		    				map.put("createTiem", new Date());
		    				deviceExceptionService.insertSms(map); // 发送短信记录
		    			}else {
		    				HashMap<String,Object> map = new HashMap<String,Object>(); 
		    				map.put("isOk", 0);
		    				map.put("phone", userEntity.getMobile());
		    				map.put("content", "您有一条待确认班前日志");
		    				map.put("type", 3);
		    				map.put("createTiem", new Date());
		    				deviceExceptionService.insertSms(map); // 发送短信记录
		    			}
		    			
		    		}
		    		String wechat = userEntity.getWechat();
		    		if(!"".equals(wechat)) { 
		    			TestMessage.ordersend(wechat, "您有一条待确认班前日志", classGroupLog.getLogNumber()); 
		    		}
		    		
				}
			}
    	}else if(classGroupLog.getLogType().equals("3")&& classGroupLog.getLogStatus().equals("2")) {// 班后日志
			if(newsEntity !=null){
				
			}else {
				String teamMembersIds = classGroupLog.getTeamMembersIds();
				String[] split = teamMembersIds.split(","); // 班组成员用户id
				for(String user_id: split) {
					NewsEntity newEntity = new NewsEntity();
					newEntity.setNewsName("您有一条待确认班后日志");
					newEntity.setNewsNumber(classGroupLog.getLogNumber());
					newEntity.setNewsType(1);
					newEntity.setUserId(Integer.parseInt(user_id));
					newEntity.setUpdateTime(new Date());
					newEntity.setCreateTime(new Date());
					newsService.insert(newEntity);
					
					SysUserEntity userEntity = sysUserService.selectById(Integer.parseInt(user_id));
		    		if(!"".equals(userEntity.getMobile())) {
		    			
		    			JSONObject returnJson = new JSONObject();
		    			returnJson.put("news_name", "您有一条待确认班后日志");
		    			returnJson.put("news_number", classGroupLog.getLogNumber());
		    			
		    			String isOk = SendSms.ordersend(userEntity.getMobile(), returnJson);
		    			if(isOk.equals("ok")) { //发生成功
		    				HashMap<String,Object> map = new HashMap<String,Object>(); 
		    				map.put("isOk", 1);
		    				map.put("phone", userEntity.getMobile());
		    				map.put("content", "您有一条待确认班后日志");
		    				map.put("type", 3);
		    				map.put("createTiem", new Date());
		    				deviceExceptionService.insertSms(map); // 发送短信记录
		    			}else {
		    				HashMap<String,Object> map = new HashMap<String,Object>(); 
		    				map.put("isOk", 0);
		    				map.put("phone", userEntity.getMobile());
		    				map.put("content", "您有一条待确认班后日志");
		    				map.put("type", 3);
		    				map.put("createTiem", new Date());
		    				deviceExceptionService.insertSms(map); // 发送短信记录
		    			}
		    			
		    		}
		    		String wechat = userEntity.getWechat();
		    		if(!"".equals(wechat)) { 
		    			TestMessage.ordersend(wechat, "您有一条待确认班后日志", classGroupLog.getLogNumber()); 
		    		}
				}
			}
    	}	
		
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("group:classgrouplog:delete")
    public R delete(@RequestBody Integer[] classIds){
			classGroupLogService.deleteBatchIds(Arrays.asList(classIds));

        return R.ok();
    }
    
    /**
     * 日志编码
     */
    @RequestMapping("/logNumber")
    @RequiresPermissions("group:classgrouplog:logNumber")
    public R dataNumber() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyMMdd");
		String newDate=sdf.format(new Date());

		List<ClassGroupLogEntity> list = classGroupLogService.selectList(new EntityWrapper<ClassGroupLogEntity>().like("log_number",newDate).orderBy("log_number", false));
        String logNumber =null;
		if(list.size()>0) {
			logNumber = list.get(0).getLogNumber();
			String numStr = logNumber.substring(logNumber.length()-3,logNumber.length());
	        String newStr = numStr.replaceAll("^(0+)", "");
	        Integer num = Integer.parseInt(newStr);
	        logNumber = OrderUtils.orderNumber(num);
        }else {
        	logNumber = OrderUtils.orderNumber(list.size());
        }
    	return R.ok().put("logNumber", logNumber);
    }
    
    /**
     * 查询班前会
     */
    @RequestMapping("/banqianlist")
    @RequiresPermissions("group:classgrouplog:banqianlist")
    public R banqianlist(@RequestParam Map<String, Object> params){
        PageUtils page = classGroupLogService.querybanqianPage(params);

        return R.ok().put("page", page);
    }
    
    
    
}
