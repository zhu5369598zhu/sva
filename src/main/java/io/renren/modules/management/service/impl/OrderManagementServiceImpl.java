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
import io.renren.modules.management.dao.OrderManagementDao;
import io.renren.modules.management.entity.OrderManagementEntity;
import io.renren.modules.management.service.OrderManagementService;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.service.SysDeptService;


@Service("orderManagementService")
public class OrderManagementServiceImpl extends ServiceImpl<OrderManagementDao, OrderManagementEntity> implements OrderManagementService {

	@Autowired
    private SysDeptService sysDeptService;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params)  {
    	String orderNumber = (String)params.get("orderNumber");
    	String orderName = (String)params.get("orderName"); 
    	String orderStatus = (String)params.get("orderStatus");
    	String startTime = (String)params.get("startTime");
        String endTime = (String)params.get("endTime");
        String deptId = (String)params.get("deptId");
        
        HashMap<String,Object> entityMap = new HashMap<String,Object>();
        if(orderNumber !=null && !orderNumber.equals("")){
        	entityMap.put("orderNumber", orderNumber);
        }
        if(orderName !=null && !orderName.equals("")) {
        	entityMap.put("orderName", orderName);
        }
        if(orderStatus !=null && !orderStatus.equals("")) {
        	entityMap.put("orderStatus", orderStatus);
        }
        if(deptId !=null && !deptId.equals("")) {
        	List<Integer> deptIds = sysDeptService.queryRecursiveChildByParentId(Long.parseLong(deptId));
        	entityMap.put("deptIds", deptIds);
        	entityMap.put("deptId", deptId);
        }
        if(startTime !=null && !startTime.equals("")) {
        	entityMap.put("startTime", startTime);
        }
        if(endTime !=null && !endTime.equals("")) {
        	entityMap.put("endTime", endTime);
        }
       
        
        Page<OrderManagementEntity> page = new Query<OrderManagementEntity>(params).getPage();
        List<OrderManagementEntity> List = this.baseMapper.selectOrderManagement(
        		page,
        		entityMap);
        
        for(OrderManagementEntity orderManagement:List) {
        	
        	if(orderManagement.getOrderStatus() ==0) {
        		orderManagement.setOrderStatusName("拟制中");
        	}else if(orderManagement.getOrderStatus()==1) {
        		orderManagement.setOrderStatusName("已下发待受理");
        	}else if(orderManagement.getOrderStatus()==2) {
        		orderManagement.setOrderStatusName("已受理待上报");
        	}else if(orderManagement.getOrderStatus()==3) {
        		orderManagement.setOrderStatusName("已上报待审核");
        	}else if(orderManagement.getOrderStatus()==4) {
        		orderManagement.setOrderStatusName("已确认待完结");
        	}else if(orderManagement.getOrderStatus()==5) {
        		orderManagement.setOrderStatusName("已完结");
        	}else if(orderManagement.getOrderStatus()==6) {
        		orderManagement.setOrderStatusName("已下发被拒绝");
        	}else if(orderManagement.getOrderStatus()==7) {
        		orderManagement.setOrderStatusName("已上报被拒绝");
        	}else if(orderManagement.getOrderStatus()==8) {
        		orderManagement.setOrderStatusName("已确认不结算"); 
        	}else if(orderManagement.getOrderStatus()==9){
        		orderManagement.setOrderStatusName("已转单待确认");
			}else if(orderManagement.getOrderStatus()==14) {
				orderManagement.setOrderStatusName("!已受理待上报");
			}
			Integer orderType = orderManagement.getOrderType();
			if(orderType == 0){
        		orderManagement.setOrderTypeName("填报工单");
			}else if(orderType ==1){
        		orderManagement.setOrderTypeName("缺陷填报工单");
			}else if(orderType ==2){
                orderManagement.setOrderTypeName("巡检异常工单");
			}
        	
        	SysDeptEntity sysDeptEntity = sysDeptService.selectById(orderManagement.getDeptId());
        	orderManagement.setDeptName(sysDeptEntity.getName());
        }
        
        page.setRecords(List);
        
        return new PageUtils(page);
    }

}
