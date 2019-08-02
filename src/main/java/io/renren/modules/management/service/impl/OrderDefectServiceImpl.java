package io.renren.modules.management.service.impl;

import java.text.SimpleDateFormat;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.inspection.entity.InspectionItemExtraEntity;
import io.renren.modules.inspection.entity.InspectionItemPresuppositionEntity;
import io.renren.modules.inspection.entity.InspectionResultEntity;
import io.renren.modules.inspection.entity.InspectionResultMediaEntity;
import io.renren.modules.inspection.service.InspectionItemExtraService;
import io.renren.modules.inspection.service.InspectionItemPresuppositionService;
import io.renren.modules.inspection.service.InspectionItemService;
import io.renren.modules.inspection.service.InspectionResultMediaService;
import io.renren.modules.inspection.service.TurnService;
import io.renren.modules.management.dao.OrderDefectDao;
import io.renren.modules.management.entity.OrderDefectiveEntity;
import io.renren.modules.management.service.OrderDefectService;
import io.renren.modules.setting.entity.ExceptionEntity;
import io.renren.modules.setting.entity.UnitEntity;
import io.renren.modules.setting.service.ExceptionService;
import io.renren.modules.setting.service.UnitService;
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
    
    @Autowired
    InspectionItemExtraService extraService;
    
    @Autowired
    InspectionItemPresuppositionService presuppositionService;
    
    @Autowired
    UnitService unitService;
	
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
	
		String deptId = (String)params.get("deptId");
		String defectiveNumber = (String) params.get("defectiveNumber");
		String orderStatus = (String)params.get("orderStatus"); 
        String startTime = (String)params.get("startTime");
        String endTime = (String)params.get("endTime");

        InspectionResultEntity resultEntity = new InspectionResultEntity();
        
        if(deptId !=null && !deptId.equals("")) {
        	List<Integer> deptIds = deptService.queryRecursiveChildByParentId(Long.parseLong(deptId));
            resultEntity.setDeptIds(deptIds);
        }
        if(defectiveNumber !=null && !defectiveNumber.equals("")){
            resultEntity.setDefectiveNumber(defectiveNumber);
        }
        if(orderStatus != null && !orderStatus.equals("")) {
        	resultEntity.setOrderStatus(Integer.parseInt(orderStatus)); 
        }
        if(startTime != null && !startTime.equals("")) {
            resultEntity.setStartTime(startTime);
        }

        if(endTime != null && !endTime.equals("")) {
            resultEntity.setEndTime(endTime);
        }

        Page<InspectionResultEntity> page = new Query<InspectionResultEntity>(params).getPage();
        List<InspectionResultEntity> list = this.baseMapper.selectResultList(
                page,
                resultEntity
        );

        for(InspectionResultEntity result: list){
        	result.setDeviceName(result.getDeviceName() + "[" + result.getDeviceCode() + "]");
            if(result.getIsHere().equals(0)) {
                result.setIsHereName("否");
            } else if(result.getIsHere().equals(1)){
                result.setIsHereName("是");
            }
            if(result.getIsOk().equals(0)){
                result.setIsOkName("否");
            } else if(result.getIsOk().equals(1)){
                result.setIsOkName("是");
            }
            if(result.getIsCheck().equals(0)){
                result.setIsCheckName("否");
            } else if(result.getIsCheck().equals(1)){
                result.setIsCheckName("是");
            }
        	if (result.getInspectionType().equals("抄表")) {
                UnitEntity unit = unitService.selectById(result.getUnit());
                if(unit != null){
                    result.setUnit(unit.getName());
                }
            }

            if(result.getInspectionType().equals("观察") && result.getResult() != null){
                InspectionItemExtraEntity extraEntity = extraService.selectByGuid(result.getResult());
                if(extraEntity != null){
                    ExceptionEntity exceptionEntity = exceptionService.selectById(extraEntity.getExceptionId());
                    if(exceptionEntity != null){
                        result.setExceptionId(exceptionEntity.getId());
                        result.setExceptionName(exceptionEntity.getName());
                        result.setResult(exceptionEntity.getName());
                    }else{
                        result.setResult("无效数据");
                    }
                }
            }

            if(result.getInspectionType().equals("预设状况") && result.getResult() != null){
                LinkedHashSet presuppositionList = new LinkedHashSet();
                try{
                    JSONArray jsonArr = JSONArray.parseArray(result.getResult());
                    for (int i = 0; i < jsonArr.size(); i++){
                        String el = jsonArr.getString(i);
                        InspectionItemPresuppositionEntity presupposition = presuppositionService.selectByGuid(el);
                        if (presupposition != null) {
                            presuppositionList.add(presupposition.getName());
                        }else {
                            result.setResult("无效数据");
                        }
                    }
                    result.setResult(org.apache.commons.lang.StringUtils.join(presuppositionList.toArray(), '/'));
                }catch (Exception e) {

                }
            }
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
            }else if(result.getOrderStatus() ==3) {
            	result.setOrderStatusName("转工单被拒绝"); 
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
