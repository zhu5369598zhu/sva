package io.renren.modules.inspection.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.inspection.entity.InspectedDateEntity;
import io.renren.modules.inspection.service.InspectedDateService;
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
 * @date 2019-12-04 11:35:22
 */
@RestController
@RequestMapping("inspection/inspecteddate")
public class InspectedDateController {
    @Autowired
    private InspectedDateService inspectedDateService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("inspection:inspecteddate:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = inspectedDateService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("inspection:inspecteddate:info")
    public R info(@PathVariable("id") Integer id){
			InspectedDateEntity inspectedDate = inspectedDateService.selectById(id);

        return R.ok().put("inspectedDate", inspectedDate);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("inspection:inspecteddate:save")
    public R save(@RequestBody InspectedDateEntity inspectedDate){
			inspectedDateService.insert(inspectedDate);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("inspection:inspecteddate:update")
    public R update(@RequestBody InspectedDateEntity inspectedDate){
			inspectedDateService.updateById(inspectedDate);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("inspection:inspecteddate:delete")
    public R delete(@RequestBody Integer[] ids){
			inspectedDateService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
