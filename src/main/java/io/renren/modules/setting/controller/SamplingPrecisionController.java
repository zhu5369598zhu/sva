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

import io.renren.modules.setting.entity.SamplingPrecisionEntity;
import io.renren.modules.setting.service.SamplingPrecisionService;
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
@RequestMapping("setting/samplingprecision")
public class SamplingPrecisionController {
    @Autowired
    private SamplingPrecisionService samplingPrecisionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("setting:samplingprecision:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = samplingPrecisionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("setting:samplingprecision:info")
    public R info(@PathVariable("id") Integer id){
			SamplingPrecisionEntity samplingPrecision = samplingPrecisionService.selectById(id);

        return R.ok().put("samplingPrecision", samplingPrecision);
    }

    /**
     * 保存
     */
    @SysLog("保存采样点数")
    @RequestMapping("/save")
    @RequiresPermissions("setting:samplingprecision:save")
    public R save(@RequestBody SamplingPrecisionEntity samplingPrecision){
			samplingPrecisionService.insert(samplingPrecision);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改采样点数")
    @RequestMapping("/update")
    @RequiresPermissions("setting:samplingprecision:update")
    public R update(@RequestBody SamplingPrecisionEntity samplingPrecision){
			samplingPrecisionService.updateById(samplingPrecision);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除采样点数")
    @RequestMapping("/delete")
    @RequiresPermissions("setting:samplingprecision:delete")
    public R delete(@RequestBody Integer[] ids){
			samplingPrecisionService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
