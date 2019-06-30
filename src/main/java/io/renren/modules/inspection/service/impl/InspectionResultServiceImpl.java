package io.renren.modules.inspection.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.renren.common.utils.FftUtils;
import io.renren.common.utils.MapUtils;
import io.renren.modules.inspection.entity.*;
import io.renren.modules.inspection.service.*;
import io.renren.modules.setting.entity.ExceptionEntity;
import io.renren.modules.setting.entity.UnitEntity;
import io.renren.modules.setting.service.ExceptionService;
import io.renren.modules.setting.service.UnitService;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysDeptService;
import io.renren.modules.sys.service.SysUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.inspection.dao.InspectionResultDao;


@Service("inspectionResultService")
public class InspectionResultServiceImpl extends ServiceImpl<InspectionResultDao, InspectionResultEntity> implements InspectionResultService {

    private Integer OK = 1;
    private Integer NOTOK = 0;
    private Integer OBSERVE = 8;
    private Integer ENTERING = 2;

    private Integer EXCEPTIONOK = 1;
    private Integer EXCEPTIONWARN = 3;
    private Integer EXCEPTIONDANGER = 6;

    @Autowired
    private InspectionItemService itemService;
    @Autowired
    private SysUserService userService;
    @Autowired
    private TurnService turnService;
    @Autowired
    private ExceptionService exceptionService;
    @Autowired
    InspectionResultMediaService mediaService;
    @Autowired
    SysDeptService deptService;
    @Autowired
    InspectionItemExtraService extraService;
    @Autowired
    InspectionItemPresuppositionService presuppositionService;
    @Autowired
    UnitService unitService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<InspectionResultEntity> page = this.selectPage(
                new Query<InspectionResultEntity>(params).getPage(),
                new EntityWrapper<InspectionResultEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<InspectionResultEntity> selectAll(Map<String, Object> params) {
        String id = (String)params.get("id");
        String isOk = (String)params.get("isOk");
        String isHere = (String)params.get("isHere");
        String deptId = (String)params.get("deptId");
        String deviceName = (String)params.get("deviceName");
        String itemName = (String)params.get("itemName");
        String username = (String)params.get("username");
        String exceptionId = (String)params.get("exceptionId");
        String statusId = (String)params.get("statusId");
        String deviceLevelId = (String)params.get("deviceLevelId");
        String inspectionTypeId = (String)params.get("inspectionTypeId");
        String startTime = (String)params.get("startTime");
        String endTime = (String)params.get("endTime");
        String itemId = (String)params.get("itemId");

        InspectionResultEntity resultEntity = new InspectionResultEntity();
        if(id != null && !id.equals("")){
            resultEntity.setId(Integer.parseInt(id));
        }
        if(deviceName != null){
            resultEntity.setDeviceName(deviceName);
        }
        if(itemName != null){
            resultEntity.setItemName(itemName);
        }
        if(username != null){
            resultEntity.setUsername(username);
        }
        if(isOk != null && !isOk.equals("")){
            resultEntity.setIsOk(Integer.parseInt(isOk));
        }
        if(isHere != null && !isHere.equals("")){
            resultEntity.setIsHere(Integer.parseInt(isHere));
        }
        if(deptId != null && !deptId.equals("")){
            List<Integer> deptIds = deptService.queryRecursiveChildByParentId(Long.parseLong(deptId));
            resultEntity.setDeptIds(deptIds);
        }
        if(itemId != null && !itemId.equals("")){
            resultEntity.setItemId(Integer.parseInt(itemId));
        }
        if(statusId != null && !statusId.equals("")){
            resultEntity.setStatusId(Integer.parseInt(statusId));
        }
        if(exceptionId != null && !exceptionId.equals("")){
            resultEntity.setExceptionId(Integer.parseInt(exceptionId));
        }
        if(deviceLevelId != null && !deviceLevelId.equals("")){
            resultEntity.setDeviceLevelId(Integer.parseInt(deviceLevelId));
        }
        if(inspectionTypeId != null && !inspectionTypeId.equals("")) {
            resultEntity.setInspectionTypeId(Integer.parseInt(inspectionTypeId));
        }
        if(startTime != null && !startTime.equals("")){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try{
                resultEntity.setStartTime(sdf.parse(startTime));
            }catch(java.text.ParseException e){

            }

        }
        if(endTime != null && !endTime.equals("")){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try{
                resultEntity.setEndTime(sdf.parse(endTime));
            }catch(java.text.ParseException e){

            }
        }

        List<InspectionResultEntity> resultList = this.baseMapper.selectResultList(resultEntity);

        for(InspectionResultEntity result: resultList){
            result.setDeviceName(result.getDeviceName() + "[" + result.getDeviceCode() + "]");
            if(result.getIsHere().equals(0)) {
                result.setIsHereName("否");
            } else if(result.getIsHere().equals(1)){
                result.setIsHereName("是");
            }
            if(result.getIsOk().equals(0)){
                result.setIsOkName("否");
            } else if(result.getIsOk().equals(1)){
                result.setIsOkName("是");
            }
            if(result.getIsCheck().equals(0)){
                result.setIsCheckName("否");
            } else if(result.getIsCheck().equals(1)){
                result.setIsCheckName("是");
            }

            //如果是抄表，单位重新读取
            if (result.getInspectionType().equals("抄表")) {
                UnitEntity unit = unitService.selectById(result.getUnit());
                if(unit != null){
                    result.setUnit(unit.getName());
                }
            }

            if(result.getInspectionType().equals("观察") && result.getResult() != null){
                InspectionItemExtraEntity extraEntity = extraService.selectByGuid(result.getResult());
                ExceptionEntity exceptionEntity = exceptionService.selectById(extraEntity.getExceptionId());
                if(exceptionEntity != null){
                    result.setExceptionId(exceptionEntity.getId());
                    result.setExceptionName(exceptionEntity.getName());
                    result.setResult(exceptionEntity.getName());
                }else{
                    result.setResult("无效数据");
                }
            }

            if(result.getInspectionType().equals("预设状况") && result.getResult() != null){
                LinkedHashSet presuppositionList = new LinkedHashSet();
                try{
                    JSONArray jsonArr = JSONArray.parseArray(result.getResult());
                    for (int i = 0; i < jsonArr.size(); i++){
                        String el = jsonArr.getString(i);
                        InspectionItemPresuppositionEntity presupposition = presuppositionService.selectByGuid(el);
                        if (presupposition != null) {
                            presuppositionList.add(presupposition.getName());
                        }
                    }
                    result.setResult(org.apache.commons.lang.StringUtils.join(presuppositionList.toArray(), '/'));
                }catch (Exception e) {

                }
            }

        }
        return resultList;
    }

    @Override
    public Map<String,Object> selectByItem(Map<String, Object> params) {
        List<InspectionResultEntity> resultList = selectAll(params);

        String[] ids = new String[resultList.size()];
        String[] category = new String[resultList.size()];
        String[] series = new String[resultList.size()];

        HashMap<String, Object> json = new HashMap<>();
        for(Integer i = 0; i < resultList.size(); i++){
            InspectionResultEntity result = (InspectionResultEntity)resultList.get(i);
            ids[i] = result.getId().toString();
            category[i] = result.getStartTime().toString();
            series[i] = result.getResult();
        }

        json.put("ids", ids);
        json.put("category", category);
        json.put("series", series);
        return json;
    }

    @Override
    public Map<String,Object> selectException(Map<String, Object> params) {
        String lineId = (String)params.get("lineId");
        String deviceId = (String)params.get("deviceId");
        String itemId = (String)params.get("itemId");
        String isOk = (String)params.get("isOk");
        String isHere = (String)params.get("isHere");
        String deptId = (String)params.get("deptId");
        String deviceName = (String)params.get("deviceName");
        String itemName = (String)params.get("itemName");
        String username = (String)params.get("username");
        String statusId = (String)params.get("statusId");
        String exceptionId = (String)params.get("exceptionId");
        String deviceLevelId = (String)params.get("deviceLevelId");
        String inspectionTypeId = (String)params.get("inspectionTypeId");
        String startTime = (String)params.get("startTime");
        String endTime = (String)params.get("endTime");

        InspectionResultEntity resultEntity = new InspectionResultEntity();
        if(lineId != null && !lineId.equals("")){
            resultEntity.setLineId(Integer.parseInt(lineId));
        }
        if(deviceName != null){
            resultEntity.setDeviceName(deviceName);
        }
        if(deviceId != null && !deviceId.equals("")){
            resultEntity.setDeviceId(Integer.parseInt(deviceId));
        }
        if(itemName != null){
            resultEntity.setItemName(itemName);
        }
        if(itemId != null && !itemId.equals("")){
            resultEntity.setItemId(Integer.parseInt(itemId));
        }
        if(username != null){
            resultEntity.setUsername(username);
        }
        if(isOk != null && !isOk.equals("")){
            resultEntity.setIsOk(Integer.parseInt(isOk));
        }
        if(isHere != null && !isHere.equals("")){
            resultEntity.setIsHere(Integer.parseInt(isHere));
        }
        if(deptId != null && !deptId.equals("")){
            List<Integer> deptIds = deptService.queryRecursiveChildByParentId(Long.parseLong(deptId));
            resultEntity.setDeptIds(deptIds);
        }
        if(statusId != null && !statusId.equals("")){
            resultEntity.setStatusId(Integer.parseInt(statusId));
        }
        if(exceptionId != null && !exceptionId.equals("")){
            resultEntity.setExceptionId(Integer.parseInt(exceptionId));
        }
        if(deviceLevelId != null && !deviceLevelId.equals("")){
            resultEntity.setDeviceLevelId(Integer.parseInt(deviceLevelId));
        }
        if(inspectionTypeId != null && !inspectionTypeId.equals("")) {
            resultEntity.setInspectionTypeId(Integer.parseInt(inspectionTypeId));
        }
        if(startTime != null && !startTime.equals("")){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try{
                resultEntity.setStartTime(sdf.parse(startTime));
            }catch(java.text.ParseException e){

            }

        }
        if(endTime != null && !endTime.equals("")){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try{
                resultEntity.setEndTime(sdf.parse(endTime));
            }catch(java.text.ParseException e){

            }
        }

        List<Map<String,Object>> resultExceptionList = this.baseMapper.selectExceptionGroupByDevice(
                resultEntity
        );

        LinkedHashSet<String>  legend = new LinkedHashSet();
        LinkedHashSet series = new LinkedHashSet();
        HashMap<String, Object> json = new HashMap<>();
        Long normal = 0L;
        Long unNormal = 0L;
        HashMap<String, Object> normalHash = new HashMap();
        HashMap<String, Object> unNormalHash = new HashMap();
        for(Map<String,Object> item:resultExceptionList){
            if(item.get("exception").toString().equals("正常") || item.get("exception").toString().equals("一般")){
                normal += (Long)item.get("count");
            }else {
                unNormal += (Long)item.get("count");
            }
        }

        if (resultExceptionList.size() > 0){
            normalHash.put("value", normal);
            normalHash.put("name", "正常");
            unNormalHash.put("value", unNormal);
            unNormalHash.put("name", "异常");
            series.add(unNormalHash);
            series.add(normalHash);
            legend.add("异常");
            legend.add("正常");
        }
        json.put("legend",legend);
        json.put("series", series);
        return json;
    }

    @Override
    public Map<String,Object> selectExceptionGroupByDevice(Map<String, Object> params) {
        String lineId = (String)params.get("lineId");
        String deviceId = (String)params.get("deviceId");
        String itemId = (String)params.get("itemId");
        String isOk = (String)params.get("isOk");
        String isHere = (String)params.get("isHere");
        String deptId = (String)params.get("deptId");
        String deviceName = (String)params.get("deviceName");
        String itemName = (String)params.get("itemName");
        String username = (String)params.get("username");
        String statusId = (String)params.get("statusId");
        String exceptionId = (String)params.get("exceptionId");
        String deviceLevelId = (String)params.get("deviceLevelId");
        String inspectionTypeId = (String)params.get("inspectionTypeId");
        String startTime = (String)params.get("startTime");
        String endTime = (String)params.get("endTime");

        InspectionResultEntity resultEntity = new InspectionResultEntity();
        if(lineId != null && !lineId.equals("")){
            resultEntity.setLineId(Integer.parseInt(lineId));
        }
        if(deviceName != null){
            resultEntity.setDeviceName(deviceName);
        }
        if(deviceId != null && !deviceId.equals("")){
            resultEntity.setDeviceId(Integer.parseInt(deviceId));
        }
        if(itemName != null){
            resultEntity.setItemName(itemName);
        }
        if(itemId != null && !itemId.equals("")){
            resultEntity.setItemId(Integer.parseInt(itemId));
        }
        if(username != null){
            resultEntity.setUsername(username);
        }
        if(isOk != null && !isOk.equals("")){
            resultEntity.setIsOk(Integer.parseInt(isOk));
        }
        if(isHere != null && !isHere.equals("")){
            resultEntity.setIsHere(Integer.parseInt(isHere));
        }
        if(deptId != null && !deptId.equals("")){
            List<Integer> deptIds = deptService.queryRecursiveChildByParentId(Long.parseLong(deptId));
            resultEntity.setDeptIds(deptIds);
        }
        if(statusId != null && !statusId.equals("")){
            resultEntity.setStatusId(Integer.parseInt(statusId));
        }
        if(exceptionId != null && !exceptionId.equals("")){
            resultEntity.setExceptionId(Integer.parseInt(exceptionId));
        }
        if(deviceLevelId != null && !deviceLevelId.equals("")){
            resultEntity.setDeviceLevelId(Integer.parseInt(deviceLevelId));
        }
        if(inspectionTypeId != null && !inspectionTypeId.equals("")) {
            resultEntity.setInspectionTypeId(Integer.parseInt(inspectionTypeId));
        }
        if(startTime != null && !startTime.equals("")){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try{
                resultEntity.setStartTime(sdf.parse(startTime));
            }catch(java.text.ParseException e){

            }

        }
        if(endTime != null && !endTime.equals("")){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try{
                resultEntity.setEndTime(sdf.parse(endTime));
            }catch(java.text.ParseException e){

            }
        }

        List<Map<String,Object>> resultExceptionList = this.baseMapper.selectExceptionGroupByDevice(
                resultEntity
        );

        LinkedHashSet<String>  legend = new LinkedHashSet();
        LinkedHashSet<String>  category = new LinkedHashSet();
        HashMap<String, Object> series = new HashMap();
        HashMap<String, Object> yAxis = new HashMap<>();
        HashMap<String, Object> json = new HashMap<>();
        LinkedHashSet<Long> normalList = new LinkedHashSet();
        LinkedHashSet<Long> unNormalList = new LinkedHashSet();
        LinkedHashSet<Long> allList = new LinkedHashSet();
        for(Map<String,Object> item:resultExceptionList){
            category.add(item.get("deviceName").toString());
            HashMap<String, Object> device = (HashMap<String, Object>)yAxis.get(item.get("deviceName").toString());
            if ( device == null){
                HashMap<String, Object> exception = new HashMap<>();
                if(item.get("exception").toString().equals("正常") || item.get("exception").toString().equals("一般")){
                    exception.put("正常",(Long)item.get("count"));
                    exception.put("异常",0L);
                }else{
                    exception.put("异常",(Long)item.get("count"));
                    exception.put("正常",0L);
                }
                exception.put("全部",(Long)item.get("count"));
                yAxis.put(item.get("deviceName").toString(), exception);
            } else {
                if(item.get("exception").toString().equals("正常") || item.get("exception").toString().equals("一般")) {
                    Long normal = (Long)device.get("正常");
                    device.remove("正常");
                    normal = normal + (Long)item.get("count");
                    device.put("正常",normal);
                }else{
                    Long normal = (Long)device.get("异常");
                    device.remove("异常");
                    normal += (Long)item.get("count");
                    device.put("异常",normal);
                }
                Long all = (Long)device.get("全部");
                device.remove("全部");
                all += (Long)item.get("count");
                device.put("全部",all);
                yAxis.remove(item.get("deviceName").toString());
                yAxis.put(item.get("deviceName").toString(), device);
            }
        }
        for(String item: category){
            HashMap<String, Object> device = (HashMap<String, Object>)yAxis.get(item);
            Long unNormal = (Long)device.get("异常");
            unNormalList.add(unNormal);
            Long normal = (Long)device.get("正常");
            normalList.add(normal);
            Long all = (Long)device.get("全部");
            allList.add(all);
        }

        if (resultExceptionList.size() > 0){
            series.put("异常", unNormalList);
            series.put("正常", normalList);
            series.put("全部", allList);
            legend.add("异常");
            legend.add("正常");
            legend.add("全部");
        }
        json.put("legend",legend);
        json.put("category", category);
        json.put("series", series);
        return json;
    }

    @Override
    public Map<String,Object> selectExceptionGroupByItem(Map<String, Object> params) {
        String lineId = (String)params.get("lineId");
        String deviceId = (String)params.get("deviceId");
        String itemId = (String)params.get("itemId");
        String isOk = (String)params.get("isOk");
        String isHere = (String)params.get("isHere");
        String deptId = (String)params.get("deptId");
        String deviceName = (String)params.get("deviceName");
        String itemName = (String)params.get("itemName");
        String username = (String)params.get("username");
        String statusId = (String)params.get("statusId");
        String exceptionId = (String)params.get("exceptionId");
        String deviceLevelId = (String)params.get("deviceLevelId");
        String inspectionTypeId = (String)params.get("inspectionTypeId");
        String startTime = (String)params.get("startTime");
        String endTime = (String)params.get("endTime");

        InspectionResultEntity resultEntity = new InspectionResultEntity();
        if(lineId != null && !lineId.equals("")){
            resultEntity.setLineId(Integer.parseInt(lineId));
        }
        if(deviceName != null){
            resultEntity.setDeviceName(deviceName);
        }
        if(deviceId != null && !deviceId.equals("")){
            resultEntity.setDeviceId(Integer.parseInt(deviceId));
        }
        if(itemName != null){
            resultEntity.setItemName(itemName);
        }
        if(itemId != null && !itemId.equals("")){
            resultEntity.setItemId(Integer.parseInt(itemId));
        }
        if(username != null){
            resultEntity.setUsername(username);
        }
        if(isOk != null && !isOk.equals("")){
            resultEntity.setIsOk(Integer.parseInt(isOk));
        }
        if(isHere != null && !isHere.equals("")){
            resultEntity.setIsHere(Integer.parseInt(isHere));
        }
        if(deptId != null && !deptId.equals("")){
            List<Integer> deptIds = deptService.queryRecursiveChildByParentId(Long.parseLong(deptId));
            resultEntity.setDeptIds(deptIds);
        }
        if(statusId != null && !statusId.equals("")){
            resultEntity.setStatusId(Integer.parseInt(statusId));
        }
        if(exceptionId != null && !exceptionId.equals("")){
            resultEntity.setExceptionId(Integer.parseInt(exceptionId));
        }
        if(deviceLevelId != null && !deviceLevelId.equals("")){
            resultEntity.setDeviceLevelId(Integer.parseInt(deviceLevelId));
        }
        if(inspectionTypeId != null && !inspectionTypeId.equals("")) {
            resultEntity.setInspectionTypeId(Integer.parseInt(inspectionTypeId));
        }
        if(startTime != null && !startTime.equals("")){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try{
                resultEntity.setStartTime(sdf.parse(startTime));
            }catch(java.text.ParseException e){

            }

        }
        if(endTime != null && !endTime.equals("")){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try{
                resultEntity.setEndTime(sdf.parse(endTime));
            }catch(java.text.ParseException e){

            }
        }
        
        List<Map<String,Object>> resultExceptionList = this.baseMapper.selectExceptionGroupByItem(
                resultEntity
        );

        LinkedHashSet<String>  legend = new LinkedHashSet();
        LinkedHashSet<String>  category = new LinkedHashSet();
        HashMap<String, Object> series = new HashMap();
        HashMap<String, Object> yAxis = new HashMap<>();
        HashMap<String, Object> json = new HashMap<>();
        LinkedHashSet<Long> normalList = new LinkedHashSet();
        LinkedHashSet<Long> unNormalList = new LinkedHashSet();
        LinkedHashSet<Long> allList = new LinkedHashSet();
        for(Map<String,Object> item:resultExceptionList){
            category.add(item.get("itemName").toString());
            HashMap<String, Object> device = (HashMap<String, Object>)yAxis.get(item.get("itemName").toString());
            if ( device == null){
                HashMap<String, Object> exception = new HashMap<>();
                if(item.get("exception").toString().equals
                        ("正常") || item.get("exception").toString().equals("一般")){
                    exception.put("正常",(Long)item.get("count"));
                    exception.put("异常",0L);
                }else{
                    exception.put("异常",(Long)item.get("count"));
                    exception.put("正常",0L);
                }
                exception.put("全部",(Long)item.get("count"));
                yAxis.put(item.get("itemName").toString(), exception);
            } else {
                if(item.get("exception").toString().equals("正常") || item.get("exception").toString().equals("一般")) {
                    Long normal = (Long)device.get("正常");
                    device.remove("正常");
                    normal = normal + (Long)item.get("count");
                    device.put("正常",normal);
                }else{
                    Long normal = (Long)device.get("异常");
                    device.remove("异常");
                    normal += (Long)item.get("count");
                    device.put("异常",normal);
                }
                Long all = (Long)device.get("全部");
                device.remove("全部");
                all += (Long)item.get("count");
                device.put("全部",all);
                yAxis.remove(item.get("itemName").toString());
                yAxis.put(item.get("itemName").toString(), device);
            }
        }
        for(String item: category){
            HashMap<String, Object> device = (HashMap<String, Object>)yAxis.get(item);
            Long unNormal = (Long)device.get("异常");
            unNormalList.add(unNormal);
            Long normal = (Long)device.get("正常");
            normalList.add(normal);
            Long all = (Long)device.get("全部");
            allList.add(all);
        }

        if (resultExceptionList.size() > 0){
            series.put("异常", unNormalList);
            series.put("正常", normalList);
            series.put("全部", allList);
            legend.add("异常");
            legend.add("正常");
            legend.add("全部");
        }
        json.put("legend",legend);
        json.put("category", category);
        json.put("series", series);
        return json;
    }

    @Override
    public PageUtils selectResultPage(Map<String, Object> params)  {
        String id = (String)params.get("id");
        String lineId = (String)params.get("lineId");
        String deviceId = (String)params.get("deviceId");
        String itemId = (String)params.get("itemId");
        String isOk = (String)params.get("isOk");
        String isHere = (String)params.get("isHere");
        String deptId = (String)params.get("deptId");
        String deviceName = (String)params.get("deviceName");
        String itemName = (String)params.get("itemName");
        String username = (String)params.get("username");
        String statusId = (String)params.get("statusId");
        String exceptionId = (String)params.get("exceptionId");
        String deviceLevelId = (String)params.get("deviceLevelId");
        String inspectionTypeId = (String)params.get("inspectionTypeId");
        String startTime = (String)params.get("startTime");
        String endTime = (String)params.get("endTime");

        InspectionResultEntity resultEntity = new InspectionResultEntity();
        if(id != null && !id.equals("")){
            resultEntity.setId(Integer.parseInt(id));
        }
        if(lineId != null && !lineId.equals("")){
            resultEntity.setLineId(Integer.parseInt(lineId));
        }
        if(deviceName != null){
            resultEntity.setDeviceName(deviceName);
        }
        if(deviceId != null && !deviceId.equals("")){
            resultEntity.setDeviceId(Integer.parseInt(deviceId));
        }
        if(itemName != null){
            resultEntity.setItemName(itemName);
        }
        if(itemId != null && !itemId.equals("")){
            resultEntity.setItemId(Integer.parseInt(itemId));
        }
        if(username != null){
            resultEntity.setUsername(username);
        }
        if(isOk != null && !isOk.equals("")){
            resultEntity.setIsOk(Integer.parseInt(isOk));
        }
        if(isHere != null && !isHere.equals("")){
            resultEntity.setIsHere(Integer.parseInt(isHere));
        }
        if(deptId != null && !deptId.equals("")){
            List<Integer> deptIds = deptService.queryRecursiveChildByParentId(Long.parseLong(deptId));
            resultEntity.setDeptIds(deptIds);
        }
        if(statusId != null && !statusId.equals("")){
            resultEntity.setStatusId(Integer.parseInt(statusId));
        }
        if(exceptionId != null && !exceptionId.equals("")){
            resultEntity.setExceptionId(Integer.parseInt(exceptionId));
        }
        if(deviceLevelId != null && !deviceLevelId.equals("")){
            resultEntity.setDeviceLevelId(Integer.parseInt(deviceLevelId));
        }
        if(inspectionTypeId != null && !inspectionTypeId.equals("")) {
            resultEntity.setInspectionTypeId(Integer.parseInt(inspectionTypeId));
        }
        if(startTime != null && !startTime.equals("")){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try{
                resultEntity.setStartTime(sdf.parse(startTime));
            }catch(java.text.ParseException e){

            }

        }
        if(endTime != null && !endTime.equals("")){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try{
                resultEntity.setEndTime(sdf.parse(endTime));
            }catch(java.text.ParseException e){

            }
        }

        Page<InspectionResultEntity> page = new Query<InspectionResultEntity>(params).getPage();
        List<InspectionResultEntity> list = this.baseMapper.selectResultList(
                page,
                resultEntity
        );


        for(InspectionResultEntity result: list){
            //如果是抄表，单位重新读取
            if (result.getInspectionType().equals("抄表")) {
                UnitEntity unit = unitService.selectById(result.getUnit());
                if(unit != null){
                    result.setUnit(unit.getName());
                }
            }

            if(result.getInspectionType().equals("观察") && result.getResult() != null){
                InspectionItemExtraEntity extraEntity = extraService.selectByGuid(result.getResult());
                if(extraEntity != null){
                    ExceptionEntity exceptionEntity = exceptionService.selectById(extraEntity.getExceptionId());
                    if(exceptionEntity != null){
                        result.setExceptionId(exceptionEntity.getId());
                        result.setExceptionName(exceptionEntity.getName());
                        result.setResult(exceptionEntity.getName());
                    }else{
                        result.setResult("无效数据");
                    }
                }
            }

            if(result.getInspectionType().equals("预设状况") && result.getResult() != null){
                LinkedHashSet presuppositionList = new LinkedHashSet();
                try{
                    JSONArray jsonArr = JSONArray.parseArray(result.getResult());
                    for (int i = 0; i < jsonArr.size(); i++){
                        String el = jsonArr.getString(i);
                        InspectionItemPresuppositionEntity presupposition = presuppositionService.selectByGuid(el);
                        if (presupposition != null) {
                            presuppositionList.add(presupposition.getName());
                        }else {
                            result.setResult("无效数据");
                        }
                    }
                    result.setResult(org.apache.commons.lang.StringUtils.join(presuppositionList.toArray(), '/'));
                }catch (Exception e) {

                }
            }

            List<InspectionResultMediaEntity> mediaEntityList = mediaService.selectListByResultId(result.getId());
            result.setMedias(mediaEntityList);
            result.setDeviceName(result.getDeviceName() + "[" + result.getDeviceCode() + "]");
            if(result.getUpLimit() != null && result.getUpupLimit() != null && result.getDownLimit() !=null && result.getDowndownLimit() !=null)
            result.setLimits(result.getUpupLimit().toString() + "/" + result.getUpLimit().toString() + "/" +
                    result.getDownLimit().toString() + "/" + result.getDowndownLimit().toString());
        }
        page.setRecords(list);
        return new PageUtils(page);
    }

    @Override
    public InspectionResultEntity selectByGuid(String guid){
        return baseMapper.selectByGuid(guid);
    }

    @Override
    public InspectionResultEntity selectByAppResultGuid(String appResultGuid){
        return baseMapper.selectByAppResultGuid(appResultGuid);
    }

    @Override
    public void deleteByAppResultGuid(String appResultGuid) {
        InspectionResultEntity result = this.selectByAppResultGuid(appResultGuid);
        if(result != null){
            mediaService.deleteByMap(new MapUtils().put("result_id", result.getId()));
        }
        baseMapper.deleteByAppResultGuid(appResultGuid);
    }

    @Override
    public String insertResult(InspectionResultEntity result){
        TurnEntity turn = turnService.selectByGuid(result.getTurnGuid());
        if(turn != null){
            result.setTurnId(turn.getId().intValue());
        } else {
            return "轮次没有找到";
        }
        SysUserEntity user = userService.selectByGuid(result.getUserGuid());
        if(user != null){
            result.setUserId(user.getUserId().intValue());
        } else {
            return "用户没有找到";
        }

        ExceptionEntity exception = exceptionService.selectById(result.getExceptionId());
        if(exception == null && result.getExceptionId() != 0){
            return "异常信息没有找到";
        }

        InspectionItemEntity item = itemService.selectByGuid(result.getItemGuid());
        if(item != null){
            result.setItemId(item.getId());
        } else {
            return "巡检项没有找到";
        }

        Integer exceptionId = result.getExceptionId();
        if(exceptionId > 1){
            result.setIsOk(0);
        }else if (exceptionId == 0){
            result.setIsOk(1);
        }
        result.setGuid(UUID.randomUUID().toString());
        result.setCreateTime(new java.util.Date());

        result.setItemId(item.getId());

        Integer id = this.baseMapper.insert(result);
        if(id > 0){
            return result.getGuid();
        }

        return "";
    }

}
