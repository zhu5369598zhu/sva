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

import io.renren.modules.setting.entity.SamplingFrequencyEntity;
import io.renren.modules.setting.service.SamplingFrequencyService;
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
@RequestMapping("setting/samplingfrequency")
public class SamplingFrequencyController {
    @Autowired
    private SamplingFrequencyService samplingFrequencyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("setting:samplingfrequency:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = samplingFrequencyService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("setting:samplingfrequency:info")
    public R info(@PathVariable("id") Integer id){
			SamplingFrequencyEntity samplingFrequency = samplingFrequencyService.selectById(id);

        return R.ok().put("samplingFrequency", samplingFrequency);
    }

    /**
     * 保存
     */
    @SysLog("保存采样频率")
    @RequestMapping("/save")
    @RequiresPermissions("setting:samplingfrequency:save")
    public R save(@RequestBody SamplingFrequencyEntity samplingFrequency){
			samplingFrequencyService.insert(samplingFrequency);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改采样频率")
    @RequestMapping("/update")
    @RequiresPermissions("setting:samplingfrequency:update")
    public R update(@RequestBody SamplingFrequencyEntity samplingFrequency){
			samplingFrequencyService.updateById(samplingFrequency);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除采样频率")
    @RequestMapping("/delete")
    @RequiresPermissions("setting:samplingfrequency:delete")
    public R delete(@RequestBody Integer[] ids){
			samplingFrequencyService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
