package io.renren.modules.sys.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.annotation.SysLog;
import io.renren.common.utils.R;
import io.renren.modules.inspection.entity.DeviceEntity;
import io.renren.modules.inspection.entity.InspectionItemEntity;
import io.renren.modules.inspection.entity.InspectionLineEntity;
import io.renren.modules.inspection.entity.ZoneEntity;
import io.renren.modules.inspection.service.DeviceService;
import io.renren.modules.inspection.service.InspectionItemService;
import io.renren.modules.inspection.service.InspectionLineService;
import io.renren.modules.inspection.service.ZoneService;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysDeptService;
import io.renren.modules.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;



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

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private InspectionLineService inspectionLineService;

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private InspectionItemService inspectionItemService;

    @Autowired
    private DeviceService deviceService;
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
        if(sysDept.getParentId() == sysDept.getDeptId()){
            R.error(1,"父部门不能是自己");
        }
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
        if(sysDept.getParentId() == sysDept.getDeptId()){
            R.error(1,"父部门不能是自己");
        }
        Long deptId = sysDept.getDeptId();
        // 查询子部门
        List<Integer> deptIds = sysDeptService.queryRecursiveChildByParentId(deptId);
        if(deptIds.size() > 0){
            for(Integer id: deptIds){
                if(id == sysDept.getParentId().intValue()){
                   return R.error(1,"上级部门不能成为子级部门的下级");
                }
            }
        }
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
        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("dept_id", deptId);
        List<SysUserEntity> userEntityList = sysUserService.selectByMap(userMap);
        if(userEntityList.size() > 0){
            return R.error("该部门下面绑定了用户，不能删除");
        }
        List<InspectionLineEntity> inspectionLineEntityList = inspectionLineService.selectByMap(userMap);
        if(inspectionLineEntityList.size() > 0){
            return R.error("该部门下绑定了巡线，不能删除");
        }
        List<ZoneEntity> zoneEntityList = zoneService.selectByMap(userMap);
        if(zoneEntityList.size() > 0){
            return R.error("该部门下绑定了巡区，不能删除");
        }
        List<InspectionItemEntity> inspectionItemEntityList = inspectionItemService.selectByMap(userMap);
        if(inspectionItemEntityList.size() > 0){
            return R.error("该部门下绑定了巡点，不能删除");
        }
        List<DeviceEntity> deviceEntityList = deviceService.selectByMap(userMap);
        if(deviceEntityList.size() > 0){
            return R.error("该部门下绑定了设备，不能删除");
        }

        sysDeptService.deleteById(deptId);

        return R.ok();
    }

}
