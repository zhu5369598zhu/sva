package io.renren.modules.management.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

import io.renren.modules.management.entity.OrderManagementEntity;
import io.renren.modules.management.entity.OrderRecordEntity;
import io.renren.modules.management.service.OrderManagementReportedService;
import io.renren.modules.management.service.OrderRecordService;
import io.renren.modules.setting.entity.ExceptionEntity;
import io.renren.modules.setting.service.ExceptionService;
import io.renren.modules.sys.entity.NewsEntity;
import io.renren.modules.sys.service.NewsService;
import io.renren.common.utils.OrderUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 工单管理表
 * 已受理待上报
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-05-21 11:00:21
 */
@RestController
@RequestMapping("management/ordermanagementreported")
public class OrderManagementReportedController {
   
    @Autowired
    private OrderManagementReportedService orderManagementReportedService;
    
    @Autowired
    private NewsService newsService;
    
    @Autowired
    private OrderRecordService orderRecordService;
    
    @Autowired
    private ExceptionService exceptionService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("management:ordermanagementreported:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderManagementReportedService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{orderId}")
    @RequiresPermissions("management:ordermanagementreported:info")
    public R info(@PathVariable("orderId") Integer orderId){
			OrderManagementEntity orderManagement = orderManagementReportedService.selectById(orderId);
			if(orderManagement.getOrderType() ==0) {
				orderManagement.setOrderTypeName("填报工单"); 
			}else if(orderManagement.getOrderType() ==1) {
				orderManagement.setOrderTypeName("缺陷工单");
			} 
			ExceptionEntity exception = exceptionService.selectById(orderManagement.getExceptionId());
			if(exception !=null){
                orderManagement.setExceptionName(exception.getName());
            }else{
                orderManagement.setExceptionName("");
            }
			
        return R.ok().put("ordermanagement", orderManagement);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("management:ordermanagementreported:save")
    public R save(@RequestBody OrderManagementEntity orderManagement){
    		orderManagement.setCreateTime(new Date()); 
    		orderManagementReportedService.insert(orderManagement);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("management:ordermanagementreported:update")
    public R update(@RequestBody OrderManagementEntity orderManagement){
    	orderManagementReportedService.updateById(orderManagement);

        return R.ok();
    }
    
    /*
     *  确认派单
     * */
    @RequestMapping("/orderupdate")
    @RequiresPermissions("management:ordermanagementreported:orderupdate")
    public R orderupdate(@RequestBody OrderManagementEntity orderManagement){
    	orderManagementReportedService.updateById(orderManagement);
    	
    	if(orderManagement.getOrderStatus() == 3) { // 上报
    		NewsEntity newsEntity = new NewsEntity();
    		newsEntity.setUserId(orderManagement.getOrderConfirmerId());
    		newsEntity.setNewsName("您有一条已上报待确认的工单日志");
    		newsEntity.setNewsType(6);
    		newsEntity.setUpdateTime(new Date());
    		newsService.update(newsEntity, new EntityWrapper<NewsEntity>()
    				.eq("news_number", orderManagement.getOrderNumber())
    				);
    		
    		OrderRecordEntity record = new OrderRecordEntity();
			record.setNowTime(new Date()); // 当前时间
			record.setOrderNumber(orderManagement.getOrderNumber());
			record.setOrderOpinion("已上报请"+orderManagement.getOrderConfirmer()+"确认"); // 工单主题当结论
			record.setOrderPeople(orderManagement.getOrderAcceptor());
			record.setOrderPeopleId(2);//受理人
			record.setOrderType(orderManagement.getOrderType());
			
			orderRecordService.insert(record);
    	}else {
    		OrderRecordEntity record = new OrderRecordEntity();
    		record.setNowTime(new Date()); // 当前时间
    		record.setOrderNumber(orderManagement.getOrderNumber());
    		record.setOrderOpinion("进行延时，延时到"+orderManagement.getDelayTime()); // 工单主题当结论
    		record.setOrderPeople(orderManagement.getOrderAcceptor());
    		record.setOrderPeopleId(2);//受理人
    		record.setOrderType(orderManagement.getOrderType());
    		
    		orderRecordService.insert(record);
    	}
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("management:ordermanagementreported:delete")
    public R delete(@RequestBody Integer[] orderIds){
    	orderManagementReportedService.deleteBatchIds(Arrays.asList(orderIds));

        return R.ok();
    }
    
    /**
     * 工单编号
     */
    @RequestMapping("/managementNumber")
    @RequiresPermissions("management:ordermanagementreported:managementNumber")
    public R managementNumber() {
    	
    	String orderNumber = OrderUtils.orderDefectNumber();
        
    	return R.ok().put("managementNumber", orderNumber);
    }
    
    
    

}
