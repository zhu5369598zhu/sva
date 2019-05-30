package io.renren.modules.inspection.controller;

import java.util.*;

import io.renren.common.annotation.SysLog;
import io.renren.modules.inspection.entity.InspectionLineEntity;
import io.renren.modules.inspection.service.InspectionLineService;
import io.renren.modules.inspection.service.PeriodTurnService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.inspection.entity.InspectionPeriodEntity;
import io.renren.modules.inspection.service.InspectionPeriodService;
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
@RequestMapping("inspection/inspectionperiod")
public class InspectionPeriodController {
    @Autowired
    private PeriodTurnService periodTurnService;
    @Autowired
    private InspectionPeriodService inspectionPeriodService;
    @Autowired
    private InspectionLineService lineService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("inspection:inspectionperiod:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = inspectionPeriodService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("inspection:inspectionperiod:info")
    public R info(@PathVariable("id") Integer id){
			InspectionPeriodEntity inspectionPeriod = inspectionPeriodService.selectById(id);

        List<Long> turnIdList = periodTurnService.queryTurnIdList(inspectionPeriod.getId());
        if (turnIdList != null){
            inspectionPeriod.setTurnList(turnIdList);
        }
        return R.ok().put("inspectionPeriod", inspectionPeriod);
    }

    /**
     * 保存
     */
    @SysLog("保存周期")
    @RequestMapping("/save")
    @RequiresPermissions("inspection:inspectionperiod:save")
    public R save(@RequestBody InspectionPeriodEntity inspectionPeriod){
        InspectionLineEntity line = lineService.selectById(inspectionPeriod.getLineId());
        Integer day = 0;
        if (line != null){
            HashMap<String, Object> periodParams = new HashMap<String, Object>();
            periodParams.put("line_id", inspectionPeriod.getLineId());
            List<InspectionPeriodEntity> inspectionPeriodEntities = inspectionPeriodService.selectByMap(periodParams);
            HashMap<String,Integer> spans = new HashMap<String,Integer>();
            if(line.getPeriodType() == 1) {
                day = 1;
            }
            if(line.getPeriodType() == 2) {
                day = 7;
            }
            if(line.getPeriodType() == 3) {
                day = 31;
            }
            for(Integer i = 1; i < day + 1 ; i++){
                spans.put(i.toString(), i);
            }

            for (InspectionPeriodEntity periodEntity:inspectionPeriodEntities){
                for(Integer j = 1; j < periodEntity.getSpan() + 1; j++){
                    Integer p = (periodEntity.getStartPoint() + periodEntity.getSpan() - 1) % day;
                    spans.remove(p.toString());
                }
            }
            for(Integer m = inspectionPeriod.getStartPoint(); m < inspectionPeriod.getStartPoint() + inspectionPeriod.getSpan(); m++ ){
                Integer today = m;
                if (today > day){
                    today = m%day;
                }
                Integer period = spans.get(today.toString());
                if (period == null){
                    return R.error(400,"添加周期失败，请检查添加周期是否重叠。");
                }
            }

            inspectionPeriod.setCreateTime(new Date());
            inspectionPeriod.setGuid(UUID.randomUUID().toString());
            inspectionPeriodService.save(inspectionPeriod);
        }


        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改周期")
    @RequestMapping("/update")
    @RequiresPermissions("inspection:inspectionperiod:update")
    public R update(@RequestBody InspectionPeriodEntity inspectionPeriod){
			inspectionPeriodService.update(inspectionPeriod);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除周期")
    @RequestMapping("/delete")
    @RequiresPermissions("inspection:inspectionperiod:delete")
    public R delete(@RequestBody Long[] ids){
			inspectionPeriodService.deleteBatch(ids);

        return R.ok();
    }

}
