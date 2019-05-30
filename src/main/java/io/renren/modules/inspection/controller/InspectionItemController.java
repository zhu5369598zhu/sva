package io.renren.modules.inspection.controller;

import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.qiniu.common.Zone;
import io.renren.common.annotation.SysLog;
import io.renren.common.utils.PoiUtils;
import io.renren.modules.inspection.entity.*;
import io.renren.modules.inspection.service.*;
import io.renren.modules.setting.entity.*;
import io.renren.modules.setting.service.*;
import io.renren.modules.sys.entity.SysUserEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.validator.internal.util.IdentitySet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-01-22 17:33:37
 */
@RestController
@RequestMapping("inspection/inspectionitem")
public class InspectionItemController {
    @Autowired
    private InspectionItemService inspectionItemService;
    @Autowired
    private InspectionItemExtraService extraService;
    @Autowired
    private ExceptionService exceptionService;
    @Autowired
    private InspectionItemPresuppositionService presuppositionService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private InspectionTypeService inspectionTypeService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private InspectionStatusService statusService;
    @Autowired
    private SamplingFrequencyService frequencyService;
    @Autowired
    private SamplingPrecisionService precisionService;
    @Autowired
    private ZoneDeviceService zoneDeviceService;
    @Autowired
    private LineZoneService lineZoneService;
    @Autowired
    private InspectionLineService lineService;
    @Autowired
    private InspectionResultService resultService;

    @PostMapping("/upload")
    @RequiresPermissions("inspection:inspectionitem:import")
    public R upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception {
        String[] mustHeader = {"所属设备<device_name>", "设备编码<device_code>", "巡检项<name>", "巡检类型名称<inspection_type_name>",
                "单位<unit>", "检时状态标记<inspection_status>", "发射率<emissivity>", "采样频率<frequency_name>", "采样点数<precision_name>",
                "上限<up_limit>", "上上限<upup_limit>", "下限<down_limit>", "下下限<downdown_limit>", "扩展<extras>",
                "扩展异常<exceptions>", "描述<remark>", "默认转速<default_rpm>"};
        List<String[]> rows = PoiUtils.readExcel(file);
        if (!Arrays.equals(rows.get(0), mustHeader)) {
            return R.error(400, "导入数据格式错误，导入失败。");
        }
        if(rows.size() < 2){
            return R.error(400, "导入数据格式错误，导入失败。");
        }
        //去掉表头
        rows.remove(0);
        List<InspectionItemEntity> items = new ArrayList<>(rows.size());
        for(String[] row: rows){
            InspectionItemEntity item = new InspectionItemEntity();
            if(!row[0].isEmpty()) {
                item.setDeviceName(row[0]);
            }
            if(!row[1].isEmpty()) {
                item.setDeviceCode(row[1]);
            }
            if(!row[2].isEmpty()) {
                item.setName(row[2]);
            }
            if(!row[3].isEmpty()) {
                item.setInspectionTypeName(row[3]);
            }
            if(!row[4].isEmpty()) {
                item.setUnit(row[4]);
            }
            if(!row[5].isEmpty()) {
                item.setInspectionStatusName(row[5]);
            }
            if(!row[6].isEmpty()) {
                item.setEmissivity(Double.valueOf(row[6]));
            }
            if(!row[7].isEmpty()) {
                item.setFrequencyName(row[7]);
            }
            if(!row[8].isEmpty()) {
                item.setPrecisionName(row[8]);
            }
            if(!row[9].isEmpty()){
                item.setUpLimit(Double.valueOf(row[9]));
            }
            if(!row[10].isEmpty()) {
                item.setUpupLimit(Double.valueOf(row[10]));
            }
            if(!row[11].isEmpty()) {
                item.setDownLimit(Double.valueOf(row[11]));
            }
            if(!row[12].isEmpty()) {
                item.setDowndownLimit(Double.valueOf(row[12]));
            }
            if(!row[13].isEmpty()) {
                item.setExtras(row[13]);
            }
            if(!row[14].isEmpty()) {
                item.setExtraExceptions(row[14]);
            }
            if(!row[15].isEmpty()) {
                item.setRemark(row[15]);
            }
            if(!row[16].isEmpty()) {
                item.setDefaultRpm(Integer.valueOf(row[16]));
            }

            items.add(item);
        }
        return R.ok().put("list", items);
    }

    @RequestMapping("/import")
    @RequiresPermissions("inspection:inspectionitem:import")
    @Transactional
    public R importExcel(@RequestBody InspectionItemEntity[] items) throws Exception {
        StringBuilder result = new StringBuilder();
        Integer success = 0;
        for (Integer i = 0; i < items.length; i++) {
            InspectionItemEntity item = items[i];
            result.append("第")
                    .append(i + 1)
                    .append("行[")
                    .append(item.getName())
                    .append("]");
            DeviceEntity device = deviceService.selectByDeviceCode(item.getDeviceCode());
            if (device != null){
                item.setDeviceId(device.getDeviceId());

                if (inspectionItemService.isExist(item.getName(), device.getDeviceCode()) > 0) {
                    result.append("重复\r\n");
                    continue;
                }
            }else{
                result.append("所属设备未找到\r\n");
                continue;
            }

            InspectionTypeEntity type = inspectionTypeService.selectByName(item.getInspectionTypeName());
            if (type != null){
                item.setInspectionType(type.getId());
            }else{
                result.append("巡检类型未找到\r\n");
                continue;
            }
            if(item.getInspectionTypeName().equals("抄表")){
                UnitEntity unit = unitService.selectByName(item.getUnit());
                if (unit != null){
                    item.setUnit(unit.getId().toString());
                }else{
                    result.append("单位未找到\r\n");
                    continue;
                }
            }

            InspectionStatusEntity status = statusService.selectByName(item.getInspectionStatusName());
            if (status != null){
                item.setInspectionStatus(status.getId());
            }else{
                result.append("检时状态未找到\r\n");
                continue;
            }

            SamplingFrequencyEntity frequency = frequencyService.selectByName(item.getFrequencyName());
            if (frequency != null){
                item.setFrequency(frequency.getId());
            }else{
                result.append("采样频率未找到\r\n");
                continue;
            }

            SamplingPrecisionEntity precision = precisionService.selectByName(item.getPrecisionName());
            if (frequency != null){
                item.setPrecision(precision.getId());
            }else{
                result.append("采样点数未找到\r\n");
                continue;
            }

            item.setGuid(UUID.randomUUID().toString());
            item.setOrderNum(0);
            item.setCreateTime(new Date());
            try{
                inspectionItemService.insert(item);
            }catch(Exception e) {
                result.append(e.getMessage());
                result.append("\r\n");
            }

            if(item.getInspectionTypeName() !=null && item.getInspectionTypeName().equals("观察")) {
                if (item.getExtraExceptions() != null && item.getExtras() != null) {
                    String[] exceptions = item.getExtraExceptions().split("/");
                    String[] extras = item.getExtras().split("/");
                    if (exceptions.length != extras.length) {
                        result.append("扩展与异常不匹配\r\n");
                        continue;
                    }
                    for (Integer j = 0; j < extras.length; j++) {
                        ExceptionEntity exception = exceptionService.selectByName(exceptions[j]);
                        if (exception == null) {
                            result.append("扩展对应异常未找到\r\n");
                            continue;
                        }
                        InspectionItemExtraEntity extra = new InspectionItemExtraEntity();
                        extra.setItemId(item.getId());
                        extra.setExceptionId(exception.getId());
                        extra.setName(extras[j]);
                        extra.setGuid(UUID.randomUUID().toString());
                        extraService.insert(extra);
                    }
                } else {
                    result.append("扩展数据为空，");
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
    @RequiresPermissions("inspection:inspectionitem:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = inspectionItemService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 列表
     */
    @RequestMapping("/export")
    @RequiresPermissions("inspection:inspectionitem:export")
    public R export(@RequestParam Map<String, Object> params){
        List<InspectionItemEntity> list = inspectionItemService.all(params);

        return R.ok().put("list", list);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("inspection:inspectionitem:info")
    public R info(@PathVariable("id") Integer id){
        InspectionItemEntity inspectionItem = inspectionItemService.selectById(id);
        List<InspectionItemExtraEntity> extras = extraService.selectList(
                new EntityWrapper<InspectionItemExtraEntity>()
                        .eq(id != null, "item_id", id)
        );
        if(extras != null){
            for(InspectionItemExtraEntity extra : extras){
                ExceptionEntity exception = exceptionService.selectById(extra.getExceptionId());
                if(exception != null){
                    extra.setExceptionName(exception.getName());
                }
            }
            inspectionItem.setExtraList(extras);
        }

        List<InspectionItemPresuppositionEntity> presuppositions = presuppositionService.selectList(
                new EntityWrapper<InspectionItemPresuppositionEntity>()
                        .eq(id != null, "item_id", id)
        );
        if(extras != null){
            inspectionItem.setPresuppositionList(presuppositions);
        }

        return R.ok().put("inspectionItem", inspectionItem);
    }

    /**
     * 保存
     */
    @SysLog("保存巡检点")
    @RequestMapping("/save")
    @RequiresPermissions("inspection:inspectionitem:save")
    public R save(@RequestBody InspectionItemEntity inspectionItem){
            inspectionItem.setCreateTime(new Date());
            inspectionItem.setGuid(UUID.randomUUID().toString());
			inspectionItemService.save(inspectionItem);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改巡检点")
    @RequestMapping("/update")
    @RequiresPermissions("inspection:inspectionitem:update")
    public R update(@RequestBody InspectionItemEntity inspectionItem){
			inspectionItemService.update(inspectionItem);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除巡检点")
    @RequestMapping("/delete")
    @RequiresPermissions("inspection:inspectionitem:delete")
    public R delete(@RequestBody Long[] ids){

        for(Long itemId:ids){
            InspectionItemEntity item = inspectionItemService.selectById(itemId);
            if(item != null){
                Integer hasData = resultService.selectCount(
                        new EntityWrapper<InspectionResultEntity>()
                                .eq("item_id",item.getId())
                );
                if(hasData > 0 ){
                    return R.error(400,"该巡检项已上传巡检数据，若删除则无法查看巡检数据，无法删除。");
                }

                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put("device_id", item.getDeviceId());
                List<ZoneDeviceEntity>  zoneDevices = zoneDeviceService.selectByMap(params);
                for(ZoneDeviceEntity zoneDeviceEntity:zoneDevices){
                    HashMap<String, Object> lineZoneParams = new HashMap<String, Object>();
                    lineZoneParams.put("zone_id", zoneDeviceEntity.getZoneId());
                    List<LineZoneEntity> lineZoneEntities = lineZoneService.selectByMap(lineZoneParams);
                    for(LineZoneEntity lineZoneEntity:lineZoneEntities){
                        InspectionLineEntity line = lineService.selectById(lineZoneEntity.getLineId());
                        if(line != null && line.getIsPublish().equals(1)){
                            return R.error(400,"该巡检项对应巡检线路[" + line.getName() + "]已发布，不能删除。");
                        }
                    }
                }

            }
        }

        boolean isOK = inspectionItemService.deleteBatchIds(Arrays.asList(ids));

        if(isOK){
            return R.ok();
        }else{
            return R.error(500,"服务器错误");
        }
    }
}
