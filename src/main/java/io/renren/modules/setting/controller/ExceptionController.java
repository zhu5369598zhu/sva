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

import io.renren.modules.setting.entity.ExceptionEntity;
import io.renren.modules.setting.service.ExceptionService;
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
@RequestMapping("setting/exception")
public class ExceptionController {
    @Autowired
    private ExceptionService exceptionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("setting:exception:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = exceptionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("setting:exception:info")
    public R info(@PathVariable("id") Integer id){
			ExceptionEntity exception = exceptionService.selectById(id);

        return R.ok().put("exception", exception);
    }

    /**
     * 保存
     */
    @SysLog("保存异常等级")
    @RequestMapping("/save")
    @RequiresPermissions("setting:exception:save")
    public R save(@RequestBody ExceptionEntity exception){
			exceptionService.insert(exception);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改异常等级")
    @RequestMapping("/update")
    @RequiresPermissions("setting:exception:update")
    public R update(@RequestBody ExceptionEntity exception){
			exceptionService.updateById(exception);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除异常等级")
    @RequestMapping("/delete")
    @RequiresPermissions("setting:exception:delete")
    public R delete(@RequestBody Integer[] ids){
			exceptionService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
