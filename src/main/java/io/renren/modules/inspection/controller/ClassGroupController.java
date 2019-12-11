package io.renren.modules.inspection.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.annotation.SysLog;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.inspection.entity.ClassGroupEntity;
import io.renren.modules.inspection.entity.TurnClassGroupEntity;
import io.renren.modules.inspection.entity.TurnEntity;
import io.renren.modules.inspection.service.ClassGroupService;
import io.renren.modules.inspection.service.ClassWorkerService;
import io.renren.modules.inspection.service.TurnClassGroupService;
import io.renren.modules.inspection.service.TurnService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


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
    @Autowired
    private TurnClassGroupService turnClassGroupService;
    @Autowired
    private TurnService turnService;
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
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("name",classGroup.getName());
        paramsMap.put("inspection_line_id",classGroup.getInspectionLineId());
        List<ClassGroupEntity> classGroupEntityList = classGroupService.selectByMap(paramsMap);
        if(classGroupEntityList.size() > 0){
            return R.error(1,"同一线路中班组名称不能重复");
        }

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
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("name",classGroup.getName());
        paramsMap.put("inspection_line_id",classGroup.getInspectionLineId());
        List<ClassGroupEntity> classGroupEntityList = classGroupService.selectByMap(paramsMap);
        if(classGroupEntityList.size() > 0){
            for (ClassGroupEntity classGroupEntity : classGroupEntityList){
                if(!classGroupEntity.getId().equals(classGroup.getId())){
                    return R.error(1,"同一线路中班组名称不能重复");
                }
            }
        }

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
        // 班组是否 被轮次绑定
        for (Long classGroupId:ids){
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("class_group_id", classGroupId);
            List<TurnClassGroupEntity> turnClassGroupEntityList = turnClassGroupService.selectByMap(params);
            if(turnClassGroupEntityList.size() > 0){
                for(TurnClassGroupEntity turnClassGroupEntity :turnClassGroupEntityList){
                    TurnEntity turnEntity = turnService.selectById(turnClassGroupEntity.getTurnId());
                    if(turnEntity != null){
                        return R.error(400,"该班组已被轮次[" + turnEntity.getName() + "]绑定使用，请先在该轮次删除.");
                    }
                }
            }
        }
		classGroupService.deleteBatch(ids);

        return R.ok();
    }

}
