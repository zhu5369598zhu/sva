package io.renren.modules.group.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.group.dao.ClassGroupLogAlreadyDao;
import io.renren.modules.group.entity.ClassGroupLogEntity;
import io.renren.modules.group.service.ClassGroupLogAlreadyService;
import io.renren.modules.setting.entity.BaseTurnEntity;
import io.renren.modules.setting.service.BaseTurnService;
import io.renren.modules.sys.entity.NewsEntity;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.service.NewsService;
import io.renren.modules.sys.service.SysDeptService;

@Service("classGroupLogAlreadyService")
public class ClassGroupLogAlreadyServiceImpl extends ServiceImpl<ClassGroupLogAlreadyDao, ClassGroupLogEntity> implements ClassGroupLogAlreadyService{

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
    	String logStatus = (String)params.get("logStatus");
    	String logUserStatus =(String)params.get("logUserStatus");
        Page<ClassGroupLogEntity> page = this.selectPage(
                new Query<ClassGroupLogEntity>(params).getPage(),
                new EntityWrapper<ClassGroupLogEntity>()
                .like(StringUtils.isNotBlank(logNumber),"log_number", logNumber)
                .like(StringUtils.isNotBlank(deptId),"dept_id", deptId)
                .like(StringUtils.isNotBlank(classGroupName),"class_group_name", classGroupName)
                .like(StringUtils.isNotBlank(baseTurnId),"base_turn_id", baseTurnId)
                .eq(StringUtils.isNotBlank(logStatus),"log_status", logStatus)
                .eq(StringUtils.isNotBlank(logUserStatus), "log_user_status",logUserStatus)
                .orderBy("class_id", false)
        );
        List<ClassGroupLogEntity> list = page.getRecords();
     
        for(ClassGroupLogEntity classGroupLogEntity :list) {
        	if(classGroupLogEntity.getLogType().equals("1")) { //班长日志
        		classGroupLogEntity.setLogTypeName("班长日志");
        	}else if(classGroupLogEntity.getLogType().equals("2")) {//班前日志
        		classGroupLogEntity.setLogTypeName("班前会");
        	}else if(classGroupLogEntity.getLogType().equals("3")) {//班后日志
        		classGroupLogEntity.setLogTypeName("班后会");
        	}
        	// 获取日志状态名称
        	if(classGroupLogEntity.getLogUserStatus().equals("1")) {//拟制中
        		classGroupLogEntity.setLogStatusName("拟制中");
        	}else if(classGroupLogEntity.getLogUserStatus().equals("2")) {
        		classGroupLogEntity.setLogStatusName("待确认");
        	}else if(classGroupLogEntity.getLogUserStatus().equals("3")) {
        		classGroupLogEntity.setLogStatusName("已确认");
        	}else if(classGroupLogEntity.getLogUserStatus().equals("4")) {
        		classGroupLogEntity.setLogStatusName("已驳回");
        	}
        	
        	// 获取部门(车间工段) 名称
        	SysDeptEntity sysDeptEntity = sysDeptService.selectById(classGroupLogEntity.getDeptId());
        	classGroupLogEntity.setDeptName(sysDeptEntity.getName());
        	
        	// 获取班次(轮次) 名称
        	BaseTurnEntity baseTurnEntity = baseTurnService.selectById(classGroupLogEntity.getBaseTurnId());
        	classGroupLogEntity.setBaseTurnName(baseTurnEntity.getName());
        }
        
        
        return new PageUtils(page);
	}

}
