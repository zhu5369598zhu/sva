package io.renren.modules.management.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.service.SysDeptService;
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

    @Autowired
    private SysDeptService sysDeptService;
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
			ExceptionEntity exception = exceptionService.selectById(orderManagement.getExceptionId());
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
			record.setOrderOpinion(orderManagement.getOrderConfirmerOpinion()); // 工单主题当结论
			record.setOrderPeople(orderManagement.getOrderConfirmer());
			record.setOrderPeopleId(3);//确认人
			record.setOrderType(orderManagement.getOrderType());
			
			orderRecordService.insert(record);

            //orderManagement.setDelayTime(new Date()); // 申请时间 改为 null
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
            record.setOrderOpinion("已消除，同意完结"); // 工单主题当结论
            record.setOrderPeople(orderManagement.getOrderConfirmerOpinion());
            record.setOrderPeopleId(3);//确认人
            record.setOrderType(orderManagement.getOrderType());
			
			orderRecordService.insert(record);
    		
    	}else if(orderManagement.getOrderStatus()== 14) { // 同意申请延期
    		orderManagement.setOrderStatus(2); // 待上报
    		NewsEntity newsEntity = new NewsEntity();
    		newsEntity.setUserId(orderManagement.getOrderAcceptorId());
    		newsEntity.setNewsType(8);
    		newsEntity.setNewsName("您有一条已上报待审核的工单同意申请延期");
    		newsEntity.setUpdateTime(new Date()); 
    		newsService.update(newsEntity, new EntityWrapper<NewsEntity>()
    				.eq("news_number", orderManagement.getOrderNumber())
    				.eq("news_type", 6)
    				);
    		OrderRecordEntity record = new OrderRecordEntity();
			record.setNowTime(new Date()); // 当前时间
			record.setOrderNumber(orderManagement.getOrderNumber());
			record.setOrderOpinion(orderManagement.getOrderConfirmerOpinion()); // 工单主题当结论
			record.setOrderPeople(orderManagement.getOrderConfirmer());
			record.setOrderPeopleId(3);//确认人
			record.setOrderType(orderManagement.getOrderType());
			
			orderRecordService.insert(record);
    	}else if(orderManagement.getOrderStatus()== 15) {// 不同意申请延期
    		orderManagement.setOrderStatus(2); // 待上报
    		NewsEntity newsEntity = new NewsEntity();
    		newsEntity.setUserId(orderManagement.getOrderAcceptorId());
    		newsEntity.setNewsType(8);
    		newsEntity.setNewsName("您有一条已上报待审核的工单不同意申请延期");
    		newsEntity.setUpdateTime(new Date()); 
    		newsService.update(newsEntity, new EntityWrapper<NewsEntity>()
    				.eq("news_number", orderManagement.getOrderNumber())
    				.eq("news_type", 6)
    				);
    		OrderRecordEntity record = new OrderRecordEntity();
			record.setNowTime(new Date()); // 当前时间
			record.setOrderNumber(orderManagement.getOrderNumber());
			record.setOrderOpinion(orderManagement.getOrderConfirmerOpinion()); // 工单主题当结论
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

        SimpleDateFormat sdf=new SimpleDateFormat("yyMMdd");
        String newDate=sdf.format(new Date());
        List<OrderManagementEntity> list = orderManagementConfirmService.selectList(new EntityWrapper<OrderManagementEntity>().like("order_number",newDate));
        String orderNubmer = OrderUtils.orderManagementNumber(list.size());
    	return R.ok().put("managementNumber", orderNubmer);
    }
    
    
    

}
