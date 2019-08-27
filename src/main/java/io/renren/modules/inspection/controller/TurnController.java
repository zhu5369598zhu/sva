package io.renren.modules.inspection.controller;

import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.annotation.SysLog;
import io.renren.modules.inspection.entity.InspectionPeriodEntity;
import io.renren.modules.inspection.entity.PeriodTurnEntity;
import io.renren.modules.inspection.service.InspectionPeriodService;
import io.renren.modules.inspection.service.PeriodTurnService;
import io.renren.modules.inspection.service.TurnClassGroupService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.inspection.entity.TurnEntity;
import io.renren.modules.inspection.service.TurnService;
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
@RequestMapping("inspection/turn")
public class TurnController {
    @Autowired
    private TurnClassGroupService turnClassGroupService;
    @Autowired
    private TurnService turnService;
    @Autowired
    private PeriodTurnService periodTurnService;
    @Autowired
    private InspectionPeriodService periodService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("inspection:turn:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = turnService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/findlist")
    @RequiresPermissions("inspection:classgroup:list")
    public R findlist(@RequestParam Map<String, Object> params){
        String inspectionLineId = (String)params.get("inspectionLineId");
        List<TurnEntity> turnEntityList = turnService.selectList(
                new EntityWrapper<TurnEntity>()
                        .eq(inspectionLineId !=null, "inspection_line_id", inspectionLineId)

        );

        return R.ok().put("turnlist", turnEntityList);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("inspection:turn:info")
    public R info(@PathVariable("id") Integer id){
			TurnEntity turn = turnService.selectById(id);

        List<Long> classGroupIdList = turnClassGroupService.queryClassGroupIdList(turn.getId());
        if (classGroupIdList != null){
            turn.setClassGroupList(classGroupIdList);
        }
        return R.ok().put("turn", turn);
    }

    /**
     * 保存
     */
    @SysLog("保存轮次")
    @RequestMapping("/save")
    @RequiresPermissions("inspection:turn:save")
    public R save(@RequestBody TurnEntity turn){
            turn.setGuid(UUID.randomUUID().toString());
            turn.setCreateTime(new Date());
			turnService.save(turn);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改轮次")
    @RequestMapping("/update")
    @RequiresPermissions("inspection:turn:update")
    public R update(@RequestBody TurnEntity turn){
			turnService.update(turn);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除轮次")
    @RequestMapping("/delete")
    @RequiresPermissions("inspection:turn:delete")
    public R delete(@RequestBody Long[] ids){
        for(Long turnId:ids){
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("turn_id",turnId);
            List<PeriodTurnEntity> periodTurnList = periodTurnService.selectByMap(params);
            if(periodTurnList.size() > 0) {
                for(PeriodTurnEntity periodTurn:periodTurnList){
                    InspectionPeriodEntity period = periodService.selectById(periodTurn.getPeriodId());
                    if(period != null){
                        return R.error(400,"该轮次已被周期[" + period.getName() + "]绑定使用，请先在该周期将期删除.");
                    }
                }
            }
        }

        turnService.deleteBatch(ids);

        return R.ok();
    }

}
