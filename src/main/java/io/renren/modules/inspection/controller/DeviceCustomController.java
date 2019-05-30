package io.renren.modules.inspection.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.inspection.entity.DeviceCustomEntity;
import io.renren.modules.inspection.service.DeviceCustomService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-04-26 12:20:16
 */
@RestController
@RequestMapping("inspection/devicecustom")
public class DeviceCustomController {
    @Autowired
    private DeviceCustomService deviceCustomService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("inspection:devicecustom:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = deviceCustomService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("inspection:devicecustom:info")
    public R info(@PathVariable("id") Integer id){
			DeviceCustomEntity deviceCustom = deviceCustomService.selectById(id);

        return R.ok().put("deviceCustom", deviceCustom);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("inspection:devicecustom:save")
    public R save(@RequestBody DeviceCustomEntity deviceCustom){
			deviceCustomService.insert(deviceCustom);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("inspection:devicecustom:update")
    public R update(@RequestBody DeviceCustomEntity deviceCustom){
			deviceCustomService.updateById(deviceCustom);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("inspection:devicecustom:delete")
    public R delete(@RequestBody Integer[] ids){
			deviceCustomService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
