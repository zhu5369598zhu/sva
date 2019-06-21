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
import io.renren.modules.management.service.OrderManagementConfirmService;
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
    private ExceptionService exceptionService;
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
    		newsEntity.setNewsName("您有一条已上报待确认的工单日志被打回");
    		newsEntity.setUpdateTime(new Date()); 
    		newsService.update(newsEntity, new EntityWrapper<NewsEntity>()
    				.eq("news_number", orderManagement.getOrderNumber())
    				.eq("news_type", 6)
    				);
    		OrderRecordEntity record = new OrderRecordEntity();
			record.setNowTime(new Date()); // 当前时间
			record.setOrderNumber(orderManagement.getOrderNumber());
			record.setOrderOpinion("确认打回"+orderManagement.getOrderConfirmerOpinion()); // 工单主题当结论
			record.setOrderPeople(orderManagement.getOrderConfirmer());
			record.setOrderPeopleId(3);//确认人
			record.setOrderType(orderManagement.getOrderType());
			
			orderRecordService.insert(record);
    		
    	}else if(orderManagement.getOrderStatus() ==4) {
    		orderManagement.setConfirmedTime(new Date()); // 确认时间
    		orderManagement.setActualTime(new Date()); // 点击上报就是 实际完成时间
    		NewsEntity newsEntity = new NewsEntity();
    		newsEntity.setUserId(orderManagement.getOrderApplicantId());
    		newsEntity.setNewsType(7);
    		newsEntity.setNewsName("您有一条已确认待完结的工单日志");
    		newsEntity.setUpdateTime(new Date()); 
    		newsService.update(newsEntity, new EntityWrapper<NewsEntity>()
    				.eq("news_number", orderManagement.getOrderNumber())
    				.eq("news_type", 6)
    				);
    		
    		OrderRecordEntity record = new OrderRecordEntity();
			record.setNowTime(new Date()); // 当前时间
			record.setOrderNumber(orderManagement.getOrderNumber());
			record.setOrderOpinion("已确认处理完毕"); // 工单主题当结论
			record.setOrderPeople(orderManagement.getOrderConfirmer());
			record.setOrderPeopleId(3);//确认人
			record.setOrderType(orderManagement.getOrderType());
			
			orderRecordService.insert(record);
    		
    	}
    	
    	orderManagementConfirmService.updateById(orderManagement);
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
    
    /**
     * 工单编号
     */
    @RequestMapping("/managementNumber")
    @RequiresPermissions("management:ordermanagementconfirm:managementNumber")
    public R managementNumber() {
    	
        String orderNubmer = OrderUtils.orderDefectNumber();
        
    	return R.ok().put("managementNumber", orderNubmer);
    }
    
    
    

}
