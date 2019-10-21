package io.renren.modules.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.utils.*;
import io.renren.modules.management.entity.DeviceMaintainEntity;
import io.renren.modules.management.entity.OrderDefectiveEntity;
import io.renren.modules.management.entity.OrderManagementEntity;
import io.renren.modules.management.entity.OrderRecordEntity;
import io.renren.modules.management.service.*;
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

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 工单管理表
 * 已上报待确认
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-05-21 11:00:21
 */
@RestController
@RequestMapping("management/ordermanagementconfirm")
public class OrderManagementConfirmController {
   
    @Autowired
    private OrderManagementConfirmService orderManagementConfirmService;

    @Autowired
    private NewsService newsService;
    
    @Autowired
    private OrderRecordService orderRecordService;
    
    @Autowired
    private OrderExceptionService orderExceptionService;

    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
	private SysUserService sysUserService;
	
	@Autowired
    private DeviceExceptionService deviceExceptionService;
	
	@Autowired
    private OrderDefectiveListService orderDefectiveListService;

	@Autowired
	private DeviceMaintainService deviceMaintainService;

	@Autowired
	private OrderDefectiveService orderDefectiveService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("management:ordermanagementconfirm:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderManagementConfirmService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{orderId}")
    @RequiresPermissions("management:ordermanagementconfirm:info")
    public R info(@PathVariable("orderId") Integer orderId){
			OrderManagementEntity orderManagement = orderManagementConfirmService.selectById(orderId);
            if(orderManagement.getOrderType() ==0) {
                orderManagement.setOrderTypeName("填报工单");
            }else if(orderManagement.getOrderType() ==1) {
                orderManagement.setOrderTypeName("缺陷工单");
            }else if(orderManagement.getOrderType() ==2) {
                orderManagement.setOrderTypeName("巡检缺陷工单");
            }
			OrderExceptionEntity exception = orderExceptionService.selectById(orderManagement.getExceptionId());
			if(exception !=null){
                orderManagement.setExceptionName(exception.getName());
            }else{
                orderManagement.setExceptionName("");
            }
            SysDeptEntity sysDeptEntity = sysDeptService.selectById(orderManagement.getDeptId());
            orderManagement.setDeptName(sysDeptEntity.getName());
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
			
        return R.ok().put("ordermanagement", orderManagement);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("management:ordermanagementconfirm:save")
    public R save(@RequestBody OrderManagementEntity orderManagement){
    		//orderManagement.setCreateTime(new Date()); 
    		orderManagementConfirmService.insert(orderManagement);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("management:ordermanagementconfirm:update")
    public R update(@RequestBody OrderManagementEntity orderManagement){
    	orderManagementConfirmService.updateById(orderManagement);

        return R.ok();
    }
    
    /*
     *  确认派单
     * */
    @RequestMapping("/orderupdate")
    @RequiresPermissions("management:ordermanagementconfirm:orderupdate")
    public R orderupdate(@RequestBody OrderManagementEntity orderManagement){
    	
    	if(orderManagement.getOrderStatus() ==7) { // 打回
    		NewsEntity newsEntity = new NewsEntity();
    		newsEntity.setUserId(orderManagement.getOrderAcceptorId());
    		newsEntity.setNewsType(8);
    		newsEntity.setNewsName("您有一条已上报待审核的工单被拒绝");
    		newsEntity.setUpdateTime(new Date()); 
    		newsService.update(newsEntity, new EntityWrapper<NewsEntity>()
    				.eq("news_number", orderManagement.getOrderNumber())
    				.eq("news_type", 6)
    				);
    		OrderRecordEntity record = new OrderRecordEntity();
			record.setNowTime(new Date()); // 当前时间
			record.setOrderNumber(orderManagement.getOrderNumber());
			record.setOrderOpinion("上报被拒绝，"+orderManagement.getOrderConfirmerOpinion()); // 工单主题当结论
			record.setOrderPeople(orderManagement.getOrderConfirmer());
			record.setOrderPeopleId(3);//确认人
			record.setOrderType(orderManagement.getOrderType());
			
			orderRecordService.insert(record);
			
			SysUserEntity userEntity = sysUserService.selectById(orderManagement.getOrderAcceptorId());
			if(!"".equals(userEntity.getMobile())) {
				
				JSONObject returnJson = new JSONObject();
				returnJson.put("news_name", "您有一条已上报待审核的工单被拒绝");
				returnJson.put("news_number", orderManagement.getOrderNumber());
				
				String isOk = SendSms.ordersend(userEntity.getMobile(), returnJson);
				if(isOk.equals("ok")) { // 发送成功
					HashMap<String,Object> map = new HashMap<String,Object>(); 
					map.put("isOk", 1);
					map.put("phone", userEntity.getMobile());
					map.put("content", "您有一条已上报待审核的工单被拒绝");
					map.put("type", 2);
					map.put("createTiem", new Date());
					deviceExceptionService.insertSms(map); // 发送短信记录
				}else {
					HashMap<String,Object> map = new HashMap<String,Object>(); 
					map.put("isOk", 0);
					map.put("phone", userEntity.getMobile());
					map.put("content", "您有一条已上报待审核的工单被拒绝");
					map.put("type", 2);
					map.put("createTiem", new Date());
					deviceExceptionService.insertSms(map); // 发送短信记录
				}
				DingdingSend.ordersend("尊敬的用户,您有一条已上报待审核的工单被拒绝编号"+orderManagement.getOrderNumber(),userEntity.getMobile());
			}
			String wechat = userEntity.getWechat();
			if(!"".equals(wechat)) { 
				TestMessage.ordersend(wechat, "您有一条已上报待审核的工单被拒绝", orderManagement.getOrderNumber()); 
			}
			
			
			orderManagement.setOrderConfirmerId(0);
            orderManagement.setOrderConfirmer(null); 
            orderManagement.setProcessingResult(null);
    	}else if(orderManagement.getOrderStatus() ==5) { // 已完结
    		orderManagement.setConfirmedTime(new Date()); // 确认时间
    		orderManagement.setActualTime(new Date()); // 点击上报就是 实际完成时间
            NewsEntity newsEntity = new NewsEntity();
            newsEntity.setNewsType(0);
            newsEntity.setUpdateTime(new Date());
            newsService.update(newsEntity, new EntityWrapper<NewsEntity>()
                    .eq("news_number", orderManagement.getOrderNumber()));

            OrderRecordEntity record = new OrderRecordEntity();
            record.setNowTime(new Date()); // 当前时间
            record.setOrderNumber(orderManagement.getOrderNumber());
            record.setOrderOpinion("同意完结"+orderManagement.getOrderConfirmerOpinion()); // 工单主题当结论
            record.setOrderPeople(orderManagement.getOrderConfirmer());
            record.setOrderPeopleId(3);//确认人
            record.setOrderType(orderManagement.getOrderType());
			orderRecordService.insert(record);
			Integer defectiveId = orderManagement.getDefectiveId();
			if(defectiveId != 0) { // 属于缺陷工单转成 工单
				// 生成设备维护记录表
				OrderDefectiveEntity orderDefective = orderDefectiveService.selectOne(new EntityWrapper<OrderDefectiveEntity>().eq("defective_number", orderManagement.getDefectiveNumber()));
				Integer deviceId = orderDefective.getDeviceId();
				// 生成记录表
				DeviceMaintainEntity deviceMaintainEntity = new DeviceMaintainEntity();
				deviceMaintainEntity.setDeviceId(deviceId);
				deviceMaintainEntity.setOrderNumber(orderManagement.getOrderNumber());
				deviceMaintainEntity.setDefectiveNumber(orderManagement.getDefectiveNumber());
				deviceMaintainEntity.setDefectiveTheme(orderManagement.getDefectiveTheme());
				deviceMaintainEntity.setConfirmedTime(orderManagement.getConfirmedTime());
				deviceMaintainService.insert(deviceMaintainEntity);

				OrderDefectiveEntity orderDefectiveEntity = orderDefectiveListService.selectById(defectiveId);
				Integer defectiveNameId = orderDefectiveEntity.getDefectiveNameId();
				String defectiveNumber = orderDefectiveEntity.getDefectiveNumber();
				
				SysUserEntity userEntity = sysUserService.selectById(defectiveNameId);
				if(!"".equals(userEntity.getMobile())) {
					
					JSONObject returnJson = new JSONObject();
					returnJson.put("news_name", "您有一条已完结的缺陷单");
					returnJson.put("news_number", defectiveNumber);
					
					String isOk = SendSms.ordersend(userEntity.getMobile(), returnJson);
					if(isOk.equals("ok")) { // 发送成功
						HashMap<String,Object> map = new HashMap<String,Object>(); 
						map.put("isOk", 1);
						map.put("phone", userEntity.getMobile());
						map.put("content", "您有一条已完结的缺陷单");
						map.put("type", 2);
						map.put("createTiem", new Date());
						deviceExceptionService.insertSms(map); // 发送短信记录
					}else {
						HashMap<String,Object> map = new HashMap<String,Object>(); 
						map.put("isOk", 0);
						map.put("phone", userEntity.getMobile());
						map.put("content", "您有一条已完结的缺陷单");
						map.put("type", 2);
						map.put("createTiem", new Date());
						deviceExceptionService.insertSms(map); // 发送短信记录
					}
					
				}
				String wechat = userEntity.getWechat();
				if(!"".equals(wechat)) { 
					TestMessage.ordersend(wechat, "您有一条已完结的缺陷单", defectiveNumber); 
				}
				Integer orderApplicantId = orderManagement.getOrderApplicantId();
				Integer orderAcceptorId = orderManagement.getOrderAcceptorId();
				Integer orderConfirmerId = orderManagement.getOrderConfirmerId();
				HashMap<Integer,Object> map = new HashMap<Integer, Object>();
				map.put(orderApplicantId, orderApplicantId);
				map.put(orderAcceptorId, orderAcceptorId);
				map.put(orderConfirmerId, orderConfirmerId);
				for(Integer userId :map.keySet()) {
					SysUserEntity user = sysUserService.selectById(userId);
					if(!"".equals(user.getMobile())) {
						JSONObject returnJson = new JSONObject();
						returnJson.put("news_name", "您有一条已完结的工单");
						returnJson.put("news_number", orderManagement.getOrderNumber());
						
						String isOk = SendSms.ordersend(user.getMobile(), returnJson);
						if(isOk.equals("ok")) { // 发送成功
							HashMap<String,Object> hashMap = new HashMap<String,Object>(); 
							hashMap.put("isOk", 1);
							hashMap.put("phone", user.getMobile());
							hashMap.put("content", "您有一条已完结的工单");
							hashMap.put("type", 2);
							hashMap.put("createTiem", new Date());
							deviceExceptionService.insertSms(hashMap); // 发送短信记录
						}else {
							HashMap<String,Object> hashMap = new HashMap<String,Object>(); 
							hashMap.put("isOk", 0);
							hashMap.put("phone", user.getMobile());
							hashMap.put("content", "您有一条已完结的工单");
							hashMap.put("type", 2);
							hashMap.put("createTiem", new Date());
							deviceExceptionService.insertSms(hashMap); // 发送短信记录
						}
						DingdingSend.ordersend("尊敬的用户,您有一条已完结的工单编号"+orderManagement.getOrderNumber(),user.getMobile());
					}
					
					String touser = user.getWechat();
					if(!"".equals(touser)) { 
						TestMessage.ordersend(touser, "您有一条已完结的工单", orderManagement.getOrderNumber()); 
					}
				}
				
			}else {
				
				Integer orderApplicantId = orderManagement.getOrderApplicantId();
				Integer orderAcceptorId = orderManagement.getOrderAcceptorId();
				Integer orderConfirmerId = orderManagement.getOrderConfirmerId();
				HashMap<Integer,Object> map = new HashMap<Integer, Object>();
				map.put(orderApplicantId, orderApplicantId);
				map.put(orderAcceptorId, orderAcceptorId);
				map.put(orderConfirmerId, orderConfirmerId);
				for(Integer userId :map.keySet()) {
					SysUserEntity user = sysUserService.selectById(userId);
					if(!"".equals(user.getMobile())) {
						JSONObject returnJson = new JSONObject();
						returnJson.put("news_name", "您有一条已完结的工单");
						returnJson.put("news_number", orderManagement.getOrderNumber());
						
						String isOk = SendSms.ordersend(user.getMobile(), returnJson);
						if(isOk.equals("ok")) { // 发送成功
							HashMap<String,Object> hashMap = new HashMap<String,Object>(); 
							hashMap.put("isOk", 1);
							hashMap.put("phone", user.getMobile());
							hashMap.put("content", "您有一条已完结的工单");
							hashMap.put("type", 2);
							hashMap.put("createTiem", new Date());
							deviceExceptionService.insertSms(hashMap); // 发送短信记录
						}else {
							HashMap<String,Object> hashMap = new HashMap<String,Object>(); 
							hashMap.put("isOk", 0);
							hashMap.put("phone", user.getMobile());
							hashMap.put("content", "您有一条已完结的工单");
							hashMap.put("type", 2);
							hashMap.put("createTiem", new Date());
							deviceExceptionService.insertSms(hashMap); // 发送短信记录
						}
						DingdingSend.ordersend("尊敬的用户,您有一条已完结的工单编号"+orderManagement.getOrderNumber(),user.getMobile());
					}
					String touser = user.getWechat();
					if(!"".equals(touser)) { 
						TestMessage.ordersend(touser, "您有一条已完结的工单", orderManagement.getOrderNumber()); 
					}
				}
			}
			
			
    	}else if(orderManagement.getOrderStatus()== 14) { // 同意申请延期
    		orderManagement.setOrderStatus(2); // 待上报
    		NewsEntity newsEntity = new NewsEntity();
    		newsEntity.setUserId(orderManagement.getOrderAcceptorId());
    		newsEntity.setNewsType(8);
    		newsEntity.setNewsName("您有一条申请延期通过的工单待处理");
    		newsEntity.setUpdateTime(new Date()); 
    		newsService.update(newsEntity, new EntityWrapper<NewsEntity>()
    				.eq("news_number", orderManagement.getOrderNumber())
    				.eq("news_type", 14)
    				);
    		OrderRecordEntity record = new OrderRecordEntity();
			record.setNowTime(new Date()); // 当前时间
			record.setOrderNumber(orderManagement.getOrderNumber());
			record.setOrderOpinion("同意申请延期，"+orderManagement.getOrderApplicantOpinion()); // 工单主题当结论
			record.setOrderPeople(orderManagement.getOrderApplicant());
			record.setOrderPeopleId(1);//确认人
			record.setOrderType(orderManagement.getOrderType());
			
			orderRecordService.insert(record);
			SysUserEntity userEntity = sysUserService.selectById(orderManagement.getOrderAcceptorId());
			if(!"".equals(userEntity.getMobile())) {
				
				JSONObject returnJson = new JSONObject();
				returnJson.put("news_name", "您有一条申请延期通过的工单待处理");
				returnJson.put("news_number", orderManagement.getOrderNumber());
				
				String isOk = SendSms.ordersend(userEntity.getMobile(), returnJson);
				if(isOk.equals("ok")) { // 发送成功
					HashMap<String,Object> map = new HashMap<String,Object>(); 
					map.put("isOk", 1);
					map.put("phone", userEntity.getMobile());
					map.put("content", "您有一条申请延期通过的工单待处理");
					map.put("type", 2);
					map.put("createTiem", new Date());
					deviceExceptionService.insertSms(map); // 发送短信记录
				}else {
					HashMap<String,Object> map = new HashMap<String,Object>(); 
					map.put("isOk", 0);
					map.put("phone", userEntity.getMobile());
					map.put("content", "您有一条申请延期通过的工单待处理");
					map.put("type", 2);
					map.put("createTiem", new Date());
					deviceExceptionService.insertSms(map); // 发送短信记录
				}
				DingdingSend.ordersend("尊敬的用户,您有一条申请延期通过的工单待处理编号"+orderManagement.getOrderNumber(),userEntity.getMobile());
			}
			String touser = userEntity.getWechat();
			if(!"".equals(touser)) { 
				TestMessage.ordersend(touser, "您有一条申请延期通过的工单待处理", orderManagement.getOrderNumber()); 
			}
			
			orderManagement.setProcessingResult(null);
	    	orderManagement.setDelayTime(null);
	    	orderManagement.setOrderConfirmer(null);
	    	orderManagement.setOrderConfirmerId(0);
	    	orderManagement.setDisPlay(1); 
    	}else if(orderManagement.getOrderStatus()== 15) {// 不同意申请延期
    		orderManagement.setOrderStatus(2); // 待上报
    		NewsEntity newsEntity = new NewsEntity();
    		newsEntity.setUserId(orderManagement.getOrderAcceptorId());
    		newsEntity.setNewsType(8);
    		newsEntity.setNewsName("您有一条申请延期未通过的工单待处理");
    		newsEntity.setUpdateTime(new Date()); 
    		newsService.update(newsEntity, new EntityWrapper<NewsEntity>()
    				.eq("news_number", orderManagement.getOrderNumber())
    				.eq("news_type", 14)
    				);
    		OrderRecordEntity record = new OrderRecordEntity();
			record.setNowTime(new Date()); // 当前时间
			record.setOrderNumber(orderManagement.getOrderNumber());
			record.setOrderOpinion("不同意申请延期，"+orderManagement.getOrderApplicantOpinion()); // 工单主题当结论
			record.setOrderPeople(orderManagement.getOrderApplicant());
			record.setOrderPeopleId(1);//确认人
			record.setOrderType(orderManagement.getOrderType());
			
			orderRecordService.insert(record);
			SysUserEntity userEntity = sysUserService.selectById(orderManagement.getOrderAcceptorId());
			if(!"".equals(userEntity.getMobile())) {
				
				JSONObject returnJson = new JSONObject();
				returnJson.put("news_name", "您有一条申请延期未通过的工单待处理");
				returnJson.put("news_number", orderManagement.getOrderNumber());
				
				String isOk = SendSms.ordersend(userEntity.getMobile(), returnJson);
				if(isOk.equals("ok")) { // 发送成功
					HashMap<String,Object> map = new HashMap<String,Object>(); 
					map.put("isOk", 1);
					map.put("phone", userEntity.getMobile());
					map.put("content", "您有一条申请延期未通过的工单待处理");
					map.put("type", 2);
					map.put("createTiem", new Date());
					deviceExceptionService.insertSms(map); // 发送短信记录
				}else {
					HashMap<String,Object> map = new HashMap<String,Object>(); 
					map.put("isOk", 0);
					map.put("phone", userEntity.getMobile());
					map.put("content", "您有一条申请延期未通过的工单待处理");
					map.put("type", 2);
					map.put("createTiem", new Date());
					deviceExceptionService.insertSms(map); // 发送短信记录
				}
				DingdingSend.ordersend("尊敬的用户,您有一条申请延期未通过的工单待处理编号"+orderManagement.getOrderNumber(),userEntity.getMobile());
			}
			String touser = userEntity.getWechat();
			if(!"".equals(touser)) { 
				TestMessage.ordersend(touser, "您有一条申请延期未通过的工单待处理", orderManagement.getOrderNumber()); 
			}
			
			orderManagement.setProcessingResult(null);
	    	orderManagement.setDelayTime(null);
	    	orderManagement.setOrderConfirmer(null);
	    	orderManagement.setOrderConfirmerId(0);
	    	orderManagement.setDisPlay(1); 
    	}
    	
    	orderManagementConfirmService.updateAllColumnById(orderManagement);
    
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("management:ordermanagementconfirm:delete")
    public R delete(@RequestBody Integer[] orderIds){
    	orderManagementConfirmService.deleteBatchIds(Arrays.asList(orderIds));

        return R.ok();
    }
    
    
    
    
    

}
