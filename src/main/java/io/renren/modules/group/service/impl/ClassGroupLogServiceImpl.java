package io.renren.modules.group.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.group.dao.ClassGroupLogDao;
import io.renren.modules.group.entity.ClassGroupLogEntity;
import io.renren.modules.group.service.ClassGroupLogService;
import io.renren.modules.setting.entity.BaseTurnEntity;
import io.renren.modules.setting.service.BaseTurnService;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.service.SysDeptService;


@Service("classGroupLogService")
public class ClassGroupLogServiceImpl extends ServiceImpl<ClassGroupLogDao, ClassGroupLogEntity> implements ClassGroupLogService {

	@Autowired
    private SysDeptService sysDeptService;
	
	@Autowired
    private BaseTurnService baseTurnService;
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	
    	String logNumber = params.get("logNumber").toString();
    	String deptId = params.get("deptId").toString();
    	String classGroupName = params.get("classGroupName").toString();
    	String baseTurnId = params.get("baseTurnId").toString();
    	String logType = params.get("logType").toString();
    	String logStatus = params.get("logStatus").toString();
        Page<ClassGroupLogEntity> page = this.selectPage(
                new Query<ClassGroupLogEntity>(params).getPage(),
                new EntityWrapper<ClassGroupLogEntity>()
                .like(StringUtils.isNotBlank(logNumber),"log_number", logNumber)
                .like(StringUtils.isNotBlank(deptId),"dept_id", deptId)
                .like(StringUtils.isNotBlank(classGroupName),"class_group_name", classGroupName)
                .like(StringUtils.isNotBlank(baseTurnId),"base_turn_id", baseTurnId)
                .like(StringUtils.isNotBlank(logType),"log_type", logType)
                .like(StringUtils.isNotBlank(logStatus),"log_status", logStatus).orderBy("class_id",false)
        );
        
        for(ClassGroupLogEntity classGroupLogEntity: page.getRecords()) {
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
        	}
			if(classGroupLogEntity.getLogStatus().equals("4")|| classGroupLogEntity.getLogUserStatus().equals("4")) {
				classGroupLogEntity.setLogStatusName("待确认有驳回");
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
