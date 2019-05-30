package io.renren.modules.inspection.controller;

import java.util.*;

import io.renren.common.annotation.SysLog;
import io.renren.modules.inspection.entity.InspectionLineEntity;
import io.renren.modules.inspection.entity.LineZoneEntity;
import io.renren.modules.inspection.service.InspectionLineService;
import io.renren.modules.inspection.service.LineZoneService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.inspection.entity.ZoneDeviceEntity;
import io.renren.modules.inspection.service.ZoneDeviceService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-01-21 03:10:48
 */
@RestController
@RequestMapping("inspection/zonedevice")
public class ZoneDeviceController {
    @Autowired
    private InspectionLineService lineService;
    @Autowired
    private LineZoneService lineZoneService;
    @Autowired
    private ZoneDeviceService zoneDeviceService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("inspection:zonedevice:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = zoneDeviceService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("inspection:zonedevice:info")
    public R info(@PathVariable("id") Integer id){
			ZoneDeviceEntity zoneDevice = zoneDeviceService.selectById(id);

        return R.ok().put("zoneDevice", zoneDevice);
    }

    /**
     * 绑定设备
     */
    @SysLog("保存绑定设备")
    @RequestMapping("/bind")
    @RequiresPermissions("inspection:zonedevice:save")
    public R bind(@RequestParam Map<String, Object> params, @RequestBody Integer[] deviceIds){
        String zoneId = (String)params.get("zoneId");
        HashMap<String, Object> lineZoneParams = new HashMap<String, Object>();
        lineZoneParams.put("zone_id", zoneId);
        List<LineZoneEntity> lineZoneEntities = lineZoneService.selectByMap(lineZoneParams);
        for(LineZoneEntity lineZoneEntity:lineZoneEntities){
            InspectionLineEntity line = lineService.selectById(lineZoneEntity.getLineId());
            if(line.getIsPublish().equals(1)){
                return R.error(400,"巡区已绑定的线路\"" + line.getName() + "\"已发布，无法进行绑定设备，请先修改线路发布状态。");
            }
        }

        List<ZoneDeviceEntity> zoneDeviceEntityList = new ArrayList<>(deviceIds.length);

        for(Integer i = 0; i < deviceIds.length;i++){
            ZoneDeviceEntity zoneDeviceEntity = new ZoneDeviceEntity();
            zoneDeviceEntity.setZoneId(Integer.valueOf(zoneId));
            zoneDeviceEntity.setDeviceId(deviceIds[i]);
            zoneDeviceEntity.setCreateTime(new Date());
            zoneDeviceEntity.setOrderNum(i);

            zoneDeviceEntityList.add(zoneDeviceEntity);
        }

        Integer result = zoneDeviceService.insertDeviceBatch(zoneDeviceEntityList);

        return R.ok().put("bind", result);
    }

    /**
     * 删除
     */
    @SysLog("删除绑定设备")
    @RequestMapping("/delete")
    @RequiresPermissions("inspection:zonedevice:delete")
    public R delete(@RequestParam Map<String, Object> params, @RequestBody Integer[] ids){
        String zoneId = (String)params.get("zoneId");
        HashMap<String, Object> lineZoneParams = new HashMap<String, Object>();
        lineZoneParams.put("zone_id", zoneId);
        List<LineZoneEntity> lineZoneEntities = lineZoneService.selectByMap(lineZoneParams);
        for(LineZoneEntity lineZoneEntity:lineZoneEntities){
            InspectionLineEntity line = lineService.selectById(lineZoneEntity.getLineId());
            if(line.getIsPublish().equals(1)){
                return R.error(400,"巡区已绑定的线路\"" + line.getName() + "\"已发布，无法删除已绑定设备，请先修改线路发布状态。");
            }
        }

        zoneDeviceService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
