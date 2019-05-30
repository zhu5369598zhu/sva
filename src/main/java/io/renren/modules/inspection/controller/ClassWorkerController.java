package io.renren.modules.inspection.controller;

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

import io.renren.modules.inspection.entity.ClassWorkerEntity;
import io.renren.modules.inspection.service.ClassWorkerService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-01-29 17:34:03
 */
@RestController
@RequestMapping("inspection/classworker")
public class ClassWorkerController {
    @Autowired
    private ClassWorkerService classWorkerService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("inspection:classworker:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = classWorkerService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("inspection:classworker:info")
    public R info(@PathVariable("id") Integer id){
			ClassWorkerEntity classWorker = classWorkerService.selectById(id);

        return R.ok().put("classWorker", classWorker);
    }

    /**
     * 保存
     */
    @SysLog("保存班组人员")
    @RequestMapping("/save")
    @RequiresPermissions("inspection:classworker:save")
    public R save(@RequestBody ClassWorkerEntity classWorker){
			classWorkerService.insert(classWorker);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改班组人员")
    @RequestMapping("/update")
    @RequiresPermissions("inspection:classworker:update")
    public R update(@RequestBody ClassWorkerEntity classWorker){
			classWorkerService.updateById(classWorker);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除班组人员")
    @RequestMapping("/delete")
    @RequiresPermissions("inspection:classworker:delete")
    public R delete(@RequestBody Integer[] ids){
			classWorkerService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
