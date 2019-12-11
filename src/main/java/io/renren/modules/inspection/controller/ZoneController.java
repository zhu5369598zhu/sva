package io.renren.modules.inspection.controller;

import io.renren.common.annotation.SysLog;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.PoiUtils;
import io.renren.common.utils.R;
import io.renren.modules.inspection.entity.InspectionLineEntity;
import io.renren.modules.inspection.entity.LineZoneEntity;
import io.renren.modules.inspection.entity.ZoneDeviceEntity;
import io.renren.modules.inspection.entity.ZoneEntity;
import io.renren.modules.inspection.service.InspectionLineService;
import io.renren.modules.inspection.service.LineZoneService;
import io.renren.modules.inspection.service.ZoneDeviceService;
import io.renren.modules.inspection.service.ZoneService;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.service.SysDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-01-21 03:10:48
 */
@RestController
@RequestMapping("inspection/zone")
public class ZoneController {
    @Autowired
    private ZoneService zoneService;
    @Autowired
    private SysDeptService deptService;
    @Autowired
    private ZoneDeviceService zoneDeviceService;
    @Autowired
    private LineZoneService lineZoneService;
    @Autowired
    private InspectionLineService lineService;


    @PostMapping("/upload")
    @RequiresPermissions("inspection:zone:import")
    public R upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception {
        String[] mustHeader = {"巡区名称<zone_name>", "编码<zone_code>", "所属机构<dept_name>"};
        List<String[]> rows = PoiUtils.readExcel(file);
        if (!Arrays.equals(rows.get(0), mustHeader)) {
            return R.error(400, "导入数据格式错误，导入失败。");
        }
        if(rows.size() < 2){
            return R.error(400, "导入数据格式错误，导入失败。");
        }
        //去掉表头
        rows.remove(0);
        List<ZoneEntity> zones = new ArrayList<>(rows.size());
        for(String[] row: rows){
            ZoneEntity zone = new ZoneEntity();
            zone.setZoneName(row[0]);
            zone.setZoneCode(row[1]);
            zone.setDeptName(row[2]);
            zones.add(zone);
        }
        return R.ok().put("list", zones);
    }

    @RequestMapping("/import")
    @RequiresPermissions("inspection:zone:import")
    @Transactional
    public R importExcel(@RequestBody ZoneEntity[] zones) throws Exception {
        StringBuilder result = new StringBuilder();
        Integer success = 0;
        for (Integer i = 0; i < zones.length; i++) {
            ZoneEntity zone = zones[i];
            result.append("第")
                    .append(i + 1)
                    .append("行[")
                    .append(zone.getZoneName())
                    .append("]");

            if("".equals(zone.getZoneName())){
                result.append("巡区名称未找到\r\n");
                continue;
            }
            if("".equals(zone.getZoneCode())){
                result.append("巡区编码未找到\r\n");
                continue;
            }
            if("".equals(zone.getDeptName())){
                result.append("巡区部门未找到\r\n");
                continue;
            }
            if (zoneService.isExist(zone.getZoneName(), zone.getZoneCode()) > 0) {
                result.append("重复\r\n");
                continue;
            }

            //设置部门
            SysDeptEntity dept = deptService.selectByName(zone.getDeptName());
            if (dept != null){
                zone.setDeptId(dept.getDeptId());
            }else{
                result.append("所属机构未找到\r\n");
                continue;
            }

            zone.setGuid(UUID.randomUUID().toString());
            zone.setCreateTime(new Date());
            zone.setOrderNum(0);
            try{
                zoneService.insert(zone);
            }catch(Exception e) {
                result.append(e.getMessage());
                result.append("\r\n");
            }

            success = success + 1;
            result.append("导入成功\r\n");
        }
        result.append("------------------------------------------------------\r\n\r\n");
        result.append("成功导入");
        result.append(success);
        result.append("条数据\r\n");
        return R.ok().put("result", result);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("inspection:zone:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = zoneService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 列表
     */
    @RequestMapping("/export")
    @RequiresPermissions("inspection:zone:export")
    public R export(@RequestParam Map<String, Object> params){
        List<ZoneEntity> list = zoneService.all(params);

        return R.ok().put("list", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{zoneId}")
    @RequiresPermissions("inspection:zone:info")
    public R info(@PathVariable("zoneId") Integer zoneId){
			ZoneEntity zone = zoneService.selectById(zoneId);

        return R.ok().put("zone", zone);
    }

    /**
     * 保存
     */
    @SysLog("保存巡区")
    @RequestMapping("/save")
    @RequiresPermissions("inspection:zone:save")
    public R save(@RequestBody ZoneEntity zone){
            zone.setCreateTime(new Date());
            zone.setGuid(UUID.randomUUID().toString());
            ZoneEntity tmp = zoneService.selectByZoneCode(zone.getZoneCode());
            if(tmp == null){
                zoneService.insert(zone);
            }else{
        return R.error(400, "编码已绑定过其它巡区，不能再次绑定。");
    }

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改巡区")
    @RequestMapping("/update")
    @RequiresPermissions("inspection:zone:update")
    public R update(@RequestBody ZoneEntity zone){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("zone_id", zone.getZoneId());
        List<LineZoneEntity> lineZoneEntities = lineZoneService.selectByMap(params);
        if(lineZoneEntities.size() > 0){
            for(LineZoneEntity lineZoneEntity:lineZoneEntities){
                InspectionLineEntity lineEntity = lineService.selectById(lineZoneEntity.getLineId());
                if(lineEntity != null && lineEntity.getIsDelete() == 0 && lineEntity.getIsPublish() == 1){
                    return R.error(400,"该巡区已绑定到巡检线路[" + lineEntity.getName() + "]，请选解除绑定再修改。");
                }
            }
        }
        List<ZoneEntity> tmp = zoneService.selectByZoneCodeList(zone.getZoneCode());
        if(tmp != null){
            if(tmp.size() == 1){
                if(tmp.get(0).getZoneId() != zone.getZoneId()){
                    return R.error(400, "编码已绑定过其它巡区，不能再次绑定。");
                }else {
                    zoneService.updateById(zone);
                }
            } else if (tmp.size()>1) {
                return R.error(400, "编码已绑定过其它巡区，不能再次绑定。");
            } else {
                zoneService.updateById(zone);
            }
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除巡区")
    @RequestMapping("/delete")
    @RequiresPermissions("inspection:zone:delete")
    public R delete(@RequestBody Integer[] zoneIds){
        boolean isOk = false;
        for(Integer zoneId:zoneIds){
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("zone_id", zoneId);
            List<ZoneDeviceEntity>  zoneDevices = zoneDeviceService.selectByMap(params);
            if(zoneDevices.size() > 0){
                return R.error(400,"该巡区下已绑定设备，无法直接删除，请先解除设备绑定再删除。");
            }

            List<LineZoneEntity> lineZoneEntities = lineZoneService.selectByMap(params);
            if(lineZoneEntities.size() > 0){
                for(LineZoneEntity lineZoneEntity:lineZoneEntities){
                    InspectionLineEntity lineEntity = lineService.selectById(lineZoneEntity.getLineId());
                    if(lineEntity != null && lineEntity.getIsDelete() == 0){
                        return R.error(400,"该巡区已绑定到巡检线路[" + lineEntity.getName() + "]，请选解除绑定再删除。");
                    }
                }
            }

            ZoneEntity zone = zoneService.selectById(zoneId);
            if(zone != null) {
                zone.setIsDelete(1);
                isOk = zoneService.updateById(zone);
                if (!isOk) {
                    break;
                }
            }
        }
        if(isOk){
            return R.ok();
        }else{
            return R.error(500,"服务器错误");
        }
    }

}
