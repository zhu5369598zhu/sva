package io.renren.modules.inspection.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.annotation.SysLog;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.MapUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.inspection.entity.InspectionPeriodEntity;
import io.renren.modules.inspection.entity.PeriodTurnEntity;
import io.renren.modules.inspection.entity.TurnClassGroupEntity;
import io.renren.modules.inspection.entity.TurnEntity;
import io.renren.modules.inspection.service.InspectionPeriodService;
import io.renren.modules.inspection.service.PeriodTurnService;
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
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("name", turn.getName());
        paramsMap.put("inspection_line_id",turn.getInspectionLineId());
        List<TurnEntity> turnEntityList = turnService.selectByMap(paramsMap);
        if(turnEntityList.size() > 0){
            return R.error(1,"同一线路，轮次名称不能重复");
        }
        HashMap<String, Object> turnMap = new HashMap<>();
        turnMap.put("inspection_line_id",turn.getInspectionLineId());
        List<TurnEntity> entityList = turnService.selectByMap(turnMap);
        if(entityList.size() > 0){
            for(TurnEntity entity: entityList){
                Date date = new Date();
                String format = DateUtils.format(date);
                String startTime = format + " " +entity.getStartTime() + ":00";
                if(startTime.equals(format + " 24:00:00")){
                    startTime =format + " 23:59:59";
                }
                String endTime = format + " " + entity.getEndTime() + ":00";
                if(endTime.equals(format + " 24:00:00")){
                    endTime =format + " 23:59:59";
                }
                String newStartTime = format + " " + turn.getStartTime() + ":00";
                if(newStartTime.equals(format + " 24:00:00")){
                    newStartTime =format + " 23:59:59";
                }
                String newEndTime = format + " " + turn.getEndTime() + ":00";
                if(newEndTime.equals(format + " 24:00:00")){
                    newEndTime =format + " 23:59:59";
                }
                Date startDate = DateUtils.stringToDate(startTime, DateUtils.DATE_TIME_PATTERN);
                Date endDate = DateUtils.stringToDate(endTime, DateUtils.DATE_TIME_PATTERN);
                Date newStartDate = DateUtils.stringToDate(newStartTime, DateUtils.DATE_TIME_PATTERN);
                Date newEedDate = DateUtils.stringToDate(newEndTime, DateUtils.DATE_TIME_PATTERN);
                boolean effectiveDate = DateUtils.isEffectiveDate(newStartDate, startDate, endDate);
                if (effectiveDate) {
                    return R.error(1,"轮次时间不能重复");
                }
                boolean iseffectiveDate = DateUtils.isEffectiveDate(newEedDate, startDate, endDate);
                if (iseffectiveDate) {
                    return R.error(1,"轮次时间不能重复");
                }
            }
        }

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
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("name", turn.getName());
        paramsMap.put("inspection_line_id",turn.getInspectionLineId());
        List<TurnEntity> turnEntityList = turnService.selectByMap(paramsMap);
        if(turnEntityList.size() > 0){
            for(TurnEntity turnEntity: turnEntityList){
                if(!turnEntity.getId().equals(turn.getId())){
                    return R.error(1,"同一线路，轮次名称不能重复");
                }
            }
        }
        HashMap<String, Object> turnMap = new HashMap<>();
        turnMap.put("inspection_line_id",turn.getInspectionLineId());
        List<TurnEntity> entityList = turnService.selectByMap(turnMap);
        if(entityList.size() > 0){
            for(TurnEntity entity: entityList){
                if(entity.getId() == turn.getId()){
                    continue;
                }
                Date date = new Date();
                String format = DateUtils.format(date);
                String startTime = format + " " +entity.getStartTime() + ":00";
                if(startTime.equals(format + " 24:00:00")){
                    startTime =format + " 23:59:59";
                }
                String endTime = format + " " + entity.getEndTime() + ":00";
                if(endTime.equals(format + " 24:00:00")){
                    endTime =format + " 23:59:59";
                }
                String newStartTime = format + " " + turn.getStartTime() + ":00";
                if(newStartTime.equals(format + " 24:00:00")){
                    newStartTime =format + " 23:59:59";
                }
                String newEndTime = format + " " + turn.getEndTime() + ":00";
                if(newEndTime.equals(format + " 24:00:00")){
                    newEndTime =format + " 23:59:59";
                }
                Date startDate = DateUtils.stringToDate(startTime, DateUtils.DATE_TIME_PATTERN);
                Date endDate = DateUtils.stringToDate(endTime, DateUtils.DATE_TIME_PATTERN);
                Date newStartDate = DateUtils.stringToDate(newStartTime, DateUtils.DATE_TIME_PATTERN);
                Date newEedDate = DateUtils.stringToDate(newEndTime, DateUtils.DATE_TIME_PATTERN);
                boolean effectiveDate = DateUtils.isEffectiveDate(newStartDate, startDate, endDate);
                if (effectiveDate) {
                    return R.error(1,"轮次时间不能重复");
                }
                boolean iseffectiveDate = DateUtils.isEffectiveDate(newEedDate, startDate, endDate);
                if (iseffectiveDate) {
                    return R.error(1,"轮次时间不能重复");
                }
            }
        }

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
            // 解绑 和班组的绑定
            List<TurnClassGroupEntity> turnClassGroupEntityList = turnClassGroupService.selectByMap(params);
            if(turnClassGroupEntityList.size() > 0){
                turnClassGroupService.deleteByMap(new MapUtils().put("turn_id", turnId));
            }
        }

        turnService.deleteBatch(ids);

        return R.ok();
    }

}
