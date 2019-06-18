package io.renren.modules.group.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
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

import com.baomidou.mybatisplus.mapper.EntityWrapper;

import io.renren.common.utils.OrderUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.group.entity.ClassGroupLogEntity;
import io.renren.modules.group.service.ClassGroupLogConfirmedService;
import io.renren.modules.sys.entity.NewsEntity;
import io.renren.modules.sys.service.NewsService;

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
	
	
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("group:classgrouplog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = classGroupLogConfirmedService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{classId}")
    @RequiresPermissions("group:classgrouplog:info")
    public R info(@PathVariable("classId") Integer classId){
			ClassGroupLogEntity classGroupLog = classGroupLogConfirmedService.selectById(classId);

        return R.ok().put("classgrouplog", classGroupLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("group:classgrouplog:save")
    public R save(@RequestBody ClassGroupLogEntity classGroupLog){
    	classGroupLogConfirmedService.insert(classGroupLog);
    	
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("group:classgrouplog:update")
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
        	}else if(classGroupLog.getLogStatus().equals("4")) { // 已驳回
        		// 查询之前是否有 驳回记录  ，没有就添加，有就修改
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
        		
        		classGroupLog.setLogStatus("2"); 
        	}
    	}else if(classGroupLog.getLogType().equals("2")) { // 班前日志
    		if(classGroupLog.getLogStatus().equals("3")) { // 已确认   都确认完的情况下 才能进行修改
    			List<NewsEntity> list = newsService.selectList(new EntityWrapper<NewsEntity>().eq("news_number", classGroupLog.getLogNumber()).eq("news_type", "1"));
    			if(list.size()>1) { // 没有确认完，还是 待确认状态
    				classGroupLog.setLogStatus("2");
    			}else {
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
    			}
    			
        		NewsEntity newEntity = new NewsEntity();
        		//newEntity.setNewsName("您有一条待确认班前日志");
        		newEntity.setNewsNumber(classGroupLog.getLogNumber());
        		newEntity.setNewsType(0);
        		//newEntity.setUserId(classGroupLog.getHandoverPersonId()); 
        		newEntity.setUpdateTime(new Date());
        		newsService.update(newEntity, 
        				new EntityWrapper<NewsEntity>()
        				.eq("news_number", classGroupLog.getLogNumber())
        				.eq("user_id", classGroupLog.getSuccessorId())); 
        	}else if(classGroupLog.getLogStatus().equals("4")) { // 已驳回
        		
        		List<NewsEntity> list = newsService.selectList(new EntityWrapper<NewsEntity>().eq("news_number", classGroupLog.getLogNumber()).eq("news_type", "1"));
    			if(list.size()>=1) { // 班组成员没有确认完，还是 待确认状态
    				classGroupLog.setLogStatus("2"); 
    				
    			}
        		// 通知 交底人 消息被 驳回  (存在就修改 ，不存在 就新增)
    			NewsEntity newsEntity = newsService.selectOne(new EntityWrapper<NewsEntity>()
    					//.eq("news_type", 2)
    					.eq("news_name", "您有一条被驳回的班前日志")
    					.eq("user_id", classGroupLog.getHandoverPersonId())
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
        		
        	}
    	}else if(classGroupLog.getLogType().equals("3")) { // 班后日志
    		if(classGroupLog.getLogStatus().equals("3")){
    			List<NewsEntity> list = newsService.selectList(new EntityWrapper<NewsEntity>().eq("news_number", classGroupLog.getLogNumber()).eq("news_type", "1"));
    			if(list.size()>1) { // 没有确认完，还是 待确认状态
    				classGroupLog.setLogStatus("2");
    			}else {
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
    			}
    			NewsEntity newEntity = new NewsEntity();
    			newEntity.setNewsNumber(classGroupLog.getLogNumber());
        		newEntity.setNewsType(0);
        		newEntity.setUpdateTime(new Date());
        		newsService.update(newEntity, new EntityWrapper<NewsEntity>().eq("news_number", classGroupLog.getLogNumber()).eq("user_id", classGroupLog.getSuccessorId()));
    		}else if(classGroupLog.getLogStatus().equals("4")) { // 已驳回
    			
    			List<NewsEntity> list = newsService.selectList(new EntityWrapper<NewsEntity>().eq("news_number", classGroupLog.getLogNumber()).eq("news_type", 1));
    			if(list.size()>=1) { // 班组成员没有确认完，还是 待确认状态
    				classGroupLog.setLogStatus("2"); 
    				
    			}
    			
    			// 通知 交班人 消息被 驳回  (存在就修改 ，不存在 就新增)
    			NewsEntity newsEntity = newsService.selectOne(new EntityWrapper<NewsEntity>()
    					//.eq("news_type", "2")
    					.eq("news_name", "您有一条被驳回的班后日志")
    					.eq("user_id", classGroupLog.getHandoverPersonId())
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
        	}
    	}
    	classGroupLogConfirmedService.updateById(classGroupLog);
    	
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("group:classgrouplog:delete")
    public R delete(@RequestBody Integer[] classIds){
    	classGroupLogConfirmedService.deleteBatchIds(Arrays.asList(classIds));

        return R.ok();
    }
    
    /**
     * 日志编码
     */
    @RequestMapping("/logNumber")
    @RequiresPermissions("group:classgrouplog:logNumber")
    public R dataNumber() {
    	
        String orderNumber = OrderUtils.orderNumber();
        
    	return R.ok().put("logNumber", orderNumber);
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
