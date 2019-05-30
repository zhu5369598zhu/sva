package io.renren.modules.setting.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.common.annotation.SysLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.setting.entity.DeviceTypeEntity;
import io.renren.modules.setting.service.DeviceTypeService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-01-22 11:59:04
 */
@RestController
@RequestMapping("setting/devicetype")
public class DeviceTypeController {
    @Autowired
    private DeviceTypeService deviceTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("setting:devicetype:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = deviceTypeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("setting:devicetype:info")
    public R info(@PathVariable("id") Integer id){
			DeviceTypeEntity deviceType = deviceTypeService.selectById(id);

        return R.ok().put("deviceType", deviceType);
    }

    /**
     * 保存
     */
    @SysLog("保存设备类型")
    @RequestMapping("/save")
    @RequiresPermissions("setting:devicetype:save")
    public R save(@RequestBody DeviceTypeEntity deviceType){
			deviceTypeService.insert(deviceType);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改设备类型")
    @RequestMapping("/update")
    @RequiresPermissions("setting:devicetype:update")
    public R update(@RequestBody DeviceTypeEntity deviceType){
			deviceTypeService.updateById(deviceType);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除设备类型")
    @RequestMapping("/delete")
    @RequiresPermissions("setting:devicetype:delete")
    public R delete(@RequestBody Integer[] ids){
			deviceTypeService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
