package io.renren.modules.group.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.group.entity.ClassGroupLogEntity;
import io.renren.modules.group.service.ClassGroupLogRejectService;
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
@RequestMapping("group/classgrouplogreject")
public class ClassGroupLogRejectController {

	
	@Autowired
	private NewsService newsService; 
	
	@Autowired
	private ClassGroupLogRejectService classGroupLogRejectService;
	
	
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("group:classgrouplog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = classGroupLogRejectService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{classId}")
    @RequiresPermissions("group:classgrouplog:info")
    public R info(@PathVariable("classId") Integer classId){
			ClassGroupLogEntity classGroupLog = classGroupLogRejectService.selectById(classId);

        return R.ok().put("classgrouplog", classGroupLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("group:classgrouplog:save")
    public R save(@RequestBody ClassGroupLogEntity classGroupLog){
    	classGroupLogRejectService.insert(classGroupLog);
    	
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("group:classgrouplog:update")
    public R update(@RequestBody ClassGroupLogEntity classGroupLog){
    	classGroupLogRejectService.updateById(classGroupLog);
    	if(classGroupLog.getLogType().equals("1")) {  // 班长日志 （可能改 接班人）
    		NewsEntity newsEntity = newsService.selectOne(new EntityWrapper<NewsEntity>().eq("news_number", classGroupLog.getLogNumber()).eq("news_type", 1));
    		if(!newsEntity.getUserId().equals(classGroupLog.getSuccessorId())) { // 更换 接班人
    			newsEntity.setUserId(classGroupLog.getSuccessorId());
    			newsEntity.setUpdateTime(new Date()); 
    			newsService.updateById(newsEntity);
    		}
    		// 驳回的通知 改为 0
    		NewsEntity entity = new NewsEntity();
    		entity.setNewsType(0);
    		entity.setUpdateTime(new Date()); 
    		newsService.update(entity, new EntityWrapper<NewsEntity>().eq("news_number", classGroupLog.getLogNumber()).eq("news_type", 2).eq("user_id", classGroupLog.getHandoverPersonId())); 
    		
    		
    	}else if(classGroupLog.getLogType().equals("2")) { // 班前日志 （ 可能 修改 班组成员的情况）
    		
    		NewsEntity Entity = new NewsEntity();
    		Entity.setNewsType(0);
    		Entity.setUpdateTime(new Date());
    		newsService.update(Entity, new EntityWrapper<NewsEntity>().eq("news_number", classGroupLog.getLogNumber()).eq("user_id", classGroupLog.getHandoverPersonId()).eq("news_type", 2));
    		
    		//可能 修改 班组成员的情况
    		Map<Integer,Boolean> map = new HashMap<Integer,Boolean>();
    		String teamMembersIds = classGroupLog.getTeamMembersIds();// 班组成员
    		if(!teamMembersIds.equals("")) { // 没有改动班组成员
    			
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
    		
    	}else if(classGroupLog.getLogType().equals("3")) {  //班后日志 （可能 修改班组 成员的情况）
    		
    		NewsEntity Entity = new NewsEntity();
    		Entity.setNewsType(0);
    		Entity.setUpdateTime(new Date());
    		newsService.update(Entity, new EntityWrapper<NewsEntity>().eq("news_number", classGroupLog.getLogNumber()).eq("user_id", classGroupLog.getHandoverPersonId()).eq("news_type", 2));
    		//可能 修改 班组成员的情况
    		Map<Integer,Boolean> map = new HashMap<Integer,Boolean>();
    		String teamMembersIds = classGroupLog.getTeamMembersIds();// 班组成员
    		if(!teamMembersIds.equals("")) { // 没有改动班组成员
    			
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
    	classGroupLogRejectService.deleteBatchIds(Arrays.asList(classIds));

        return R.ok();
    }
    
    /**
     * 日志编码
     */
    @RequestMapping("/logNumber")
    @RequiresPermissions("group:classgrouplog:logNumber")
    public R dataNumber() {
    	
    	String str = "SWQ";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate=sdf.format(new Date());
        Random random=new Random();
        String result="";
        for(int i=0;i<3;i++){
        result+=random.nextInt(10);
        }
        String sb =str+newDate+result;
        
    	return R.ok().put("logNumber", sb);
    }
    
    
    /**
     * 去确认 确定
     */
    @RequestMapping("/reject")
    @RequiresPermissions("group:classgrouplogreject:reject")
	public R conFirmed(@RequestParam Map<String, Object> params) {
		
    	String user_id = params.get("user_id").toString();
    	String news_number = params.get("log_number").toString();
    	 
    	List<NewsEntity> selectList = newsService.selectList(
    			new EntityWrapper<NewsEntity>()
    			.eq("user_id", Integer.parseInt(user_id))
    			.eq("news_number", news_number)
    			.eq("news_type", 2));
    	
    	return R.ok().put("newsCounts", selectList.size()); 
	}
    
    
	
	
}
