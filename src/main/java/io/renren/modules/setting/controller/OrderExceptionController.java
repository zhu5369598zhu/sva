package io.renren.modules.setting.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.setting.entity.OrderExceptionEntity;
import io.renren.modules.setting.service.OrderExceptionService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-07-02 15:22:39
 */
@RestController
@RequestMapping("setting/orderexception")
public class OrderExceptionController {
    @Autowired
    private OrderExceptionService orderExceptionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("setting:orderexception:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderExceptionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("setting:orderexception:info")
    public R info(@PathVariable("id") Integer id){
			OrderExceptionEntity orderException = orderExceptionService.selectById(id);

        return R.ok().put("orderException", orderException);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("setting:orderexception:save")
    public R save(@RequestBody OrderExceptionEntity orderException){
			orderExceptionService.insert(orderException);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("setting:orderexception:update")
    public R update(@RequestBody OrderExceptionEntity orderException){
			orderExceptionService.updateById(orderException);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("setting:orderexception:delete")
    public R delete(@RequestBody Integer[] ids){
			orderExceptionService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
