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

import io.renren.modules.setting.entity.UnitEntity;
import io.renren.modules.setting.service.UnitService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-01-29 17:38:32
 */
@RestController
@RequestMapping("setting/unit")
public class UnitController {
    @Autowired
    private UnitService unitService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("setting:unit:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = unitService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("setting:unit:info")
    public R info(@PathVariable("id") Integer id){
			UnitEntity unit = unitService.selectById(id);

        return R.ok().put("unit", unit);
    }

    /**
     * 保存
     */
    @SysLog("保存测量单位")
    @RequestMapping("/save")
    @RequiresPermissions("setting:unit:save")
    public R save(@RequestBody UnitEntity unit){
			unitService.insert(unit);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改测量单位")
    @RequestMapping("/update")
    @RequiresPermissions("setting:unit:update")
    public R update(@RequestBody UnitEntity unit){
			unitService.updateById(unit);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除测量单位")
    @RequestMapping("/delete")
    @RequiresPermissions("setting:unit:delete")
    public R delete(@RequestBody Integer[] ids){
			unitService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
