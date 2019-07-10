package io.renren.modules.management.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.inspection.entity.InspectionResultEntity;
import io.renren.modules.inspection.entity.InspectionResultMediaEntity;
import io.renren.modules.inspection.service.InspectionItemService;
import io.renren.modules.inspection.service.InspectionResultMediaService;
import io.renren.modules.inspection.service.TurnService;
import io.renren.modules.management.dao.OrderDefectDao;
import io.renren.modules.management.entity.OrderDefectiveEntity;
import io.renren.modules.management.service.OrderDefectService;
import io.renren.modules.setting.service.ExceptionService;
import io.renren.modules.sys.service.SysDeptService;
import io.renren.modules.sys.service.SysUserService;

@Service("orderDefectServiceImpl")
public class OrderDefectServiceImpl extends ServiceImpl<OrderDefectDao, OrderDefectiveEntity> implements OrderDefectService{

	@Autowired
    private InspectionItemService itemService;
	
    @Autowired
    private SysUserService userService;
    
    @Autowired
    private TurnService turnService;
    
    @Autowired
    private ExceptionService exceptionService;
    
    @Autowired
    private InspectionResultMediaService mediaService;
    
    @Autowired
    private SysDeptService deptService;
    
    @Autowired
    private OrderDefectDao orderDefectDao;
	
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
	
		String deptId = (String)params.get("deptId");
		String orderStatus = (String)params.get("orderStatus"); 
        String startTime = (String)params.get("startTime");
        String endTime = (String)params.get("endTime");

        InspectionResultEntity resultEntity = new InspectionResultEntity();
        
        if(deptId !=null && !deptId.equals("")) {
        	List<Integer> deptIds = deptService.queryRecursiveChildByParentId(Long.parseLong(deptId));
            resultEntity.setDeptIds(deptIds);
        }

        if(orderStatus != null && !orderStatus.equals("")) {
        	resultEntity.setOrderStatus(Integer.parseInt(orderStatus)); 
        }

        Page<InspectionResultEntity> page = new Query<InspectionResultEntity>(params).getPage();
        List<InspectionResultEntity> list = this.baseMapper.selectResultList(
                page,
                resultEntity
        );

        for(InspectionResultEntity result: list){
            List<InspectionResultMediaEntity> mediaEntityList = mediaService.selectListByResultId(result.getId());
            result.setMedias(mediaEntityList);
            result.setDeviceName(result.getDeviceName() + "[" + result.getDeviceCode() + "]");
            StringBuffer buffer = new StringBuffer(20);
            if(result.getUpLimit() != null){
                buffer.append(result.getUpLimit() + "/");
            }else{
                buffer.append("-/");
            }
            if(result.getUpupLimit() !=null){
                buffer.append(result.getUpupLimit() + "/");
            }else{
                buffer.append("-/");
            }
            if(result.getDownLimit() !=null){
                buffer.append(result.getDownLimit() + "/");
            }else{
                buffer.append("-/");
            }
            if(result.getDowndownLimit() !=null){
                buffer.append(result.getDowndownLimit() );
            }else{
                buffer.append("-");
            }
            result.setLimits(buffer.toString());
            if(result.getOrderStatus() == null || result.getOrderStatus() ==0  ) {
            	result.setOrderStatusName("待确认");
            }else if(result.getOrderStatus() ==1) {
            	result.setOrderStatusName("已确认");
            }else if(result.getOrderStatus() ==2) {
            	result.setOrderStatusName("已挂起");
            }
                                                                                                                                                       
        }
        page.setRecords(list);
        
        return new PageUtils(page);
	}

	@Override
	public OrderDefectiveEntity selectBydefectiveorderId(Integer resultId) {
		
		OrderDefectiveEntity orderDefectiveEntity = orderDefectDao.selectBydefectiveorderId(resultId);
		
		return orderDefectiveEntity;
	}

	
	
}
