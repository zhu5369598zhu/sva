package io.renren.modules.management.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.management.entity.OrderRecordEntity;
import io.renren.modules.management.service.OrderRecordService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 工单操作记录
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-05-27 15:40:37
 */
@RestController
@RequestMapping("management/orderrecord")
public class OrderRecordController {
    @Autowired
    private OrderRecordService orderRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("management:orderrecord:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderRecordService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{recordId}")
    @RequiresPermissions("management:orderrecord:info")
    public R info(@PathVariable("recordId") Integer recordId){
			OrderRecordEntity orderRecord = orderRecordService.selectById(recordId);
						
        return R.ok().put("orderRecord", orderRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("management:orderrecord:save")
    public R save(@RequestBody OrderRecordEntity orderRecord){
			orderRecordService.insert(orderRecord);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("management:orderrecord:update")
    public R update(@RequestBody OrderRecordEntity orderRecord){
			orderRecordService.updateById(orderRecord);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("management:orderrecord:delete")
    public R delete(@RequestBody Integer[] recordIds){
			orderRecordService.deleteBatchIds(Arrays.asList(recordIds));

        return R.ok();
    }

}
