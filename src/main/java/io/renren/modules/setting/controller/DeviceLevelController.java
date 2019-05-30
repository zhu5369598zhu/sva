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

import io.renren.modules.setting.entity.DeviceLevelEntity;
import io.renren.modules.setting.service.DeviceLevelService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-01-21 19:32:48
 */
@RestController
@RequestMapping("setting/devicelevel")
public class DeviceLevelController {
    @Autowired
    private DeviceLevelService deviceLevelService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("setting:devicelevel:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = deviceLevelService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("setting:devicelevel:info")
    public R info(@PathVariable("id") Integer id){
			DeviceLevelEntity deviceLevel = deviceLevelService.selectById(id);

        return R.ok().put("deviceLevel", deviceLevel);
    }

    /**
     * 保存
     */
    @SysLog("保存设备等级")
    @RequestMapping("/save")
    @RequiresPermissions("setting:devicelevel:save")
    public R save(@RequestBody DeviceLevelEntity deviceLevel){
			deviceLevelService.insert(deviceLevel);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改设备等级")
    @RequestMapping("/update")
    @RequiresPermissions("setting:devicelevel:update")
    public R update(@RequestBody DeviceLevelEntity deviceLevel){
			deviceLevelService.updateById(deviceLevel);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除设备等级")
    @RequestMapping("/delete")
    @RequiresPermissions("setting:devicelevel:delete")
    public R delete(@RequestBody Integer[] ids){
			deviceLevelService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
