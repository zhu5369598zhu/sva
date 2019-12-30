package io.renren.modules.inspection.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.inspection.entity.InspectionTaskEntity;
import io.renren.modules.inspection.service.InspectionTaskService;
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
@RequestMapping("inspection/inspectiontask")
public class InspectionTaskController {
    @Autowired
    private InspectionTaskService inspectionTaskService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("inspection:inspectiontask:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = inspectionTaskService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 获取完成率
     */
    @RequestMapping("/getstatus")
    public R getstatus(String date){
        List<Map<String,Object>> statusList = inspectionTaskService.selectByDate(date);

        return R.ok().put("status", statusList);
    }

    /**
     * 获取缺勤数据
     */
    @RequestMapping("/getabsencebydate")
    public R getAbsenceByDate(@RequestParam Map<String, Object> params){
        PageUtils page = inspectionTaskService.getAbsenceByDate(params);

        return R.ok().put("page", page);
    }

    /**
     * 获取缺勤数据导出
     */
    @RequestMapping("/getallabsencebydate")
    public R getAllAbsenceByDate(@RequestParam Map<String, Object> params){
        List<Map<String,Object>> list = inspectionTaskService.getAllAbsenceByDate(params);

        return R.ok().put("list", list);
    }

    /**
     * 获取缺勤数据图表
     */
    @RequestMapping("/getabsencestatisticsbydate")
    public R getAbsenceStatisticsByDate(@RequestParam Map<String, Object> params){
        Map<String,Object> data = inspectionTaskService.getAbsenceStatisticsByDate(params);

        return R.ok().put("data", data);
    }

    /**
     * 获取缺勤率统计数据
     */
    @RequestMapping("/getlinebydate")
    public R getLineByDate(@RequestParam Map<String, Object> params){
        PageUtils page = inspectionTaskService.getLineByDate(params);

        return R.ok().put("page", page);
    }

    /**
     * 获取缺勤率统计导出（不分页）
     */
    @RequestMapping("/getalllinebydate")
    public R getAllLineByDate(@RequestParam Map<String, Object> params){
        List<Map<String,Object>> list = inspectionTaskService.getAllLineByDate(params);

        return R.ok().put("list", list);
    }

    /**
     * 获取缺勤率统计图表
     */
    @RequestMapping("/getlinestatisticsbydate")
    public R getLineStatisticsByDate(@RequestParam Map<String, Object> params){
        Map<String,Object> data = inspectionTaskService.getLineStatisticsByDate(params);

        return R.ok().put("data", data);
    }

    /**
     * 获取缺勤周期表数据
     */
    @RequestMapping("/getturnbydate")
    public R getTurnByDate(@RequestParam Map<String, Object> params){
        PageUtils page = inspectionTaskService.getTurnByDate(params);

        return R.ok().put("page", page);
    }

    /**
     * 获取缺勤周期表导出
     */
    @RequestMapping("/getallturnbydate")
    public R getAllTurnByDate(@RequestParam Map<String, Object> params){
        List<Map<String,Object>> list = inspectionTaskService.getAllTurnByDate(params);

        return R.ok().put("list", list);
    }

    /**
     * 获取缺勤周期表图表
     */
    @RequestMapping("/getturnstatisticsbydate")
    public R getTurnStatisticsByDate(@RequestParam Map<String, Object> params){
        Map<String,Object> data = inspectionTaskService.getTurnStatisticsByDate(params);

        return R.ok().put("data", data);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("inspection:inspectiontask:info")
    public R info(@PathVariable("id") Integer id){
			InspectionTaskEntity inspectionTask = inspectionTaskService.selectById(id);

        return R.ok().put("inspectionTask", inspectionTask);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("inspection:inspectiontask:save")
    public R save(@RequestBody InspectionTaskEntity inspectionTask){
			inspectionTaskService.insert(inspectionTask);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("inspection:inspectiontask:update")
    public R update(@RequestBody InspectionTaskEntity inspectionTask){
			inspectionTaskService.updateById(inspectionTask);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("inspection:inspectiontask:delete")
    public R delete(@RequestBody Integer[] ids){
			inspectionTaskService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }
    /**
     *  获取线路统计
     * */

    @RequestMapping("/getlinebytime")
    public R getLineByTime(@RequestParam Map<String, Object> params){
        PageUtils page = inspectionTaskService.getLineByTime(params);

        return R.ok().put("page", page);
    }

}
