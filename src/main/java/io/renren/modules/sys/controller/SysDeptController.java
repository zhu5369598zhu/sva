package io.renren.modules.sys.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.annotation.SysLog;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.service.SysDeptService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 部门管理
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-01-17 17:04:27
 */
@RestController
@RequestMapping("sys/dept")
public class SysDeptController {
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:dept:list")
    public List<SysDeptEntity> list(@RequestParam Map<String, Object> params){
        String name = (String)params.get("name");

        List<SysDeptEntity> deptList = sysDeptService.selectList(new EntityWrapper<SysDeptEntity>()
                .like(StringUtils.isNotBlank(name),"name", name)
                .orderBy("order_num")
        );

        for(SysDeptEntity sysDeptEntity : deptList){
            SysDeptEntity parentDeptEntity = sysDeptService.selectById(sysDeptEntity.getParentId());
            if(parentDeptEntity != null){
                sysDeptEntity.setParentName(parentDeptEntity.getName());
            }
        }

        return deptList;
    }

    /**
     * 列表
     */
    @RequestMapping("/tree")
    public List<SysDeptEntity> tree(@RequestParam Map<String, Object> params){
        String name = (String)params.get("name");

        List<SysDeptEntity> deptList = sysDeptService.selectList(new EntityWrapper<SysDeptEntity>()
                .like(StringUtils.isNotBlank(name),"name", name)
                .orderBy("order_num")
        );

        for(SysDeptEntity sysDeptEntity : deptList){
            SysDeptEntity parentDeptEntity = sysDeptService.selectById(sysDeptEntity.getParentId());
            if(parentDeptEntity != null){
                sysDeptEntity.setParentName(parentDeptEntity.getName());
            }
        }

        return deptList;
    }


    /**
     * 选择菜单(添加、修改菜单)
     */
    @GetMapping("/select")
    @RequiresPermissions("sys:dept:select")
    public R select(){
        //查询列表数据
        List<SysDeptEntity> deptList = sysDeptService.selectList(null);

        //添加顶级菜单
        SysDeptEntity root = new SysDeptEntity();
        root.setDeptId(0L);
        root.setName("一级机构");
        root.setParentId(-1L);
        root.setOpen(true);
        deptList.add(root);

        return R.ok().put("deptList", deptList);
    }
    /**
     * 信息
     */
    @RequestMapping("/info/{deptId}")
    @RequiresPermissions("sys:dept:info")
    public R info(@PathVariable("deptId") Long deptId){
			SysDeptEntity sysDept = sysDeptService.selectById(deptId);

        return R.ok().put("sysDept", sysDept);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:dept:save")
    public R save(@RequestBody SysDeptEntity sysDept){
            sysDept.setGuid(UUID.randomUUID().toString());
			sysDeptService.insert(sysDept);

        return R.ok();
    }

    /**
     * 修改部门
     */
    @SysLog("修改部门")
    @RequestMapping("/update")
    @RequiresPermissions("sys:dept:update")
    public R update(@RequestBody SysDeptEntity sysDept){
			sysDeptService.updateById(sysDept);

        return R.ok();
    }

    /**
     * 删除部门
     */
    @SysLog("删除部门")
    @RequestMapping("/delete/{deptId}")
    @RequiresPermissions("sys:dept:delete")
    public R delete(@PathVariable("deptId") long deptId){
        List<Map<String,Object>> deptList =  sysDeptService.queryListParentId(deptId);
        if(deptList.size() > 0){
            return R.error("请先删除子部门");
        }

        sysDeptService.deleteById(deptId);

        return R.ok();
    }

}
