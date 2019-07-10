package io.renren.modules.management.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import io.renren.modules.setting.entity.OrderExceptionEntity;
import io.renren.modules.setting.service.OrderExceptionService;
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
import io.renren.modules.management.service.OrderManagementAlreadyService;
import io.renren.modules.management.service.OrderRecordService;
import io.renren.modules.sys.entity.NewsEntity;
import io.renren.modules.sys.service.NewsService;
import io.renren.common.utils.OrderUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 工单管理表
 * 已下发待受理
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-05-21 11:00:21
 */
@RestController
@RequestMapping("management/ordermanagementalready")
public class OrderManagementAlreadyController {
   
    @Autowired
    private OrderManagementAlreadyService orderManagementAlreadyService;
    
    @Autowired
    private NewsService newsService;
    
    @Autowired
    private OrderRecordService orderRecordService;

    @Autowired
    private OrderExceptionService orderExceptionService;

    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("management:ordermanagementalready:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderManagementAlreadyService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{orderId}")
    @RequiresPermissions("management:ordermanagementalready:info")
    public R info(@PathVariable("orderId") Integer orderId){
        OrderManagementEntity orderManagement = orderManagementAlreadyService.selectById(orderId);
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
        }

        return R.ok().put("ordermanagement", orderManagement);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("management:ordermanagementalready:save")
    public R save(@RequestBody OrderManagementEntity orderManagement){
    		orderManagement.setCreateTime(new Date()); 
    		orderManagementAlreadyService.insert(orderManagement);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("management:ordermanagementalready:update")
    public R update(@RequestBody OrderManagementEntity orderManagement){
    	orderManagementAlreadyService.updateById(orderManagement);

        return R.ok();
    }
    
    /*
     *  确认派单
     * */
    @RequestMapping("/orderupdate")
    @RequiresPermissions("management:ordermanagementalready:orderupdate")
    public R orderupdate(@RequestBody OrderManagementEntity orderManagement){
    	
    	orderManagementAlreadyService.updateById(orderManagement);
    	
    	Integer orderStatus = orderManagement.getOrderStatus();
    	if(orderStatus ==6) { //拒绝受理 通知 填报人
    		NewsEntity newsEntity = new NewsEntity();
    		newsEntity.setUserId(orderManagement.getOrderApplicantId());
    		newsEntity.setNewsName("您有一条确认派单的工单被驳回");
    		newsEntity.setNewsNumber(orderManagement.getOrderNumber());
    		newsEntity.setNewsType(4); // 已下发被被驳回
    		newsEntity.setUpdateTime(new Date()); 
    		newsService.update(newsEntity, new EntityWrapper<NewsEntity>()
    				.eq("news_number",orderManagement.getOrderNumber())
    				.eq("user_id", orderManagement.getOrderAcceptorId())
    				.eq("news_type", 3)
    				);
    		
    		//记录流程
    		OrderRecordEntity record = new OrderRecordEntity();
			record.setNowTime(new Date()); // 当前时间
			record.setOrderNumber(orderManagement.getOrderNumber());
			record.setOrderOpinion("拒绝受理"+orderManagement.getOrderAcceptorOpinion()); // 工单主题当结论
			record.setOrderPeople(orderManagement.getOrderAcceptor());
			record.setOrderPeopleId(2);// 受理人
			record.setOrderType(orderManagement.getOrderType());
			orderRecordService.insert(record);
    		
    		
    	}else if(orderStatus ==2) { // 同意
    		NewsEntity entity = new NewsEntity();
        	entity.setUpdateTime(new Date()); 
        	entity.setNewsName("您有一条已受理待上报的工单");
    		entity.setNewsType(5);
    		newsService.update(entity, new EntityWrapper<NewsEntity>()
    				.eq("news_number",orderManagement.getOrderNumber())
    				.eq("user_id", orderManagement.getOrderAcceptorId())
    				.eq("news_type", 3)
    				);
    		OrderRecordEntity record = new OrderRecordEntity();
			record.setNowTime(new Date()); // 当前时间
			record.setOrderNumber(orderManagement.getOrderNumber());
			record.setOrderOpinion("同意受理"+orderManagement.getOrderAcceptorOpinion()); // 工单主题当结论
			record.setOrderPeople(orderManagement.getOrderAcceptor());
			record.setOrderPeopleId(2);// 受理人
			record.setOrderType(orderManagement.getOrderType());
			orderRecordService.insert(record);
    	}
    	
    	
    	
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("management:ordermanagementalready:delete")
    public R delete(@RequestBody Integer[] orderIds){
    	orderManagementAlreadyService.deleteBatchIds(Arrays.asList(orderIds));

        return R.ok();
    }
    
    /**
     * 工单编号
     */
    @RequestMapping("/managementNumber")
    @RequiresPermissions("management:ordermanagementalready:managementNumber")
    public R managementNumber() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyMMdd");
        String newDate=sdf.format(new Date());
        List<OrderManagementEntity> list = orderManagementAlreadyService.selectList(new EntityWrapper<OrderManagementEntity>().like("order_number",newDate));
        String orderNubmer = OrderUtils.orderManagementNumber(list.size());
    	return R.ok().put("managementNumber", orderNubmer);
    }
    
    
    

}
