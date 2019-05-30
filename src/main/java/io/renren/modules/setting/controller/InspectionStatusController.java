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

import io.renren.modules.setting.entity.InspectionStatusEntity;
import io.renren.modules.setting.service.InspectionStatusService;
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
@RequestMapping("setting/inspectionstatus")
public class InspectionStatusController {
    @Autowired
    private InspectionStatusService inspectionStatusService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("setting:inspectionstatus:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = inspectionStatusService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("setting:inspectionstatus:info")
    public R info(@PathVariable("id") Integer id){
			InspectionStatusEntity inspectionStatus = inspectionStatusService.selectById(id);

        return R.ok().put("inspectionStatus", inspectionStatus);
    }

    /**
     * 保存
     */
    @SysLog("保存巡检状态")
    @RequestMapping("/save")
    @RequiresPermissions("setting:inspectionstatus:save")
    public R save(@RequestBody InspectionStatusEntity inspectionStatus){
			inspectionStatusService.insert(inspectionStatus);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改巡检状态")
    @RequestMapping("/update")
    @RequiresPermissions("setting:inspectionstatus:update")
    public R update(@RequestBody InspectionStatusEntity inspectionStatus){
			inspectionStatusService.updateById(inspectionStatus);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除巡检状态")
    @RequestMapping("/delete")
    @RequiresPermissions("setting:inspectionstatus:delete")
    public R delete(@RequestBody Integer[] ids){
			inspectionStatusService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
