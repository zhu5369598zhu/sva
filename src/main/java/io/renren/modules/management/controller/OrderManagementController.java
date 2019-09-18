package io.renren.modules.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.utils.*;
import io.renren.modules.inspection.entity.InspectionResultEntity;
import io.renren.modules.inspection.service.InspectionResultService;
import io.renren.modules.management.entity.OrderDefectiveEntity;
import io.renren.modules.management.entity.OrderManagementEntity;
import io.renren.modules.management.entity.OrderRecordEntity;
import io.renren.modules.management.service.OrderDefectiveService;
import io.renren.modules.management.service.OrderManagementService;
import io.renren.modules.management.service.OrderRecordService;
import io.renren.modules.setting.entity.OrderExceptionEntity;
import io.renren.modules.setting.service.OrderExceptionService;
import io.renren.modules.sys.entity.NewsEntity;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.DeviceExceptionService;
import io.renren.modules.sys.service.NewsService;
import io.renren.modules.sys.service.SysDeptService;
import io.renren.modules.sys.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 工单管理表
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-05-21 11:00:21
 */
@RestController
@RequestMapping("management/ordermanagement")
public class OrderManagementController {
    @Autowired
    private OrderManagementService orderManagementService;

    @Autowired
    private NewsService newsService;
    
    @Autowired
    private SysDeptService sysDeptService;
    
    @Autowired
    private OrderRecordService orderRecordService;

	@Autowired
	private OrderExceptionService orderExceptionService;

    @Autowired
    private OrderDefectiveService orderDefectiveService;

	@Autowired
	private InspectionResultService inspectionResultService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
    private DeviceExceptionService deviceExceptionService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("management:ordermanagement:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderManagementService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{orderId}")
    @RequiresPermissions("management:ordermanagement:info")
    public R info(@PathVariable("orderId") Integer orderId){
			OrderManagementEntity orderManagement = orderManagementService.selectById(orderId);
			
			SysDeptEntity sysDeptEntity = sysDeptService.selectById(orderManagement.getDeptId());
        	orderManagement.setDeptName(sysDeptEntity.getName());
        	
        	if(orderManagement.getOrderType() ==0) {
        		orderManagement.setOrderTypeName("填报工单");
        	}else if(orderManagement.getOrderType() ==1) {
        		orderManagement.setOrderTypeName("缺陷填报工单");
        	}else if(orderManagement.getOrderType() ==2) {
        		orderManagement.setOrderTypeName("巡检异常工单");
        	}
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
			}else if(orderManagement.getOrderStatus()==14){
            	orderManagement.setOrderStatusName("!已受理待上报"); 
            }

		SysDeptEntity sysDeptEntity1 = sysDeptService.selectById(orderManagement.getDeptId());
        orderManagement.setDeptName(sysDeptEntity.getName());
		OrderExceptionEntity exception = orderExceptionService.selectById(orderManagement.getExceptionId());
		if(exception !=null){
			orderManagement.setExceptionName(exception.getName());
		}else{
			orderManagement.setExceptionName("");
		}
		OrderDefectiveEntity defectiveEntity = orderDefectiveService.selectById(orderManagement.getDefectiveId());
		if(defectiveEntity !=null) {
			orderManagement.setDefectiveDevice(defectiveEntity.getDefectiveDevice());
		}else {
			orderManagement.setDefectiveDevice("");
		}
        	
        	
        return R.ok().put("ordermanagement", orderManagement);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("management:ordermanagement:save")
    public R save(@RequestBody OrderManagementEntity orderManagement){
		SimpleDateFormat sdf=new SimpleDateFormat("yyMMdd");
		String newDate=sdf.format(new Date());
		List<OrderManagementEntity> list = orderManagementService.selectList(new EntityWrapper<OrderManagementEntity>().like("order_number",newDate).orderBy("order_id", false)); 
		String orderNumber = null;
		if(list.size()>0) {
			String order_number = list.get(0).getOrderNumber();
	        String numStr = order_number.substring(order_number.length()-3,order_number.length());
	        String newStr = numStr.replaceAll("^(0+)", "");
	        Integer num = Integer.parseInt(newStr);
			orderNumber = OrderUtils.orderManagementNumber(num);
		} else {
			orderNumber = OrderUtils.orderManagementNumber(list.size());
		}
    	orderManagement.setOrderNumber(orderNumber);
		orderManagement.setCreateTime(new Date());
		orderManagementService.insert(orderManagement);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("management:ordermanagement:update")
    public R update(@RequestBody OrderManagementEntity orderManagement){
			orderManagementService.updateById(orderManagement);

        return R.ok();
    }
    
    /*
     *  确认派单
     * */
    @RequestMapping("/orderupdate")
    @RequiresPermissions("management:ordermanagement:orderupdate")
    public R orderupdate(@RequestBody OrderManagementEntity orderManagement){
    	    orderManagement.setCreateTime(new Date()); 
    	    orderManagement.setOrderAcceptorOpinion(null);
			// orderManagementService.updateById(orderManagement);
			orderManagementService.updateAllColumnById(orderManagement);
			NewsEntity entity =  newsService.selectOne(new EntityWrapper<NewsEntity>().eq("news_number", orderManagement.getOrderNumber()));
			
			if(entity !=null) {
				NewsEntity newsEntity = new NewsEntity();
			    newsEntity.setUserId(orderManagement.getOrderAcceptorId());
			    newsEntity.setNewsName("您有一条已下发待受理的工单");
			    newsEntity.setNewsNumber(orderManagement.getOrderNumber());
			    newsEntity.setNewsType(3);
			    newsEntity.setUpdateTime(new Date());
				newsService.update(newsEntity, new EntityWrapper<NewsEntity>()
						.eq("news_number",orderManagement.getOrderNumber())
						);
			}else {
				// 确认派单 通知 受理人  
			    NewsEntity newsEntity = new NewsEntity();
			    newsEntity.setUserId(orderManagement.getOrderAcceptorId());
			    newsEntity.setNewsName("您有一条已下发待受理的工单");
			    newsEntity.setNewsNumber(orderManagement.getOrderNumber());
			    newsEntity.setNewsType(3);
			    newsEntity.setUpdateTime(new Date());
				newsService.insert(newsEntity);


			}
			Integer orderAcceptorId = orderManagement.getOrderAcceptorId();
			SysUserEntity userEntity = sysUserService.selectById(orderAcceptorId);
			if(!"".equals(userEntity.getMobile())) {
				
				JSONObject returnJson = new JSONObject();
				returnJson.put("news_name", "您有一条已下发待受理的工单");
				returnJson.put("news_number", orderManagement.getOrderNumber());
				
				String isOk = SendSms.ordersend(userEntity.getMobile(), returnJson);
				if(isOk.equals("ok")) { //发生成功
					HashMap<String,Object> map = new HashMap<String,Object>(); 
					map.put("isOk", 1);
					map.put("phone", userEntity.getMobile());
					map.put("content", "您有一条已下发待受理的工单");
					map.put("type", 2);
					map.put("createTiem", new Date());
					deviceExceptionService.insertSms(map); // 发送短信记录
				}else {
					HashMap<String,Object> map = new HashMap<String,Object>(); 
					map.put("isOk", 0);
					map.put("phone", userEntity.getMobile());
					map.put("content", "您有一条已下发待受理的工单");
					map.put("type", 2);
					map.put("createTiem", new Date());
					deviceExceptionService.insertSms(map); // 发送短信记录
				}
				DingdingSend.ordersend("尊敬的用户,您有一条已下发待受理的工单编号"+orderManagement.getOrderNumber() ,userEntity.getMobile());
			}
			String wechat = userEntity.getWechat();
			if(!"".equals(wechat)) { 
				TestMessage.ordersend(wechat, "您有一条已下发待受理的工单", orderManagement.getOrderNumber()); 
			}
			
			OrderRecordEntity record = new OrderRecordEntity();
			record.setNowTime(new Date()); // 当前时间
			record.setOrderNumber(orderManagement.getOrderNumber());
			record.setOrderOpinion("请"+orderManagement.getOrderAcceptor()+",安排处理!"); // 工单主题当结论
			record.setOrderPeople(orderManagement.getOrderApplicant());
			record.setOrderPeopleId(1);// 填报人
			record.setOrderType(orderManagement.getOrderType());
			orderRecordService.insert(record);
			
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("management:ordermanagement:delete")
    public R delete(@RequestBody Integer[] orderIds){

		List<OrderManagementEntity> orderManagementList = orderManagementService.selectBatchIds(Arrays.asList(orderIds));
		Integer[] integers = new Integer[orderIds.length];
		for(int i=0 ;i< orderManagementList.size();i++){
			if(orderManagementList.get(i).getOrderStatus() == 0){
				integers[i] = orderManagementList.get(i).getOrderId();
			}
		}
		orderManagementService.deleteBatchIds(Arrays.asList(integers));

        return R.ok();
    }
    
	/**
	 * 拒绝转单
	 */
	@RequestMapping("/disagreelower")
	@RequiresPermissions("management:ordermanagement:disagreelower")
    public R disagreelower(@RequestBody OrderManagementEntity orderManagement){
		OrderDefectiveEntity defectiveEntity = orderDefectiveService.selectById(orderManagement.getDefectiveId());
		String orderApplicantOpinion = orderManagement.getOrderApplicantOpinion();
		// 0 填报工单，1缺陷工单,2 巡检缺陷工单
		Integer orderType = orderManagement.getOrderType();
		if(orderType == 1){ // 填报缺陷工单
			// 修改通知
			NewsEntity newsEntity = new NewsEntity();
			newsEntity.setUserId(defectiveEntity.getDefectiveNameId());
			newsEntity.setNewsName("您有一条转工单被拒绝的填报缺陷");
			newsEntity.setNewsType(11);
			newsEntity.setNewsNumber(defectiveEntity.getDefectiveNumber());
			newsEntity.setUpdateTime(new Date());
			newsService.update(newsEntity,new EntityWrapper<NewsEntity>()
					.eq("news_number",orderManagement.getOrderNumber()));
			// 修改 缺陷工单为 拒绝中的状态
			defectiveEntity.setOrderStatus(3);// 被拒绝
			defectiveEntity.setOrderConfirmer(null);
			defectiveEntity.setOrderConfirmerId(null);
			defectiveEntity.setOrderConfirmerOpinion(orderManagement.getOrderApplicantOpinion());
			defectiveEntity.setConfirmedTime(null);
			defectiveEntity.setRequirementTime(null);
			orderDefectiveService.updateAllColumnById(defectiveEntity);
			
			SysUserEntity userEntity = sysUserService.selectById(defectiveEntity.getDefectiveNameId());
			if(!"".equals(userEntity.getMobile())) {
				
				JSONObject returnJson = new JSONObject();
				returnJson.put("news_name", "您有一条转工单被拒绝的填报缺陷");
				returnJson.put("news_number", orderManagement.getOrderNumber());
				
				String isOk = SendSms.ordersend(userEntity.getMobile(), returnJson);
				if(isOk.equals("ok")) { //发生成功
					HashMap<String,Object> map = new HashMap<String,Object>(); 
					map.put("isOk", 1);
					map.put("phone", userEntity.getMobile());
					map.put("content", "您有一条转工单被拒绝的填报缺陷");
					map.put("type", 2);
					map.put("createTiem", new Date());
					deviceExceptionService.insertSms(map); // 发送短信记录
				}else {
					HashMap<String,Object> map = new HashMap<String,Object>(); 
					map.put("isOk", 0);
					map.put("phone", userEntity.getMobile());
					map.put("content", "您有一条转工单被拒绝的填报缺陷");
					map.put("type", 2);
					map.put("createTiem", new Date());
					deviceExceptionService.insertSms(map); // 发送短信记录
				}
				DingdingSend.ordersend("尊敬的用户,您有一条转工单被拒绝的填报缺陷编号"+orderManagement.getOrderNumber(),userEntity.getMobile());
			}
			String wechat = userEntity.getWechat();
			if(!"".equals(wechat)) { 
				TestMessage.ordersend(wechat, "您有一条转工单被拒绝的填报缺陷", orderManagement.getOrderNumber()); 
			}

		}else if(orderType ==2){// 巡检缺陷工单
			// 修改通知
			NewsEntity newsEntity = new NewsEntity();
			newsEntity.setUserId(defectiveEntity.getDefectiveNameId());
			newsEntity.setNewsName("您有一条转工单被拒绝的巡检异常缺陷");
			newsEntity.setNewsType(12);
			newsEntity.setNewsNumber(defectiveEntity.getDefectiveNumber());
			newsEntity.setUpdateTime(new Date());
			newsService.update(newsEntity,new EntityWrapper<NewsEntity>()
					.eq("news_number",orderManagement.getOrderNumber()));
			
			// 修改 缺陷工单为 拒绝中的状态
			defectiveEntity.setOrderStatus(3);// 被拒绝
			defectiveEntity.setOrderConfirmer(null);
			defectiveEntity.setOrderConfirmerId(null);
			defectiveEntity.setOrderConfirmerOpinion(orderManagement.getOrderApplicantOpinion());
			defectiveEntity.setConfirmedTime(null);
			defectiveEntity.setRequirementTime(null);
			orderDefectiveService.updateAllColumnById(defectiveEntity);

			Integer resultId = defectiveEntity.getResultId();
			InspectionResultEntity inspectionResult = new InspectionResultEntity();
			inspectionResult.setId(resultId);
			inspectionResult.setStatus(3);// 被拒绝 
			inspectionResultService.updateById(inspectionResult);
			
			SysUserEntity userEntity = sysUserService.selectById(defectiveEntity.getDefectiveNameId());
			if(!"".equals(userEntity.getMobile())) {
				
				JSONObject returnJson = new JSONObject();
				returnJson.put("news_name", "您有一条转工单被拒绝的巡检异常缺陷");
				returnJson.put("news_number", orderManagement.getOrderNumber());
				
				String isOk = SendSms.ordersend(userEntity.getMobile(), returnJson);
				if(isOk.equals("ok")) { //发生成功
					HashMap<String,Object> map = new HashMap<String,Object>(); 
					map.put("isOk", 1);
					map.put("phone", userEntity.getMobile());
					map.put("content", "您有一条转工单被拒绝的巡检异常缺陷");
					map.put("type", 2);
					map.put("createTiem", new Date());
					deviceExceptionService.insertSms(map); // 发送短信记录
				}else {
					HashMap<String,Object> map = new HashMap<String,Object>(); 
					map.put("isOk", 0);
					map.put("phone", userEntity.getMobile());
					map.put("content", "您有一条转工单被拒绝的巡检异常缺陷");
					map.put("type", 2);
					map.put("createTiem", new Date());
					deviceExceptionService.insertSms(map); // 发送短信记录
				}
				DingdingSend.ordersend("尊敬的用户,您有一条转工单被拒绝的巡检异常缺陷编号"+orderManagement.getOrderNumber(),userEntity.getMobile());
			}
			String wechat = userEntity.getWechat();
			if(!"".equals(wechat)) { 
				TestMessage.ordersend(wechat, "您有一条转工单被拒绝的巡检异常缺陷", orderManagement.getOrderNumber()); 
			}
		}
		Integer defectiveId = orderManagement.getDefectiveId();


		boolean isok = orderManagementService.deleteById(orderManagement.getOrderId());// 删除工单
		return R.ok();
	}
	
	/**
     * 导出列表
     */
    @RequestMapping("/export")
    @RequiresPermissions("management:ordermanagement:export")
    public R export(@RequestParam Map<String, Object> params){
        List<OrderManagementEntity> all = orderManagementService.all(params);

        return R.ok().put("list", all);
    }
	
	
}
