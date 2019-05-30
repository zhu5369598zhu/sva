package io.renren.modules.inspection.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.inspection.entity.InspectionResultEntity;
import io.renren.modules.inspection.service.InspectionResultService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-02-13 11:25:57
 */
@RestController
@RequestMapping("inspection/inspectionresult")
public class InspectionResultController {
    @Autowired
    private InspectionResultService inspectionResultService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("inspection:inspectionresult:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = inspectionResultService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/query")
    @RequiresPermissions("inspection:inspectionresult:list")
    public R query(@RequestParam Map<String, Object> params){
        PageUtils page = inspectionResultService.selectResultPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/statistics")
    @RequiresPermissions("inspection:inspectionresult:list")
    public R statistics(@RequestParam Map<String, Object> params){
        Map<String, Object> data = inspectionResultService.selectException(params);

        return R.ok().put("data", data);
    }

    /**
     * 列表
     */
    @RequestMapping("/statisticsbydevice")
    @RequiresPermissions("inspection:inspectionresult:list")
    public R statisticsByDevice(@RequestParam Map<String, Object> params){
        Map<String, Object> data = inspectionResultService.selectExceptionGroupByDevice(params);

        return R.ok().put("data", data);
    }

    /**
     * 列表
     */
    @RequestMapping("/statisticsbyitem")
    @RequiresPermissions("inspection:inspectionresult:list")
    public R statisticsByItem(@RequestParam Map<String, Object> params){
        Map<String, Object> data = inspectionResultService.selectExceptionGroupByItem(params);

        return R.ok().put("data", data);
    }


    /**
     * 列表
     */
    @RequestMapping("/selectbyitem")
    @RequiresPermissions("inspection:inspectionresult:list")
    public R selectByItem(@RequestParam Map<String, Object> params){
        Map<String,Object> data = inspectionResultService.selectByItem(params);

        return R.ok().put("data", data);
    }
    /**
     * 导出
     */
    @RequestMapping("/export")
    @RequiresPermissions("inspection:inspectionresult:export")
    R export(@RequestParam Map<String, Object> params){
       List<InspectionResultEntity> list = inspectionResultService.selectAll(params);

        return R.ok().put("list", list);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("inspection:inspectionresult:info")
    public R info(@PathVariable("id") Integer id){
			InspectionResultEntity inspectionResult = inspectionResultService.selectById(id);

        return R.ok().put("inspectionResult", inspectionResult);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("inspection:inspectionresult:save")
    public R save(@RequestBody InspectionResultEntity inspectionResult){
        String guid = inspectionResultService.insertResult(inspectionResult);
        if(guid.equals("")){
            return R.error(400, "参数错误");
        } else {
            return R.ok().put("guid", guid);
        }
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("inspection:inspectionresult:update")
    public R update(@RequestBody InspectionResultEntity inspectionResult){
			inspectionResultService.updateById(inspectionResult);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("inspection:inspectionresult:delete")
    public R delete(@RequestBody Integer[] ids){
			inspectionResultService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
