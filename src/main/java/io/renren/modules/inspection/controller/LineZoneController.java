package io.renren.modules.inspection.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.renren.common.annotation.SysLog;
import io.renren.modules.inspection.entity.ZoneEntity;
import io.renren.modules.inspection.service.ZoneService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.inspection.entity.LineZoneEntity;
import io.renren.modules.inspection.service.LineZoneService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-02-01 17:02:21
 */
@RestController
@RequestMapping("inspection/linezone")
public class LineZoneController {
    @Autowired
    private LineZoneService lineZoneService;
    @Autowired
    private ZoneService zoneService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("inspection:linezone:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = lineZoneService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("inspection:linezone:info")
    public R info(@PathVariable("id") Integer id){
			LineZoneEntity lineZone = lineZoneService.selectById(id);

        return R.ok().put("lineZone", lineZone);
    }

    /**
     * 绑定设备
     */
    @SysLog("绑定巡区")
    @RequestMapping("/bind")
    @RequiresPermissions("inspection:linezone:bind")
    public R bind(@RequestParam Map<String, Object> params, @RequestBody Integer[] zoneIds){
        String lineId = (String)params.get("lineId");
        List<LineZoneEntity> lineZoneEntityList = new ArrayList<>(zoneIds.length);
        for(Integer i = 0; i < zoneIds.length;i++){
            LineZoneEntity lineZoneEntity = new LineZoneEntity();
            lineZoneEntity.setLineId(Integer.valueOf(lineId));
            lineZoneEntity.setZoneId(zoneIds[i]);
            lineZoneEntity.setOrderNum(i);

            lineZoneEntityList.add(lineZoneEntity);
        }

        Integer result = lineZoneService.insertZoneBatch(lineZoneEntityList);

        return R.ok().put("bind", result);
    }

    /**
     * 选择未绑定到巡区的所有设备
     */
    @RequestMapping("/selectunbind")
    @RequiresPermissions("inspection:linezone:list")
    public R selectUnbind(@RequestParam Map<String, Object> params){
        String filterField = "";
        String filter = (String)params.get("filter");
        String key = (String)params.get("key");
        if (filter.equals("zoneName")) {
            filterField = "zone_name";
        }
        if (filter.equals("zoneCode")) {
            filterField = "zone_code";
        }

        Long deptId = Long.valueOf((String)params.get("deptId"));
        Long lineId = Long.valueOf((String)params.get("lineId"));
        List<ZoneEntity> zoneList = lineZoneService.findZoneUnBind(filterField, key, deptId, lineId);

        return R.ok().put("zoneList", zoneList);
    }

    /**
     * 保存
     */
    @SysLog("保存绑定巡区")
    @RequestMapping("/save")
    @RequiresPermissions("inspection:linezone:save")
    public R save(@RequestBody LineZoneEntity lineZone){
			lineZoneService.insert(lineZone);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改绑定巡区")
    @RequestMapping("/update")
    @RequiresPermissions("inspection:linezone:update")
    public R update(@RequestBody LineZoneEntity lineZone){
			lineZoneService.updateById(lineZone);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除绑定巡区")
    @RequestMapping("/delete")
    @RequiresPermissions("inspection:linezone:delete")
    public R delete(@RequestBody Integer[] ids){
			lineZoneService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
