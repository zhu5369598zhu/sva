package io.renren.modules.management.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.management.entity.DeviceMaintainEntity;
import io.renren.modules.management.service.DeviceMaintainService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 设备日常维护记录表
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-10-09 15:39:27
 */
@RestController
@RequestMapping("management/devicemaintain")
public class DeviceMaintainController {
    @Autowired
    private DeviceMaintainService deviceMaintainService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("management:devicemaintain:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = deviceMaintainService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("management:devicemaintain:info")
    public R info(@PathVariable("id") Integer id){
			DeviceMaintainEntity deviceMaintain = deviceMaintainService.selectById(id);

        return R.ok().put("deviceMaintain", deviceMaintain);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("management:devicemaintain:save")
    public R save(@RequestBody DeviceMaintainEntity deviceMaintain){
			deviceMaintainService.insert(deviceMaintain);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("management:devicemaintain:update")
    public R update(@RequestBody DeviceMaintainEntity deviceMaintain){
			deviceMaintainService.updateById(deviceMaintain);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("management:devicemaintain:delete")
    public R delete(@RequestBody Integer[] ids){
			deviceMaintainService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
