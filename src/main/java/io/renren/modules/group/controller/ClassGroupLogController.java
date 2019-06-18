package io.renren.modules.group.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

import io.renren.modules.group.entity.ClassGroupLogEntity;
import io.renren.modules.group.service.ClassGroupLogService;
import io.renren.modules.sys.entity.NewsEntity;
import io.renren.modules.sys.service.NewsService;
import io.renren.common.utils.OrderUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



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

        return R.ok().put("classgrouplog", classGroupLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("group:classgrouplog:save")
    public R save(@RequestBody ClassGroupLogEntity classGroupLog){
			classGroupLogService.insert(classGroupLog);
			
		if(classGroupLog.getLogType().equals("1")) { //班长日志
    		NewsEntity newEntity = new NewsEntity();
    		newEntity.setNewsName("您有一条待确认班长日志");
    		newEntity.setNewsNumber(classGroupLog.getLogNumber());
    		newEntity.setNewsType(1);
    		newEntity.setUserId(classGroupLog.getSuccessorId()); 
    		newEntity.setUpdateTime(new Date());
    		newEntity.setCreateTime(new Date());
    		newsService.insert(newEntity);
    	}	
		if(classGroupLog.getLogType().equals("2")) { // 班前日志
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
			}
			
		} 
		if(classGroupLog.getLogType().equals("3")) {  // 班后日志
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
		if(classGroupLog.getLogType().equals("1")) { //班长日志
    		NewsEntity newsEntity = newsService.selectOne(new EntityWrapper<NewsEntity>().eq("news_number", classGroupLog.getLogNumber()).eq("news_type", 1));
    		if(!newsEntity.getUserId().equals(classGroupLog.getSuccessorId())) { // 是否更换 接班人
    			//更改 接班人
    			newsEntity.setUserId(classGroupLog.getSuccessorId()); 
    			newsEntity.setUpdateTime(new Date());
        		newsService.update(newsEntity, new EntityWrapper<NewsEntity>().eq("news_number", classGroupLog.getLogNumber()).eq("news_type", 1));
        		// 清空 驳回信息
        		NewsEntity entity = new NewsEntity();
        		entity.setNewsType(0);
        		entity.setUpdateTime(new Date()); 
        		newsService.update(entity, new EntityWrapper<NewsEntity>().eq("news_number", classGroupLog.getLogNumber()).eq("news_type", 2));
        		
        		// 修改 被驳回的 状态
        		classGroupLog.setLogStatus("2");
        		classGroupLog.setLogUserStatus("2");
        		classGroupLogService.updateById(classGroupLog);
    		}
    		
		}else if(classGroupLog.getLogType().equals("2")) { // 班前日志
			
			String teamMembersIds = classGroupLog.getTeamMembersIds();
    		Map<Integer,Boolean> map = new HashMap<Integer,Boolean>();
    		if(!teamMembersIds.equals("")) { // 改动班组成员
    			// 驳回状态的 全部干掉
    			NewsEntity Entity = new NewsEntity();
        		Entity.setNewsType(0);
        		Entity.setUpdateTime(new Date());
        		newsService.update(Entity, new EntityWrapper<NewsEntity>().eq("news_number", classGroupLog.getLogNumber()).eq("user_id", classGroupLog.getHandoverPersonId()).eq("news_type", 2));
    			
    			String[] teamMembersIdList = teamMembersIds.split(","); 
        		
        		// 查询之前的  已经确认的 班组成员
        		List<NewsEntity> NewsList = newsService.selectList(new EntityWrapper<NewsEntity>().eq("news_number", classGroupLog.getLogNumber()).eq("news_type", 0).eq("news_name", "您有一条待确认班前日志")); 
        		if(NewsList.size()>0) {
        			for(int i=0;i<NewsList.size();i++) {
            			Integer userId = NewsList.get(i).getUserId();
            			map.put(userId, false);
            		}
        		}
        		// 清空 之前的 未确认 班组成员
        		NewsEntity entity = new NewsEntity();
        		entity.setNewsType(0);
        		entity.setUpdateTime(new Date()); 
        		//newsService.update(entity, new EntityWrapper<NewsEntity>().eq("news_number", classGroupLog.getLogNumber()).eq("news_type", 1));
        		newsService.delete(new EntityWrapper<NewsEntity>().eq("news_number", classGroupLog.getLogNumber()).eq("news_type", 1));
        		// 取值 前端设置的 班组 查看 是否有重复的 
        		for(String user_id: teamMembersIdList) {
        			Integer userId = Integer.parseInt(user_id);
        			if(!map.containsKey(userId)) {
        				map.put(userId,true);
        			}
        		}
        		for (Entry<Integer, Boolean> entry:map.entrySet()){
        			if (entry.getValue().equals(Boolean.TRUE)){
        				//list.add(entry.getKey());
        				Integer user_id = entry.getKey();
        				// 进行通知 操作
        				NewsEntity newEntity = new NewsEntity();
        				newEntity.setNewsName("您有一条待确认的班前日志");
        				newEntity.setNewsNumber(classGroupLog.getLogNumber());
        				newEntity.setNewsType(1);
        				newEntity.setUserId(user_id);
        				newEntity.setCreateTime(new Date());
        				newEntity.setUpdateTime(new Date()); 
        				newsService.insertOrUpdate(newEntity);
        			}
        			
        		}
    		}
    		
    	}else if(classGroupLog.getLogType().equals("3")) {// 班后日志
    		
    		//可能 修改 班组成员的情况
    		Map<Integer,Boolean> map = new HashMap<Integer,Boolean>();
    		String teamMembersIds = classGroupLog.getTeamMembersIds();// 班组成员
    		if(!teamMembersIds.equals("")) { // 没有改动班组成员
    			
    			NewsEntity Entity = new NewsEntity();
        		Entity.setNewsType(0);
        		Entity.setUpdateTime(new Date());
        		newsService.update(Entity, new EntityWrapper<NewsEntity>().eq("news_number", classGroupLog.getLogNumber()).eq("user_id", classGroupLog.getHandoverPersonId()).eq("news_type", 2));
    			
    			String[] teamMembersIdList = teamMembersIds.split(","); 
        		
        		// 查询之前的  已经确认的 班组成员
        		List<NewsEntity> NewsList = newsService.selectList(new EntityWrapper<NewsEntity>().eq("news_number", classGroupLog.getLogNumber()).eq("news_type", 0).eq("news_name", "您有一条待确认班后日志")); 
        		if(NewsList.size()>0) {
        			for(int i=0;i<NewsList.size();i++) {
            			Integer userId = NewsList.get(i).getUserId();
            			map.put(userId, false);
            		}
        		}
        		// 清空 之前的 未确认 班组成员
        		NewsEntity entity = new NewsEntity();
        		entity.setNewsType(0);
        		entity.setUpdateTime(new Date()); 
        		//newsService.update(entity, new EntityWrapper<NewsEntity>().eq("news_number", classGroupLog.getLogNumber()).eq("news_type", 1));
        		newsService.delete(new EntityWrapper<NewsEntity>().eq("news_number", classGroupLog.getLogNumber()).eq("news_type", 1));
        		// 取值 前端设置的 班组 查看 是否有重复的 
        		for(String user_id: teamMembersIdList) {
        			Integer userId = Integer.parseInt(user_id);
        			if(!map.containsKey(userId)) {
        				map.put(userId,true);
        			}
        		}
        		for (Entry<Integer, Boolean> entry:map.entrySet()){
        			if (entry.getValue().equals(Boolean.TRUE)){
        				Integer user_id = entry.getKey();
        				// 进行通知 操作
        				NewsEntity newEntity = new NewsEntity();
        				newEntity.setNewsName("您有一条待确认的班后日志");
        				newEntity.setNewsNumber(classGroupLog.getLogNumber());
        				newEntity.setNewsType(1);
        				newEntity.setUserId(user_id);
        				newEntity.setCreateTime(new Date());
        				newEntity.setUpdateTime(new Date()); 
        				newsService.insertOrUpdate(newEntity);
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
    	
        String orderNumber = OrderUtils.orderNumber();
        
    	return R.ok().put("logNumber", orderNumber);
    }
    
    
    
    
}
