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

import io.renren.modules.setting.entity.InspectionTypeEntity;
import io.renren.modules.setting.service.InspectionTypeService;
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
@RequestMapping("setting/inspectiontype")
public class InspectionTypeController {
    @Autowired
    private InspectionTypeService inspectionTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("setting:inspectiontype:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = inspectionTypeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("setting:inspectiontype:info")
    public R info(@PathVariable("id") Integer id){
			InspectionTypeEntity inspectionType = inspectionTypeService.selectById(id);

        return R.ok().put("inspectionType", inspectionType);
    }

    /**
     * 保存
     */
    @SysLog("保存巡检类型")
    @RequestMapping("/save")
    @RequiresPermissions("setting:inspectiontype:save")
    public R save(@RequestBody InspectionTypeEntity inspectionType){
			inspectionTypeService.insert(inspectionType);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改巡检类型")
    @RequestMapping("/update")
    @RequiresPermissions("setting:inspectiontype:update")
    public R update(@RequestBody InspectionTypeEntity inspectionType){
			inspectionTypeService.updateById(inspectionType);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("setting:inspectiontype:delete")
    public R delete(@RequestBody Integer[] ids){
			inspectionTypeService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
