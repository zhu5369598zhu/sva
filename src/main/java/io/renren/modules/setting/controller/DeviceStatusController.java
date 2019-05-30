package io.renren.modules.setting.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import io.renren.common.annotation.SysLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.setting.entity.DeviceStatusEntity;
import io.renren.modules.setting.service.DeviceStatusService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-01-22 11:36:34
 */
@RestController
@RequestMapping("setting/devicestatus")
public class DeviceStatusController {
    @Autowired
    private DeviceStatusService deviceStatusService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("setting:devicestatus:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = deviceStatusService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("setting:devicestatus:info")
    public R info(@PathVariable("id") Integer id){
			DeviceStatusEntity deviceStatus = deviceStatusService.selectById(id);

        return R.ok().put("deviceStatus", deviceStatus);
    }

    /**
     * 保存
     */
    @SysLog("保存设备状态")
    @RequestMapping("/save")
    @RequiresPermissions("setting:devicestatus:save")
    public R save(@RequestBody DeviceStatusEntity deviceStatus){
            deviceStatus.setGuid(UUID.randomUUID().toString());
			deviceStatusService.insert(deviceStatus);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改设备状态")
    @RequestMapping("/update")
    @RequiresPermissions("setting:devicestatus:update")
    public R update(@RequestBody DeviceStatusEntity deviceStatus){
			deviceStatusService.updateById(deviceStatus);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除设备状态")
    @RequestMapping("/delete")
    @RequiresPermissions("setting:devicestatus:delete")
    public R delete(@RequestBody Integer[] ids){
			deviceStatusService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
