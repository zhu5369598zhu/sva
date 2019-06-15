package io.renren.modules.management.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.management.dao.OrderDefectiveDao;
import io.renren.modules.management.entity.OrderDefectiveEntity;
import io.renren.modules.management.service.OrderDefectiveService;
import io.renren.modules.setting.entity.ExceptionEntity;
import io.renren.modules.setting.service.ExceptionService;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.service.SysDeptService;


@Service("orderDefectiveService")
public class OrderDefectiveServiceImpl extends ServiceImpl<OrderDefectiveDao, OrderDefectiveEntity> implements OrderDefectiveService {

	
	@Autowired
    private ExceptionService exceptionService;
	
	@Autowired
	private SysDeptService sysDeptService;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	
    	String deptId = (String)params.get("deptId");
    	String defectiveTheme = (String)params.get("defectiveTheme");
    	String defectiveNumber = (String)params.get("defectiveNumber");
    	String defectiveName = (String)params.get("defectiveName");
    	String exceptionId = (String)params.get("exceptionId");
    	
    	String pageIndex = (String)params.get("page");
    	String limit = (String)params.get("limit");
        
        HashMap<String,Object> entityMap = new HashMap<String,Object>();
        if(deptId !=null && !deptId.equals("")) { 
        	List<Integer> deptIds = sysDeptService.queryRecursiveChildByParentId(Long.parseLong(deptId));
        	entityMap.put("deptIds", deptIds);
        	entityMap.put("deptId", deptId);
        }
        if(defectiveTheme !=null && !defectiveTheme.equals("")) {
        	entityMap.put("defectiveTheme", defectiveTheme);
        }
        if(defectiveNumber !=null && !defectiveNumber.equals("")) {
        	entityMap.put("defectiveNumber",defectiveNumber);
        }
        if(defectiveName !=null && !defectiveName.equals("")) {
        	entityMap.put("defectiveName", defectiveName);
        }
        if(exceptionId !=null && !exceptionId.equals("")) {
        	entityMap.put("exceptionId", exceptionId);
        }
        
        Page<OrderDefectiveEntity> page = new Query<OrderDefectiveEntity>(params).getPage();
        List<OrderDefectiveEntity> orderDefectiveList = this.baseMapper.selectOrderDefective(entityMap);
        
        page.setTotal(orderDefectiveList.size());
        
        if(pageIndex !=null && !pageIndex.equals("")) {
        	entityMap.put("pageIndex",10* (Integer.parseInt(pageIndex)-1));
        }
        if(limit !=null && !limit.equals("")) {
        	entityMap.put("limit", Integer.parseInt(limit));
        }
        List<OrderDefectiveEntity> orderDefective = this.baseMapper.selectOrderDefective(entityMap);
        for(OrderDefectiveEntity defective: orderDefective) {
        	
        	
        	if(defective.getDefectiveType() ==0) {
        		defective.setDefectiveTypeName("巡检缺陷异常");
        	}else if(defective.getDefectiveType() ==1) {
        		defective.setDefectiveTypeName("填报缺陷异常"); 
        	}
        	
        	if(defective.getOrderStatus() ==0) {
        		defective.setOrderStatusName("拟制中");
        	}else if(defective.getOrderStatus() ==1) {
        		defective.setOrderStatusName("待确认");
        	}else if(defective.getOrderStatus() ==2) {
        		defective.setOrderStatusName("已转"); 
        	}
        	
        	SysDeptEntity sysDeptEntity = sysDeptService.selectById(defective.getDeptId());
        	defective.setDeptName(sysDeptEntity.getName()); 
        	
        	ExceptionEntity exception = exceptionService.selectById(defective.getExceptionId());
        	defective.setExceptionName(exception.getName()); 
        }
        
        
        
        page.setRecords(orderDefective);
        
        return new PageUtils(page);
    }

}
