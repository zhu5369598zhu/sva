package io.renren.modules.management.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
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

import io.renren.modules.management.entity.OrderDefectiveEntity;
import io.renren.modules.management.entity.OrderManagementEntity;
import io.renren.modules.management.entity.OrderRecordEntity;
import io.renren.modules.management.service.OrderDefectiveService;
import io.renren.modules.management.service.OrderManagementService;
import io.renren.modules.management.service.OrderRecordService;
import io.renren.modules.setting.entity.ExceptionEntity;
import io.renren.modules.setting.service.ExceptionService;
import io.renren.modules.sys.entity.NewsEntity;
import io.renren.modules.sys.service.NewsService;
import io.renren.common.utils.OrderUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 缺陷工单表
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-05-31 15:16:09
 */
@RestController
@RequestMapping("management/orderdefective")
public class OrderDefectiveController {
    @Autowired
    private OrderDefectiveService orderDefectiveService;
    
    @Autowired
    private NewsService newsService;
    
    @Autowired
    private OrderManagementService orderManagementService;

	@Autowired
	private OrderExceptionService orderExceptionService;

	@Autowired
	private  SysDeptService sysDeptService;

    
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("management:orderdefective:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderDefectiveService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{defectiveId}")
    @RequiresPermissions("management:orderdefective:info")
    public R info(@PathVariable("defectiveId") Integer defectiveId){
			OrderDefectiveEntity orderDefective = orderDefectiveService.selectById(defectiveId);
			
			if(orderDefective.getOrderStatus() ==0) {
				orderDefective.setOrderStatusName("拟制中");
			}else if(orderDefective.getOrderStatus() ==1) {
				orderDefective.setOrderStatusName("待确认");
			}else if(orderDefective.getOrderStatus() ==2) {
				orderDefective.setOrderStatusName("已转");
			} 
			
			if(orderDefective.getDefectiveType() ==0) {
				orderDefective.setDefectiveTypeName("巡检缺陷异常");
			}else if(orderDefective.getDefectiveType() ==1) {
				orderDefective.setDefectiveTypeName("填报缺陷异常");
			}
		OrderExceptionEntity exception = orderExceptionService.selectById(orderDefective.getExceptionId());

		orderDefective.setExceptionName(exception.getName());

		SysDeptEntity sysDeptEntity = sysDeptService.selectById(orderDefective.getDeptId());
		orderDefective.setDeptName(sysDeptEntity.getName());

		return R.ok().put("orderdefective", orderDefective);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("management:orderdefective:save")
    public R save(@RequestBody OrderDefectiveEntity orderDefective){
		SimpleDateFormat sdf=new SimpleDateFormat("yyMMdd");
		String newDate=sdf.format(new Date());
		List<OrderDefectiveEntity> list = orderDefectiveService.selectList(new EntityWrapper<OrderDefectiveEntity>().like("defective_number", newDate));
		String orderNumber = OrderUtils.orderDefectNumber(list.size());
		orderDefective.setDefectiveNumber(orderNumber);
    	orderDefective.setCreateTime(new Date());
		orderDefectiveService.insert(orderDefective);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("management:orderdefective:update")
    public R update(@RequestBody OrderDefectiveEntity orderDefective){
			orderDefectiveService.updateById(orderDefective);

        return R.ok();
    }
    
    
    /**
     * 确认并派单
     */
    @RequestMapping("/orderupdate")
    @RequiresPermissions("management:orderdefective:orderupdate")
    public R orderupdate(@RequestBody OrderDefectiveEntity orderDefective){
    	orderDefectiveService.updateById(orderDefective);
		SimpleDateFormat sdf=new SimpleDateFormat("yyMMdd");
		String newDate=sdf.format(new Date());
		List<OrderManagementEntity> list = orderManagementService.selectList(new EntityWrapper<OrderManagementEntity>().like("order_number",newDate));

		String orderNumber = OrderUtils.orderManagementNumber(list.size()); // 工单编号
    	// 填报缺陷工单 转到 工单管理  
    	OrderManagementEntity managementEntity = new OrderManagementEntity();
    	managementEntity.setOrderNumber(orderNumber);
    	managementEntity.setDefectiveId(orderDefective.getDefectiveId());
    	managementEntity.setDefectiveName(orderDefective.getDefectiveName());
    	managementEntity.setDefectiveNumber(orderDefective.getDefectiveNumber());
    	managementEntity.setDefectiveTheme(orderDefective.getDefectiveTheme());
    	managementEntity.setOrderName(orderDefective.getDefectiveTheme());
    	managementEntity.setDeptId(orderDefective.getDeptId());
    	managementEntity.setOrderContent(orderDefective.getOrderContent());
    	managementEntity.setOrderApplicant(orderDefective.getOrderConfirmer());// 工单填报人
    	managementEntity.setOrderApplicantId(orderDefective.getOrderConfirmerId());//工单填报人id
		managementEntity.setOrderConfirmer(orderDefective.getOrderConfirmer());
		managementEntity.setOrderConfirmerId(orderDefective.getOrderConfirmerId());
		// managementEntity.setOrderAcceptor(orderDefective.getOrderAcceptor()); // 工单受理人
    	// managementEntity.setOrderAcceptorId(orderDefective.getOrderAcceptorId()); // 工单受理人id
    	// managementEntity.setOrderApplicantOpinion(orderDefective.getDefectiveNameOpinion());
    	managementEntity.setExceptionId(orderDefective.getExceptionId()); 
    	managementEntity.setCreateTime(new Date()); 
    	managementEntity.setRequirementTime(orderDefective.getRequirementTime());
    	managementEntity.setOrderStatus(9);
    	managementEntity.setOrderType(1); // 缺陷工单
    	orderManagementService.insert(managementEntity);
    	
    	// 进行通知
    	NewsEntity newsEntity = new NewsEntity();
	    newsEntity.setUserId(orderDefective.getOrderConfirmerId());
	    newsEntity.setNewsName("您有一条已转单待确认的工单");
	    newsEntity.setNewsNumber(orderNumber);
	    newsEntity.setNewsType(9);
	    newsEntity.setUpdateTime(new Date());
		newsEntity.setCreateTime(new Date());
		newsService.insert(newsEntity);

		// 修改之前的记录 为 0 ，使之成为 无效状态
		NewsEntity news = new NewsEntity();
		news.setNewsType(0);
		news.setUpdateTime(new Date());
		newsService.update(news, new EntityWrapper<NewsEntity>()
				.eq("news_number",orderDefective.getDefectiveNumber()));
		//进行记录
		/*OrderRecordEntity recordEntity = new OrderRecordEntity();
		recordEntity.setOrderNumber(orderNumber);
		recordEntity.setDefectiveId(orderDefective.getDefectiveId());
		recordEntity.setDefectiveNumber(orderDefective.getDefectiveNumber());
    	recordEntity.setOrderPeople(orderDefective.getOrderConfirmer());
		recordEntity.setOrderOpinion("请"+orderDefective.getOrderAcceptor()+"尽快处理!");
		recordEntity.setOrderPeopleId(1);
		recordEntity.setOrderType(1);
		recordEntity.setCreateTime(orderDefective.getCreateTime());
		recordEntity.setRequirementTime(orderDefective.getRequirementTime());
		recordEntity.setNowTime(new Date());
		orderRecordService.insert(recordEntity);*/
		
    	return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("management:orderdefective:delete")
    public R delete(@RequestBody Integer[] defectiveIds){
			orderDefectiveService.deleteBatchIds(Arrays.asList(defectiveIds));

        return R.ok();
    }
    
    /**
     * 缺陷单编号
     */
    @RequestMapping("/managementNumber")
    @RequiresPermissions("management:orderdefective:managementNumber")
    public R managementNumber() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyMMdd");
		String newDate=sdf.format(new Date());
		List<OrderDefectiveEntity> list = orderDefectiveService.selectList(new EntityWrapper<OrderDefectiveEntity>().like("defective_number",newDate));
		String orderNumber = OrderUtils.orderDefectNumber(list.size());
        
    	return R.ok().put("managementNumber", orderNumber);
    }
    
    
    
    

}
