package io.renren.modules.inspection.controller;

import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import io.renren.common.annotation.SysLog;
import io.renren.modules.inspection.service.ClassWorkerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.inspection.entity.ClassGroupEntity;
import io.renren.modules.inspection.service.ClassGroupService;
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
@RequestMapping("inspection/classgroup")
public class ClassGroupController {
    @Autowired
    private ClassWorkerService classWorkerService;
    @Autowired
    private ClassGroupService classGroupService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("inspection:classgroup:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = classGroupService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/findlist")
    @RequiresPermissions("inspection:classgroup:list")
    public R findlist(@RequestParam Map<String, Object> params){
        String lineId = (String)params.get("lineId");
        List<ClassGroupEntity> classGroupEntityList = classGroupService.selectList(
                new EntityWrapper<ClassGroupEntity>()
                    .eq(lineId !=null, "inspection_line_id", lineId)

        );

        return R.ok().put("classGroupList", classGroupEntityList);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("inspection:classgroup:info")
    public R info(@PathVariable("id") Integer id){
			ClassGroupEntity classGroup = classGroupService.selectById(id);

			List<Long> workerList = classWorkerService.queryUserIdList(classGroup.getId());
			if (workerList != null) {
                classGroup.setWorkerList(workerList);
            }
        return R.ok().put("classGroup", classGroup);
    }

    /**
     * 保存
     */
    @SysLog("保存班组")
    @RequestMapping("/insert")
    @RequiresPermissions("inspection:classgroup:save")
    public R insert(@RequestParam Map<String, Object> params, @RequestBody Integer[] users, @RequestBody List<String> usernames){
        String name = (String)params.get("name");
        String inspectionLineId = (String)params.get("inspectionLineId");

        ClassGroupEntity classGroupEntity = new ClassGroupEntity();
        classGroupEntity.setName(name);
        return R.ok();
    }

    /**
     * 保存
     */
    @SysLog("保存班组")
    @RequestMapping("/save")
    @RequiresPermissions("inspection:classgroup:save")
    public R save(@RequestBody ClassGroupEntity classGroup){
            classGroup.setCreateTime(new Date());
            classGroup.setGuid(UUID.randomUUID().toString());
			classGroupService.save(classGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改班组")
    @RequestMapping("/update")
    @RequiresPermissions("inspection:classgroup:update")
    public R update(@RequestBody ClassGroupEntity classGroup){
			classGroupService.update(classGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除班组")
    @RequestMapping("/delete")
    @RequiresPermissions("inspection:classgroup:delete")
    public R delete(@RequestBody Long[] ids){
			classGroupService.deleteBatch(ids);

        return R.ok();
    }

}
