package io.renren.modules.inspection.controller;

import java.io.ByteArrayOutputStream;
import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.annotation.SysLog;
import io.renren.common.utils.*;
import io.renren.modules.inspection.entity.DeviceQrcodeEntity;
import io.renren.modules.inspection.entity.ZoneDeviceEntity;
import io.renren.modules.inspection.entity.ZoneEntity;
import io.renren.modules.inspection.service.DeviceQrcodeService;
import io.renren.modules.inspection.service.ZoneDeviceService;
import io.renren.modules.inspection.service.ZoneService;
import io.renren.modules.setting.entity.DeviceLevelEntity;
import io.renren.modules.setting.service.DeviceLevelService;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.service.SysDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.inspection.entity.DeviceEntity;
import io.renren.modules.inspection.service.DeviceService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-01-21 03:10:48
 */
@RestController
@RequestMapping("inspection/device")
public class DeviceController {

    @Autowired
    private ZoneDeviceService zoneDeviceService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceQrcodeService qrcodeService;
    @Autowired
    private SysDeptService deptService;
    @Autowired
    private ZoneService zoneService;
    @Autowired
    private DeviceLevelService levelService;




    @PostMapping("/upload")
    @RequiresPermissions("inspection:device:import")
    public R upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception{
        String[] mustHeader = {"设备名称<device_name>", "设备等级<device_level_name>", "所属机构<dept_name>","生产日期<manufacture_date>",
                "设备编号<device_num>", "设备编码<device_code>", "设备规格<device_unit>", "生产厂家<device_manufacturer>", "供货商<device_supplier>",
                "设备模型<device_model>", "责任人<device_master>", "巡区<zone_name>", "是否巡检<is_inspect>", "看板显示<is_show_dashboard>"};
        List<String[]> rows = PoiUtils.readExcel(file);
        if (!Arrays.equals(rows.get(0), mustHeader)) {
            return R.error(400, "导入数据格式错误，导入失败。");
        }
        if(rows.size() < 2){
            return R.error(400, "导入数据格式错误，导入失败。");
        }
        //去掉表头
        rows.remove(0);
        List<DeviceEntity> devices = new ArrayList<>(rows.size());
        for(String[] row: rows){
            DeviceEntity device = new DeviceEntity();
            device.setDeviceName(row[0]);
            device.setDeviceLevelName(row[1]);
            device.setDeptName(row[2]);
            device.setManufactureDate(DateUtils.stringToDate(row[3], "yyyy-MM-dd HH:mm:ss"));
            device.setDeviceNum(row[4]);
            device.setDeviceCode(row[5]);
            device.setDeviceUnit(row[6]);
            device.setDeviceManufacturer(row[7]);
            device.setDeviceSupplier(row[8]);
            device.setDeviceModel(row[9]);
            device.setDeviceMaster(row[10]);
            device.setZones(row[11]);
            device.setIsInspectName(row[12]);
            device.setIsShowDashboardName(row[13]);
            devices.add(device);
        }
        return R.ok().put("list", devices);
    }

    @RequestMapping("/import")
    @RequiresPermissions("inspection:device:import")
    @Transactional
    public R importExcel(@RequestBody DeviceEntity[] devices) throws Exception{
        StringBuilder result = new StringBuilder();
        Integer success = 0;
        for(Integer i = 0; i < devices.length; i++){
            DeviceEntity device = devices[i];
            result.append("第")
                    .append(i+1)
                    .append("行[")
                    .append(device.getDeviceName())
                    .append("]");
            Integer count = deviceService.isExist(device.getDeviceName(), device.getDeviceCode());
            if ( count > 0) {
                result.append("重复\r\n");
                continue;
            }
            //设置部门
            SysDeptEntity dept = deptService.selectByName(device.getDeptName());
            if (dept != null){
                device.setDeviceDept(dept.getDeptId());
            }else{
                result.append("所属机构未找到\r\n");
                continue;
            }
            //设置级别
            DeviceLevelEntity level = levelService.selectByName(device.getDeviceLevelName());
            if (level != null){
                device.setDeviceLevel(level.getId());
            }else{
                result.append("设备等级未找到\r\n");
                continue;
            }
            if(device.getIsInspectName().equals("是")){
                device.setIsInspect(1);
            }else if(device.getIsInspectName().equals("否")){
                device.setIsInspect(0);
            }else{
                result.append("是否巡检数据错误\r\n");
                continue;
            }
            if(device.getIsShowDashboardName().equals("是")){
                device.setIsShowDashboard(1);
            }else if(device.getIsShowDashboardName().equals("否")){
                device.setIsShowDashboard(0);
            }else{
                result.append("是否看板显示数据错误\r\n");
                continue;
            }

            device.setGuid(UUID.randomUUID().toString());
            device.setCreateTime(new Date());
            try{
                deviceService.insert(device);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                QrCodeUtils.encode(device.getGuid(), baos);
                DeviceQrcodeEntity qrcode = new DeviceQrcodeEntity();
                qrcode.setGuid(UUID.randomUUID().toString());
                qrcode.setCreateTime(new Date());
                qrcode.setData(baos.toByteArray());
                qrcode.setDeviceId(device.getDeviceId());
                HashMap<String, Object> qrcodeParams = new HashMap<>();
                qrcodeParams.put("device_id", device.getDeviceId());
                qrcodeService.deleteByMap(qrcodeParams);
                qrcodeService.insert(qrcode);
                baos.close();
            }catch(Exception e) {
                result.append(e.getMessage());
                result.append("\r\n");
            }
            if(device.getDeviceId() > 0 && device.getZones().length() > 0){
                String[] zoneArray = device.getZones().split("/");
                for(String zoneName: zoneArray){
                    ZoneEntity zone = zoneService.selectByName(zoneName);
                    if(zone != null){
                        HashMap<String, Object> params = new HashMap<>();
                        params.put("zone_id", zone.getZoneId());
                        params.put("device_id",device.getDeviceId());
                        List<ZoneDeviceEntity> zoneDeviceEntities = zoneDeviceService.selectByMap(params);
                        if(zoneDeviceEntities.size() >0){
                            result.append("已加入该巡区，不能重复，");
                        }else{
                            ZoneDeviceEntity zoneDevice= new ZoneDeviceEntity();
                            zoneDevice.setDeviceId(device.getDeviceId());
                            zoneDevice.setZoneId(zone.getZoneId());
                            zoneDevice.setOrderNum(0);
                            zoneDevice.setCreateTime(new Date());
                            zoneDeviceService.insert(zoneDevice);
                        }
                    } else {
                        result.append("设备信息导入成功，但巡区未找到\r\n");
                    }
                }


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
    @RequiresPermissions("inspection:device:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = deviceService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/export")
    @RequiresPermissions("inspection:device:export")
    public R export(@RequestParam Map<String, Object> params){
        List<DeviceEntity> all = deviceService.all(params);

        return R.ok().put("list", all);
    }

    /**
     * 获取部门树及下属设备
     */
    @RequestMapping("/tree")
    @RequiresPermissions("inspection:device:tree")
    public R tree(@RequestParam Map<String, Object> params){
        String deviceName = (String)params.get("deviceName");
        List<Map<String,Object>> deviceTreeList = deviceService.findDeviceTree(deviceName);

        return R.ok().put("deviceTree", deviceTreeList);
    }


    /**
     * 获取部门树及下属设备
     */
    @RequestMapping("/linetree")
    @RequiresPermissions("inspection:device:tree")
    public R linetree(@RequestParam Map<String, Object> params){
        List<Map<String,Object>> lineTreeList = deviceService.findLineTree(params);

        return R.ok().put("lineTree", lineTreeList);
    }

    /**
     * 获取设备异常状态
     */
    @RequestMapping("/getstatus")
    public R getstatus(@RequestParam Map<String, Object> params){
        List<Map<String,Object>> statusList = deviceService.getDeviceStatus(params);

        return R.ok().put("status", statusList);
    }

    /**
     * 获取设备异常状态
     */
    @RequestMapping("/getdevicetoinspection")
    public R getDeviceToInspection(){
        List<DeviceEntity> deviceList = deviceService.selectList(
                new EntityWrapper<DeviceEntity>()
                .eq("is_delete",0)
                .eq("is_show_dashboard",1)
        );

        return R.ok().put("deviceList", deviceList);
    }

    /**
     * 获取设备异常排名
     */
    @RequestMapping("/getexceptiontop")
    public R getDeviceExceptionTop(@RequestBody Map<String, Object> params){
        ArrayList<Integer> levelIds = (ArrayList<Integer>)params.get("levleIds");
        String startTime = (String)params.get("startTime");
        String endTime = (String)params.get("endTime");
        List<Map<String,Object>> topList = deviceService.getDeviceExceptionTop(levelIds,startTime,endTime);

        return R.ok().put("topList", topList);
    }

    /**
     * 选择未绑定到巡区的所有设备
     */
    @RequestMapping("/selectunbind")
    @RequiresPermissions("inspection:device:list")
    public R selectUnbind(@RequestParam Map<String, Object> params){
        String filterField = "";
        String filter = (String)params.get("filter");
        String key = (String)params.get("key");
        if (filter.equals("deviceName")) {
            filterField = "device_name";
        }
        if (filter.equals("deviceCode")) {
            filterField = "device_code";
        }
        Long zoneId = Long.valueOf((String)params.get("zoneId"));
        List<DeviceEntity> deviceList = deviceService.findDeviceUnBind(filterField, key, zoneId);

        return R.ok().put("deviceList", deviceList);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{deviceId}")
    @RequiresPermissions("inspection:device:info")
    public R info(@PathVariable("deviceId") Integer deviceId){
			DeviceEntity device = deviceService.selectById(deviceId);

        return R.ok().put("device", device);
    }

    /**
     * 创建二维码
     */
    @SysLog("创建二维码")
    @RequestMapping("/createqrcode")
    @RequiresPermissions("inspection:device:save")
    public R save(@RequestParam Map<String, Object> params) throws Exception{
        String deviceId = (String)params.get("deviceId");
        DeviceEntity device = deviceService.selectById(deviceId);
        if(device != null){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            QrCodeUtils.encode(device.getGuid(), baos);
            DeviceQrcodeEntity qrcode = new DeviceQrcodeEntity();
            qrcode.setGuid(UUID.randomUUID().toString());
            qrcode.setCreateTime(new Date());
            qrcode.setData(baos.toByteArray());
            qrcode.setDeviceId(device.getDeviceId());
            HashMap<String, Object> qrcodeParams = new HashMap<>();
            qrcodeParams.put("device_id", device.getDeviceId());
            qrcodeService.deleteByMap(qrcodeParams);
            qrcodeService.insert(qrcode);
            baos.close();
            return R.ok();
        }else{
            R.error(400,"未找到对应的设备。");
        }
        return R.error(500,"数据库错误。");
    }

    /**
     * 保存
     */
    @SysLog("保存设备")
    @RequestMapping("/save")
    @RequiresPermissions("inspection:device:save")
    public R save(@RequestBody DeviceEntity device) throws Exception{
        if(!device.getDeviceCode().equals("")){
            DeviceEntity tmp = deviceService.selectByCode(device.getDeviceId(), device.getDeviceCode());
            if (tmp != null){
                return R.error(400,"设备编码已存在，参数错误。");
            }
        }
        Date cur = new Date();
        device.setCreateTime(cur);
        device.setGuid(UUID.randomUUID().toString());
        boolean isOk = deviceService.insert(device);
        if (isOk) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            QrCodeUtils.encode(device.getGuid(), baos);
            DeviceQrcodeEntity qrcode = new DeviceQrcodeEntity();
            qrcode.setGuid(UUID.randomUUID().toString());
            qrcode.setCreateTime(cur);
            qrcode.setData(baos.toByteArray());
            qrcode.setDeviceId(device.getDeviceId());
            HashMap<String, Object> qrcodeParams = new HashMap<>();
            qrcodeParams.put("device_id", device.getDeviceId());
            qrcodeService.deleteByMap(qrcodeParams);
            qrcodeService.insert(qrcode);
            baos.close();
            return R.ok();
        }
        return R.error(500,"数据库错误。");
    }

    /**
     * 修改
     */
    @SysLog("修改设备")
    @RequestMapping("/update")
    @RequiresPermissions("inspection:device:update")
    public R update(@RequestBody DeviceEntity device){
        if(!device.getDeviceCode().equals("")){
            DeviceEntity tmp = deviceService.selectByCode(device.getDeviceId(), device.getDeviceCode());
            if (tmp != null){
                return R.error(400,"设备编码已存在，参数错误。");
            }
        }


        deviceService.updateById(device);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除设备")
    @RequestMapping("/delete")
    @RequiresPermissions("inspection:device:delete")
    public R delete(@RequestBody Integer[] deviceIds){
        boolean isOK = false;
        for(Integer deviceId:deviceIds){
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("device_id", deviceId);
            List<ZoneDeviceEntity>  zoneDevices = zoneDeviceService.selectByMap(params);
            if(zoneDevices.size() > 0){
                return R.error(400,"设备已绑定到巡区，无法删除，请先取消绑定。");
            }

            DeviceEntity deviceEntity = deviceService.selectById(deviceId);
            if (deviceEntity != null){
                deviceEntity.setIsDelete(1);
                isOK = deviceService.updateById(deviceEntity);
                if(!isOK){
                    break;
                }
            }
        }

        if(isOK){
            return R.ok();
        }else{
            return R.error(500,"服务器错误");
        }
    }

}
