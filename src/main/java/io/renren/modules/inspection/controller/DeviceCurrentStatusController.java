package io.renren.modules.inspection.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.inspection.entity.DeviceCurrentStatusEntity;
import io.renren.modules.inspection.service.DeviceCurrentStatusService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-12-03 10:55:02
 */
@RestController
@RequestMapping("inspection/devicecurrentstatus")
public class DeviceCurrentStatusController {
    @Autowired
    private DeviceCurrentStatusService deviceCurrentStatusService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("inspection:devicecurrentstatus:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = deviceCurrentStatusService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("inspection:devicecurrentstatus:info")
    public R info(@PathVariable("id") Integer id){
			DeviceCurrentStatusEntity deviceCurrentStatus = deviceCurrentStatusService.selectById(id);

        return R.ok().put("deviceCurrentStatus", deviceCurrentStatus);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("inspection:devicecurrentstatus:save")
    public R save(@RequestBody DeviceCurrentStatusEntity deviceCurrentStatus){
			deviceCurrentStatusService.insert(deviceCurrentStatus);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("inspection:devicecurrentstatus:update")
    public R update(@RequestBody DeviceCurrentStatusEntity deviceCurrentStatus){
			deviceCurrentStatusService.updateById(deviceCurrentStatus);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("inspection:devicecurrentstatus:delete")
    public R delete(@RequestBody Integer[] ids){
			deviceCurrentStatusService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
