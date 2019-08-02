package io.renren.modules.group.service.impl;

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
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


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
    	String [] LogStatusObj = new String[10];
		if(logStatus.equals("4") ||logStatus.equals("2")){ // 待确认有驳回
			LogStatusObj[0] = "2";
			LogStatusObj[1] = "4";
		}else if(logStatus.equals("1")) {
			LogStatusObj[0] = "1";
		}else if(logStatus.equals("3")) {
			LogStatusObj[0] = "3";
		}
        Page<ClassGroupLogEntity> page = this.selectPage(
                new Query<ClassGroupLogEntity>(params).getPage(),
                new EntityWrapper<ClassGroupLogEntity>()
                .like(StringUtils.isNotBlank(logNumber),"log_number", logNumber)
                .like(StringUtils.isNotBlank(deptId),"dept_id", deptId)
                .like(StringUtils.isNotBlank(classGroupName),"class_group_name", classGroupName)
                .like(StringUtils.isNotBlank(baseTurnId),"base_turn_id", baseTurnId)
                .like(StringUtils.isNotBlank(logType),"log_type", logType)
                .in(StringUtils.isNotBlank(logStatus),"log_status", LogStatusObj)
				.orderBy("class_id",false)
				
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
				classGroupLogEntity.setLogStatusName("!待确认");
			}
        	
        	// 获取部门(车间工段) 名称
        	SysDeptEntity sysDeptEntity = sysDeptService.selectById(classGroupLogEntity.getDeptId());
        	classGroupLogEntity.setDeptName(sysDeptEntity.getName());
        	
        	// 获取班次(轮次) 名称
        	BaseTurnEntity baseTurnEntity = baseTurnService.selectById(classGroupLogEntity.getBaseTurnId());
        	classGroupLogEntity.setBaseTurnName(baseTurnEntity.getName());
        	
        	String successor = classGroupLogEntity.getSuccessor();
        	String[] split = successor.split(",");
        	if(split.length>1) {
        		classGroupLogEntity.setSuccessorName(split[0]+"...");
        	}else {
        		classGroupLogEntity.setSuccessorName(successor); 
        	}
        	
        }
        
        
        
        return new PageUtils(page);
    }
    
    @Override
    public PageUtils querybanqianPage(Map<String, Object> params) {
    	Page<ClassGroupLogEntity> page = this.selectPage(
                new Query<ClassGroupLogEntity>(params).getPage(),
                new EntityWrapper<ClassGroupLogEntity>()
                .eq("log_type", "2")
				.orderBy("class_id",false)
				
        );
    	return new PageUtils(page);
    }
}
