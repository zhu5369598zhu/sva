package io.renren.modules.inspection.controller;

import java.util.*;

import io.renren.common.annotation.SysLog;
import io.renren.modules.inspection.entity.InspectionLineEntity;
import io.renren.modules.inspection.entity.LineZoneEntity;
import io.renren.modules.inspection.entity.ZoneDeviceEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.inspection.entity.InspectionLinePublishEntity;
import io.renren.modules.inspection.service.InspectionLinePublishService;
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
@RequestMapping("inspection/inspectionlinepublish")
public class InspectionLinePublishController {
    @Autowired
    private InspectionLinePublishService inspectionLinePublishService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("inspection:inspectionlinepublish:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = inspectionLinePublishService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("inspection:inspectionlinepublish:info")
    public R info(@PathVariable("id") Integer id){
			InspectionLinePublishEntity inspectionLinePublish = inspectionLinePublishService.selectById(id);

        return R.ok().put("inspectionLinePublish", inspectionLinePublish);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("inspection:inspectionlinepublish:save")
    public R save(@RequestBody InspectionLinePublishEntity inspectionLinePublish){
			inspectionLinePublishService.insert(inspectionLinePublish);

        return R.ok();
    }

    /**
     * 绑定设备
     */
    @SysLog("保存绑定设备")
    @RequestMapping("/bind")
    @RequiresPermissions("inspection:inspectionlinepublish:bind")
    public R bind(@RequestParam Map<String, Object> params, @RequestBody Integer[] pdaIds){
        Integer lineId = Integer.valueOf((String)params.get("lineId"));
        List<InspectionLinePublishEntity> linePublishEntities = new ArrayList<>(pdaIds.length);

        for(Integer i = 0; i < pdaIds.length;i++){
            InspectionLinePublishEntity linePublish = new InspectionLinePublishEntity();
            linePublish.setLineId(lineId);
            linePublish.setPdaId(pdaIds[i]);
            linePublish.setIsDownload(0);
            linePublish.setCreateTime(new Date());

            linePublishEntities.add(linePublish);
        }

        Integer result = inspectionLinePublishService.insertPublishBatch(linePublishEntities);

        return R.ok().put("bind", result);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("inspection:inspectionlinepublish:update")
    public R update(@RequestBody InspectionLinePublishEntity inspectionLinePublish){
			inspectionLinePublishService.updateById(inspectionLinePublish);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("inspection:inspectionlinepublish:delete")
    public R delete(@RequestBody Integer[] ids){
			inspectionLinePublishService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
