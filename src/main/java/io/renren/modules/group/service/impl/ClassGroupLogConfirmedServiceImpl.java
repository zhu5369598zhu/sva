package io.renren.modules.group.service.impl;

import java.util.List;
import java.util.Map;

import io.renren.modules.inspection.entity.ClassGroupEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.group.dao.ClassGroupLogConfirmedDao;
import io.renren.modules.group.entity.ClassGroupLogEntity;
import io.renren.modules.group.service.ClassGroupLogConfirmedService;
import io.renren.modules.setting.entity.BaseTurnEntity;
import io.renren.modules.setting.service.BaseTurnService;
import io.renren.modules.sys.entity.NewsEntity;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.service.NewsService;
import io.renren.modules.sys.service.SysDeptService;

@Service("classGroupLogServiceConfirmed")
public class ClassGroupLogConfirmedServiceImpl extends ServiceImpl<ClassGroupLogConfirmedDao, ClassGroupLogEntity> implements ClassGroupLogConfirmedService{

	@Autowired
    private SysDeptService sysDeptService;
	
	@Autowired
    private BaseTurnService baseTurnService;
	
	@Autowired
	private NewsService newsService;


	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		
		String logNumber = (String)params.get("logNumber");
    	String deptId = (String)params.get("deptId");
    	String classGroupName = (String)params.get("classGroupName");
    	String baseTurnId = (String)params.get("baseTurnId");
    	String logType = (String)params.get("logType");
    	String logStatus = (String)params.get("logStatus"); 
    	String[] LogStatusObj = new String[2];
		if(logStatus.equals("4") ||logStatus.equals("2")){ // 待确认有驳回
			LogStatusObj[0] = "2";
			LogStatusObj[1] = "4";
		}
		String[] logTypeObj = new String[2];
		logTypeObj[0] = "1";
		logTypeObj[1] = "2";
    	
        Page<ClassGroupLogEntity> page = this.selectPage(
                new Query<ClassGroupLogEntity>(params).getPage(),
                new EntityWrapper<ClassGroupLogEntity>()
                .like(StringUtils.isNotBlank(logNumber),"log_number", logNumber)
                .like(StringUtils.isNotBlank(deptId),"dept_id", deptId)
                .like(StringUtils.isNotBlank(classGroupName),"class_group_name", classGroupName)
                .like(StringUtils.isNotBlank(baseTurnId),"base_turn_id", baseTurnId)
                .like(StringUtils.isNotBlank(logType),"log_type", logType)
                .in(StringUtils.isNotBlank(logStatus),"log_status", LogStatusObj)
                .orderBy("class_id", false)
                
        );
        
        List<ClassGroupLogEntity> list = page.getRecords();
        // 清除 和用户不相关的 日志
        for(int i=0;i<list.size();i++) {
        	ClassGroupLogEntity classGroupLogEntity = list.get(i); 
        	if(!params.get("user_id").toString().equals("")) { 
        		List<NewsEntity> selectList = newsService.selectList(new EntityWrapper<NewsEntity>().eq("user_id", Integer.parseInt(params.get("user_id").toString())).in("news_type", logTypeObj).eq("news_number", classGroupLogEntity.getLogNumber()));
            	if(selectList.size()==0) {
            		list.remove(i);
            	}
        	}
        }
        for(ClassGroupLogEntity classGroupLogEntity:list) {
        	// 获取日志名称
        	if(classGroupLogEntity.getLogType().equals("1")) { //班长日志
        		classGroupLogEntity.setLogTypeName("班长日志");
        	}else if(classGroupLogEntity.getLogType().equals("2")) {//班前日志
        		classGroupLogEntity.setLogTypeName("班前会");
        	}else if(classGroupLogEntity.getLogType().equals("3")) {//班后日志
        		classGroupLogEntity.setLogTypeName("班后会");
        	}
        	// 获取日志状态名称
        	if(classGroupLogEntity.getLogStatus().equals("1")) {//拟制中
        		classGroupLogEntity.setLogStatusName("拟制中");
        	}else if(classGroupLogEntity.getLogStatus().equals("2")) {
        		classGroupLogEntity.setLogStatusName("待确认");
        	}else if(classGroupLogEntity.getLogStatus().equals("3")) {
        		classGroupLogEntity.setLogStatusName("已确认");
        	}else if(classGroupLogEntity.getLogStatus().equals("4")) {
        		classGroupLogEntity.setLogStatusName("!待确认");
        	}
        	if(classGroupLogEntity.getLogUserStatus().equals("4")) {
        		classGroupLogEntity.setLogStatusName("!待确认");
        	}
        	// 获取部门(车间工段) 名称
        	SysDeptEntity sysDeptEntity = sysDeptService.selectById(classGroupLogEntity.getDeptId());
        	classGroupLogEntity.setDeptName(sysDeptEntity.getName());
        	
        	// 获取班次(轮次) 名称
        	BaseTurnEntity baseTurnEntity = baseTurnService.selectById(classGroupLogEntity.getBaseTurnId());
        	classGroupLogEntity.setBaseTurnName(baseTurnEntity.getName());

			String user_id = params.get("userid").toString();
            String news_number = classGroupLogEntity.getLogNumber();
			List<NewsEntity> selectList = newsService.selectList(
					new EntityWrapper<NewsEntity>()
							.eq("user_id", Integer.parseInt(user_id))
							.eq("news_number", news_number)
							.eq("news_type", 1));
			classGroupLogEntity.setNewsCounts(selectList.size());
			
			List<NewsEntity> rejectselectList = newsService.selectList(
					new EntityWrapper<NewsEntity>()
							.eq("user_id", Integer.parseInt(user_id))
							.eq("news_number", news_number)
							.eq("news_type", 2));
			classGroupLogEntity.setRejectNewsCounts(rejectselectList.size()); 
			
        }
        
        return new PageUtils(page);
	}


}
