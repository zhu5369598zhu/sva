package io.renren.modules.inspection.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.inspection.entity.InspectionTaskDeviceEntity;
import io.renren.modules.inspection.service.InspectionTaskDeviceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;



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
     * 获取缺勤详情
     */
    @RequestMapping("/getmissingbydate")
    public R getMissingByDate(@RequestParam Map<String, Object> params){
        PageUtils page = inspectionTaskDeviceService.getMissingByDate(params);

        return R.ok().put("page", page);
    }

    /**
     * 获取缺勤详情（不分页）
     */
    @RequestMapping("/getallmissingbydate")
    public R getAllMissingByDate(@RequestParam Map<String, Object> params){
        List<Map<String,Object>> list = inspectionTaskDeviceService.getAllMissingByDate(params);

        return R.ok().put("list", list);
    }

    /**
     * 获取缺勤统计
     */
    @RequestMapping("/getmissingstatisticsbydate")
    public R getMissingStatisticsByDate(@RequestParam Map<String, Object> params){
        Map<String,Object> data = inspectionTaskDeviceService.getMissingStatisticsByDate(params);

        return R.ok().put("data", data);
    }

    /**
     * 获取缺勤详情
     */
    @RequestMapping("/getlinebydate")
    public R getLineByDate(@RequestParam Map<String, Object> params){
        PageUtils page = inspectionTaskDeviceService.getLineByDate(params);

        return R.ok().put("page", page);
    }

    /**
     * 获取缺勤详情（不分页）
     */
    @RequestMapping("/getalllinebydate")
    public R getAllLineByDate(@RequestParam Map<String, Object> params){
        List<Map<String,Object>> list = inspectionTaskDeviceService.getAllLineByDate(params);

        return R.ok().put("list", list);
    }

    /**
     * 获取缺勤统计
     */
    @RequestMapping("/getlinestatisticsbydate")
    public R getLineStatisticsByDate(@RequestParam Map<String, Object> params){
        Map<String,Object> data = inspectionTaskDeviceService.getLineStatisticsByDate(params);

        return R.ok().put("data", data);
    }

    /**
     * 获取缺勤详情
     */
    @RequestMapping("/getturnbydate")
    public R getTurnByDate(@RequestParam Map<String, Object> params){
        PageUtils page = inspectionTaskDeviceService.getTurnByDate(params);

        return R.ok().put("page", page);
    }

    /**
     * 获取缺勤详情（不分页）
     */
    @RequestMapping("/getallturnbydate")
    public R getAllTurnByDate(@RequestParam Map<String, Object> params){
        List<Map<String,Object>> list = inspectionTaskDeviceService.getAllTurnByDate(params);

        return R.ok().put("list", list);
    }

    /**
     * 获取缺勤统计
     */
    @RequestMapping("/getturnstatisticsbydate")
    public R getTurnStatisticsByDate(@RequestParam Map<String, Object> params){
        Map<String,Object> data = inspectionTaskDeviceService.getTurnStatisticsByDate(params);

        return R.ok().put("data", data);
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
    /**
     *  设备耗时统计
     * */
    @RequestMapping("/getdevicebytime")
    public R getDeviceDate(@RequestParam Map<String, Object> params){
        PageUtils page = inspectionTaskDeviceService.getDeviceDate(params);
        return R.ok().put("page", page);
    }

}
