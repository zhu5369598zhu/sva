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

import io.renren.modules.inspection.entity.DeviceRepairEntity;
import io.renren.modules.inspection.service.DeviceRepairService;
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
@RequestMapping("inspection/devicerepair")
public class DeviceRepairController {
    @Autowired
    private DeviceRepairService deviceRepairService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("inspection:devicerepair:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = deviceRepairService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("inspection:devicerepair:info")
    public R info(@PathVariable("id") Integer id){
			DeviceRepairEntity deviceRepair = deviceRepairService.selectById(id);

        return R.ok().put("deviceRepair", deviceRepair);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("inspection:devicerepair:save")
    public R save(@RequestBody DeviceRepairEntity deviceRepair){
			deviceRepairService.insert(deviceRepair);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("inspection:devicerepair:update")
    public R update(@RequestBody DeviceRepairEntity deviceRepair){
			deviceRepairService.updateById(deviceRepair);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("inspection:devicerepair:delete")
    public R delete(@RequestBody Integer[] ids){
			deviceRepairService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
