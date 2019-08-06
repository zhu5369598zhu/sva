package io.renren.modules.management.controller;

import java.util.*;

import io.renren.modules.setting.entity.OrderExceptionEntity;
import io.renren.modules.setting.service.OrderExceptionService;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.service.SysDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.management.entity.OrderDefectiveEntity;
import io.renren.modules.management.service.OrderDefectiveListService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 总缺陷列表
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-05-31 15:16:09
 */
@RestController
@RequestMapping("management/orderdefectivelist")
public class OrderDefectiveListController {
    
    @Autowired
    private OrderDefectiveListService orderDefectiveListService;
    
	@Autowired
	private OrderExceptionService orderExceptionService;

	@Autowired
	private  SysDeptService sysDeptService;

    
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("management:orderdefectivelist:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderDefectiveListService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{defectiveId}")
    @RequiresPermissions("management:orderdefectivelist:info")
    public R info(@PathVariable("defectiveId") Integer defectiveId){
			OrderDefectiveEntity orderDefective = orderDefectiveListService.selectById(defectiveId);
			
			if(orderDefective.getOrderStatus() ==0) {
				orderDefective.setOrderStatusName("拟制中");
			}else if(orderDefective.getOrderStatus() ==1) {
				orderDefective.setOrderStatusName("待确认");
			}else if(orderDefective.getOrderStatus() ==2) {
				orderDefective.setOrderStatusName("已转");
			}else if(orderDefective.getOrderStatus() ==3){
				orderDefective.setOrderStatusName("转工单被拒绝");
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

   
}
