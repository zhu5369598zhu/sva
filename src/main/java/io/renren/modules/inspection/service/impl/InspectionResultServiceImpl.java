package io.renren.modules.inspection.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.MapUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.inspection.dao.InspectionResultDao;
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

import java.text.SimpleDateFormat;
import java.util.*;


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
    @Autowired
    InspectionTaskService taskService;
    @Autowired
    InspectionTaskDeviceService taskDeviceService;

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

        if(startTime != null && !startTime.equals("")) {
            resultEntity.setStartTime(startTime);
        }

        if(endTime != null && !endTime.equals("")) {
            resultEntity.setEndTime(endTime);
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
        String itemId = (String)params.get("itemId");
        List<InspectionResultEntity> resultList = selectAll(params);
        HashMap<String, Object> json = new HashMap<>();
        if(!"".equals(itemId)){
            String[] ids = new String[resultList.size()];
            String[] category = new String[resultList.size()];
            String[] series = new String[resultList.size()];

            Integer k = 0;
            for(Integer i = resultList.size() -1; i >= 0; i--){
                InspectionResultEntity result = (InspectionResultEntity)resultList.get(i);
                ids[k] = result.getId().toString();
                category[k] = result.getStartTime().toString();
                series[k] = result.getResult();
                k = k + 1;
            }

            json.put("ids", ids);
            json.put("category", category);
            json.put("series", series);
            return json;
        }else{
            json.put("ids", null);
            json.put("category", null);
            json.put("series", null);
            return json;
        }


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
        if(startTime != null && !startTime.equals("")) {
            resultEntity.setStartTime(startTime);
        }

        if(endTime != null && !endTime.equals("")) {
            resultEntity.setEndTime(endTime);
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
        if(startTime != null && !startTime.equals("")) {
            resultEntity.setStartTime(startTime);
        }

        if(endTime != null && !endTime.equals("")) {
            resultEntity.setEndTime(endTime);
        }

        List<Map<String,Object>> resultExceptionList = this.baseMapper.selectExceptionGroupByDevice(
                resultEntity
        );

        LinkedHashSet<String>  legend = new LinkedHashSet();
        LinkedHashSet<String>  category = new LinkedHashSet();
        HashMap<String, Object> series = new HashMap();
        HashMap<String, Object> yAxis = new HashMap<>();
        HashMap<String, Object> json = new HashMap<>();
        /*LinkedHashSet<Long> normalList = new LinkedHashSet();
        LinkedHashSet<Long> unNormalList = new LinkedHashSet();
        LinkedHashSet<Long> allList = new LinkedHashSet();*/
        ArrayList<Long> normalList = new ArrayList<>();
        ArrayList<Long> unNormalList = new ArrayList<>();
        ArrayList<Long> allList = new ArrayList<>();
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
    public Map<String,Object> selectExceptionGroupByTime(Map<String, Object> params) {
        String startTime = (String)params.get("startTime");
        String type = (String)params.get("type");

        List<Map<String,Object>> resultExceptionList = this.baseMapper.selectExceptionGroupByTime(startTime,type);

        LinkedHashSet<String>  legend = new LinkedHashSet();
        LinkedHashSet<String>  category = new LinkedHashSet();
        HashMap<String, Object> series = new HashMap();
        HashMap<String, Object> yAxis = new HashMap<>();
        HashMap<String, Object> json = new HashMap<>();
        List<Long> aList = new ArrayList<>();
        List<Long> bList = new ArrayList<>();
        List<Long> cList = new ArrayList<>();
        for(Map<String,Object> item:resultExceptionList){
            category.add(item.get("time").toString());
            HashMap<String, Object> time = (HashMap<String, Object>)yAxis.get(item.get("time").toString());
            if ( time == null){
                HashMap<String, Object> exception = new HashMap<>();
                if(item.get("name").toString().equals("A类")) {
                    exception.put("A类",(Long)item.get("count"));
                }else {
                    exception.put("A类",0L);
                }
                if(item.get("name").toString().equals("B类")){
                    exception.put("B类",(Long)item.get("count"));
                }else {
                    exception.put("B类",0L);
                }
                if(item.get("name").toString().equals("C类")){
                    exception.put("C类",(Long)item.get("count"));
                }else{
                    exception.put("C类",0L);
                }
                yAxis.put(item.get("time").toString(), exception);
            } else {
                if(item.get("name").toString().equals("A类")) {
                    Long a = (Long)time.get("A类");
                    time.remove("A类");
                    a = a + (Long)item.get("count");
                    time.put("A类",a);
                }else if(item.get("name").toString().equals("B类")) {
                    Long b = (Long)time.get("B类");
                    time.remove("B类");
                    b += (Long)item.get("count");
                    time.put("B类",b);
                }else if(item.get("name").toString().equals("C类")){
                    Long c = (Long)time.get("C类");
                    time.remove("C类");
                    c += (Long)item.get("count");
                    time.put("C类",c);
                }
                yAxis.remove(item.get("time").toString());
                yAxis.put(item.get("time").toString(), time);
            }
        }
        for(String item: category){
            HashMap<String, Object> time = (HashMap<String, Object>)yAxis.get(item);
            Long a = (Long)time.get("A类");
            aList.add(a);
            Long b = (Long)time.get("B类");
            bList.add(b);
            Long c = (Long)time.get("C类");
            cList.add(c);
        }

        if (resultExceptionList.size() > 0){
            series.put("A", aList);
            series.put("B", bList);
            series.put("C", cList);
            legend.add("A类");
            legend.add("B类");
            legend.add("C类");
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
        if(startTime != null && !startTime.equals("")) {
            resultEntity.setStartTime(startTime);
        }

        if(endTime != null && !endTime.equals("")) {
            resultEntity.setEndTime(endTime);
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
        if(startTime != null && !startTime.equals("")) {
            resultEntity.setStartTime(startTime);
        }

        if(endTime != null && !endTime.equals("")) {
            resultEntity.setEndTime(endTime);
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
        if(exceptionId == 1){
            result.setIsOk(1);
        }else {
            result.setIsOk(0);
        }
        result.setGuid(UUID.randomUUID().toString());
        result.setCreateTime(new java.util.Date());

        result.setItemId(item.getId());

        Integer id = this.baseMapper.insert(result);
        if(id > 0){
            HashMap itemParams = new HashMap();
            itemParams.put("id",result.getItemId());
            List<InspectionItemEntity>  itemList= itemService.selectByMap(itemParams);
            if(itemList.size() > 0){
                InspectionItemEntity itemEntity = itemList.get(0);
                HashMap taskDeviceParams = new HashMap();
                taskDeviceParams.put("turn_id",turn.getId());
                taskDeviceParams.put("line_id",turn.getInspectionLineId());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                taskDeviceParams.put("inspection_date",simpleDateFormat.format(new Date()));
                Date dt = new Date();
                InspectionTaskDeviceEntity taskDeviceEntity = null;
                List<InspectionTaskDeviceEntity> taskDeviceList = taskDeviceService.selectByMap(taskDeviceParams);
                if(taskDeviceList.size() > 0){
                    taskDeviceEntity = taskDeviceList.get(0);
                    taskDeviceEntity.setInspectedItemCount(taskDeviceEntity.getInspectedItemCount() + 1);
                    if(taskDeviceEntity.getInspectedItemCount() >= taskDeviceEntity.getInsepctItemCount()){
                        taskDeviceEntity.setIsUpdate(0);
                        HashMap taskParams = new HashMap();
                        taskParams.put("line_id",turn.getInspectionLineId());
                        taskParams.put("turn_id",turn.getId());
                        taskParams.put("inspection_span_end_date",simpleDateFormat.format(new Date()));
                        List<InspectionTaskEntity> taskList = taskService.selectByMap(taskParams);
                        if(taskList.size() >0){
                            InspectionTaskEntity task = taskList.get(0);
                            task.setIsInspected(1);
                            task.setInspectedDeviceCount(task.getInspectedDeviceCount() + 1);
                            task.setInspectedItemCount(taskDeviceEntity.getInspectedItemCount());
                            taskService.updateById(task);
                        }
                    }else{
                        taskDeviceEntity.setIsUpdate(1);
                    }
                    taskDeviceService.updateById(taskDeviceEntity);
                }else{
                    taskDeviceEntity = new InspectionTaskDeviceEntity();
                    taskDeviceEntity.setTurnId(turn.getId());
                    taskDeviceEntity.setDeviceId(itemEntity.getDeviceId());
                    taskDeviceEntity.setInspectedItemCount(1);
                    taskDeviceEntity.setInspectionDate(dt);
                    taskDeviceEntity.setIsUpdate(1);
                    taskDeviceService.insert(taskDeviceEntity);
                }
            }
            return result.getGuid();
        }

        return "";
    }

}
