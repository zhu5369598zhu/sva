package io.renren.modules.inspection.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.renren.modules.inspection.entity.InspectionResultEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.inspection.entity.InspectionRandomResultEntity;
import io.renren.modules.inspection.service.InspectionRandomResultService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-03-15 11:43:31
 */
@RestController
@RequestMapping("inspection/inspectionrandomresult")
public class InspectionRandomResultController {
    @Autowired
    private InspectionRandomResultService inspectionRandomResultService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("inspection:inspectionrandomresult:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = inspectionRandomResultService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("inspection:inspectionrandomresult:info")
    public R info(@PathVariable("id") Integer id){
			InspectionRandomResultEntity inspectionRandomResult = inspectionRandomResultService.selectById(id);

        return R.ok().put("inspectionRandomResult", inspectionRandomResult);
    }
    /**
     * 列表
     */
    @RequestMapping("/query")
    @RequiresPermissions("inspection:inspectionrandomresult:list")
    public R query(@RequestParam Map<String, Object> params){
        PageUtils page = inspectionRandomResultService.selectResultPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 导出
     */
    @RequestMapping("/export")
    @RequiresPermissions("inspection:inspectionrandomresult:export")
    R export(@RequestParam Map<String, Object> params){
        List<InspectionRandomResultEntity> list = inspectionRandomResultService.selectAll(params);

        return R.ok().put("list", list);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("inspection:inspectionrandomresult:save")
    public R save(@RequestBody InspectionRandomResultEntity inspectionRandomResult){
			inspectionRandomResultService.insert(inspectionRandomResult);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("inspection:inspectionrandomresult:update")
    public R update(@RequestBody InspectionRandomResultEntity inspectionRandomResult){
			inspectionRandomResultService.updateById(inspectionRandomResult);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("inspection:inspectionrandomresult:delete")
    public R delete(@RequestBody Integer[] ids){
			inspectionRandomResultService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
