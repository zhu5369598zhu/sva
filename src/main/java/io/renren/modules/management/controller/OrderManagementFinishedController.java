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
import io.renren.modules.management.service.OrderManagementFinishedService;
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
 * 已确认待完结
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-05-21 11:00:21
 */
@RestController
@RequestMapping("management/ordermanagementfinished")
public class OrderManagementFinishedController {
   
    @Autowired
    private OrderManagementFinishedService orderManagementFinishedService;
   
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
    @RequiresPermissions("management:ordermanagementfinished:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderManagementFinishedService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{orderId}")
    @RequiresPermissions("management:ordermanagementfinished:info")
    public R info(@PathVariable("orderId") Integer orderId){
			OrderManagementEntity orderManagement = orderManagementFinishedService.selectById(orderId);
			if(orderManagement.getOrderType() ==0) {
				orderManagement.setOrderTypeName("填报工单");
			}else if(orderManagement.getOrderType() ==1) {
				orderManagement.setOrderTypeName("缺陷工单"); 
			}
			ExceptionEntity exception = exceptionService.selectById(orderManagement.getExceptionId());
            orderManagement.setExceptionName(exception.getName()); 
			
        return R.ok().put("ordermanagement", orderManagement);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("management:ordermanagementfinished:save")
    public R save(@RequestBody OrderManagementEntity orderManagement){
    		orderManagement.setCreateTime(new Date()); 
    		orderManagementFinishedService.insert(orderManagement);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("management:ordermanagementfinished:update")
    public R update(@RequestBody OrderManagementEntity orderManagement){
    	orderManagementFinishedService.updateById(orderManagement);

        return R.ok();
    }
    
    /*
     *  确认完结
     * */
    @RequestMapping("/orderupdate")
    @RequiresPermissions("management:ordermanagementfinished:orderupdate")
    public R orderupdate(@RequestBody OrderManagementEntity orderManagement){
    	orderManagementFinishedService.updateById(orderManagement);
    	
    	NewsEntity newsEntity = new NewsEntity();
    	newsEntity.setNewsType(0);
    	newsEntity.setUpdateTime(new Date());
    	newsService.update(newsEntity, new EntityWrapper<NewsEntity>()
    			.eq("news_number", orderManagement.getOrderNumber())); 
    	
    	OrderRecordEntity record = new OrderRecordEntity();
		record.setNowTime(new Date()); // 当前时间
		record.setOrderNumber(orderManagement.getOrderNumber());
		record.setOrderOpinion("已消除，同意完结"); // 工单主题当结论
		record.setOrderPeople(orderManagement.getOrderApplicant());
		record.setOrderPeopleId(1);//确认人
		record.setOrderType(orderManagement.getOrderType());
		
		orderRecordService.insert(record);
    	
    	
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("management:ordermanagementfinished:delete")
    public R delete(@RequestBody Integer[] orderIds){
    	orderManagementFinishedService.deleteBatchIds(Arrays.asList(orderIds));

        return R.ok();
    }
    
    /**
     * 工单编号
     */
    @RequestMapping("/managementNumber")
    @RequiresPermissions("management:ordermanagementfinished:managementNumber")
    public R managementNumber() {
    	
    	String orderNumber = OrderUtils.orderDefectNumber();
        
    	return R.ok().put("managementNumber", orderNumber);
    }
    
    
    

}
