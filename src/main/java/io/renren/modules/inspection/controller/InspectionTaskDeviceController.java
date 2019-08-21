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

import io.renren.modules.inspection.entity.InspectionTaskDeviceEntity;
import io.renren.modules.inspection.service.InspectionTaskDeviceService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-08-11 16:55:04
 */
@RestController
@RequestMapping("inspection/inspectiontaskdevice")
public class InspectionTaskDeviceController {
    @Autowired
    private InspectionTaskDeviceService inspectionTaskDeviceService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("inspection:inspectiontaskdevice:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = inspectionTaskDeviceService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("inspection:inspectiontaskdevice:info")
    public R info(@PathVariable("id") Integer id){
			InspectionTaskDeviceEntity inspectionTaskDevice = inspectionTaskDeviceService.selectById(id);

        return R.ok().put("inspectionTaskDevice", inspectionTaskDevice);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("inspection:inspectiontaskdevice:save")
    public R save(@RequestBody InspectionTaskDeviceEntity inspectionTaskDevice){
			inspectionTaskDeviceService.insert(inspectionTaskDevice);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("inspection:inspectiontaskdevice:update")
    public R update(@RequestBody InspectionTaskDeviceEntity inspectionTaskDevice){
			inspectionTaskDeviceService.updateById(inspectionTaskDevice);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("inspection:inspectiontaskdevice:delete")
    public R delete(@RequestBody Integer[] ids){
			inspectionTaskDeviceService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
