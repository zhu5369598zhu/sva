package io.renren.modules.inspection.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.inspection.entity.InspectionTaskEntity;
import io.renren.modules.inspection.service.InspectionTaskService;
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
    @RequestMapping("/getStatus")
    public R getstatus(String date){
        List<Map<String,Object>> statusList = inspectionTaskService.selectByDate(date);

        return R.ok().put("status", statusList);
    }

    /**
     * 获取完成率
     */
    @RequestMapping("/selectListByDate")
    public R getstatus1(String date){
        List<Map<String,Object>> statusList = inspectionTaskService.selectByDate(date);

        return R.ok().put("status", statusList);
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

}
