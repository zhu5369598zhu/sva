package io.renren.modules.inspection.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.inspection.dao.DeviceDao;
import io.renren.modules.inspection.entity.DeviceEntity;
import io.renren.modules.inspection.entity.ZoneDeviceEntity;
import io.renren.modules.inspection.entity.ZoneEntity;
import io.renren.modules.inspection.service.*;
import io.renren.modules.setting.entity.DeviceLevelEntity;
import io.renren.modules.setting.service.DeviceLevelService;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.service.SysDeptService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service("deviceService")
public class DeviceServiceImpl extends ServiceImpl<DeviceDao, DeviceEntity> implements DeviceService {

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private ZoneDeviceService zoneDeviceService;
    @Autowired
    private SysDeptService deptService;
    @Autowired
    private InspectionLineService lineService;
    @Autowired
    private InspectionItemService itemService;
    @Autowired
    private DeviceLevelService levelService;
    @Autowired
    private ZoneService zoneService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String filterfield = "";
        String deptId = (String)params.get("deptId");
        String filterkey = (String)params.get("filterkey");
        if (filterkey.equals("deviceName")) {
            filterfield = "device_name";
        }
        if (filterkey.equals("deviceCode")) {
            filterfield = "device_code";
        }
        Integer[] array = null;
        if(!"".equals(deptId)&& deptId != null){
            List<Integer> deptIds = deptService.queryRecursiveChildByParentId(Long.parseLong(deptId));
            Integer[] deptIdsIn = new Integer[deptIds.size()];
            array = deptIds.toArray(deptIdsIn);
        }
        String key = (String)params.get("key");

        Page<DeviceEntity> page = this.selectPage(
                new Query<DeviceEntity>(params).getPage(),
                new EntityWrapper<DeviceEntity>()
                        .like(StringUtils.isNotBlank(key),filterfield, key)
                        // .eq( deptId != null , "device_dept", deptId)
                        .in(array !=null,"device_dept",array)
                        .eq("is_delete",0)
        );

        for(DeviceEntity deviceEntity : page.getRecords()){
            SysDeptEntity deptEntity = deptService.selectById(deviceEntity.getDeviceDept());
            if(deptEntity != null){
                deviceEntity.setDeptName(deptEntity.getName());
            }
        }
        for(DeviceEntity deviceEntity : page.getRecords()){
            DeviceLevelEntity level = levelService.selectById(deviceEntity.getDeviceLevel());
            deviceEntity.setDeviceLevelName(level.getName());
        }
        return new PageUtils(page);
    }

    @Override
    public List<DeviceEntity> all (Map<String, Object> params) {
        String filterfield = "";
        String deptId = (String)params.get("deptId");
        String filterkey = (String)params.get("filterkey");
        if (filterkey.equals("deviceName")) {
            filterfield = "device_name";
        }
        if (filterkey.equals("deviceCode")) {
            filterfield = "device_code";
        }
        Integer[] array = null;
        if(!"".equals(deptId)&& deptId != null){
            List<Integer> deptIds = deptService.queryRecursiveChildByParentId(Long.parseLong(deptId));
            Integer[] deptIdsIn = new Integer[deptIds.size()];
            array = deptIds.toArray(deptIdsIn);
        }
        String key = (String)params.get("key");

        List<DeviceEntity> list = this.selectList(
                new EntityWrapper<DeviceEntity>()
                .like(StringUtils.isNotBlank(key),filterfield, key)
                //.eq( deptId != null , "device_dept", deptId)
                .in(array !=null,"device_dept",array)
                .eq("is_delete",0)
        );

        for(DeviceEntity deviceEntity : list){
            SysDeptEntity deptEntity = deptService.selectById(deviceEntity.getDeviceDept());
            if(deptEntity != null){
                deviceEntity.setDeptName(deptEntity.getName());
            }

            DeviceLevelEntity level = levelService.selectById(deviceEntity.getDeviceLevel());
            if(level !=null){
                deviceEntity.setDeviceLevelName(level.getName());
            }

            if (deviceEntity.getIsInspect().equals(1)) {
                deviceEntity.setIsInspectName("是");
            } else {
                deviceEntity.setIsInspectName("否");
            }

            if (deviceEntity.getIsShowDashboard().equals(1)) {
                deviceEntity.setIsShowDashboardName("是");
            } else {
                deviceEntity.setIsShowDashboardName("否");
            }

            List<String> zoneList = new ArrayList<>();
            HashMap<String, Object> zoneDeviceParams = new HashMap<>();
            zoneDeviceParams.put("device_id", deviceEntity.getDeviceId());
            List<ZoneDeviceEntity> zoneDeviceEntities = zoneDeviceService.selectByMap(zoneDeviceParams);
            for(ZoneDeviceEntity zoneDevice: zoneDeviceEntities){
                ZoneEntity zone = zoneService.selectById(zoneDevice.getZoneId());
                if (zone != null){
                    zoneList.add(zone.getZoneName());
                }
            }
            deviceEntity.setZones(org.apache.commons.lang.StringUtils.join(zoneList.toArray(), '/'));
        }
        return list;
    }

    @Override
    public List<DeviceEntity> findDeviceUnBind(String filterField, String key, Long zoneId){
        Integer isInspect = 1;
        Long deptId = null;
        ZoneEntity zone = zoneService.selectById(zoneId);
        if (zone != null){
            deptId = zone.getDeptId();
        }
        List<DeviceEntity> deviceList = deviceService.selectList(
                new EntityWrapper<DeviceEntity>()
                        .like(StringUtils.isNotBlank(key),filterField, key)
                        .eq( deptId != null , "device_dept", deptId)
                        .eq(isInspect != null, "is_inspect", isInspect)
                        .eq("is_delete",0)
        );
        List<Map<String, Object>> zoneDeviceList = zoneDeviceService.findDeviceByzoneId(zoneId);

        Iterator<DeviceEntity> item = deviceList.iterator();
        while (item.hasNext()){
            DeviceEntity device = item.next();
            for (Map<String, Object> m : zoneDeviceList) {
                if (device.getDeviceId().equals(m.get("device_id"))) {
                    if(item != null){
                        item.remove();
                    }
                }
            }
        }

        return deviceList;
    }

    @Override
    public List<Map<String,Object>> findDeviceTree(String deviceNmae){
        List<Map<String,Object>> root = deptService.queryListParentId(0l);

        for(Map<String, Object> m : root){
            getTreeNodeData(m, deviceNmae);
        }

        return clearNode(root);
    }

    @Override
    public DeviceEntity selectByCode(Integer deviceId, String deviceCode){
        return baseMapper.selectByCode(deviceId, deviceCode);
    }

    private void getTreeNodeData(Map<String,Object> node, String deviceName){
        List<Map<String,Object>> devices = this.findDeviceList((Long)node.get("id"), deviceName);

        List<Map<String,Object>> children = deptService.queryListParentId((Long)node.get("id"));
        if(children != null && children.size() > 0 ){
            children.addAll(devices);
            node.put("children", children);
            for(Map<String, Object> m : children){
                getTreeNodeData(m, deviceName);
            }
        } else {
            node.put("children", devices);
        }
    }



    /**
     * 获取设备树
     */
    public List<Map<String,Object>> findLineTree(Map<String, Object> params){
        String inspectionType = (String)params.get("inspectionType");
        List<Map<String,Object>> root = deptService.queryListParentId(0l);

        for(Map<String, Object> m : root){
            getLineTreeNodeData(m, Integer.valueOf(inspectionType));
        }

        return clearNode(root);
    }

    /**
     * 获取设备异常统计信息
     */
    public List<Map<String,Object>> getDeviceExceptionTop(ArrayList<Integer> deviceLevelId, String startTime,String endTime){
        return baseMapper.getDeviceExceptionTop(deviceLevelId,startTime,endTime);
    }

    /**
     * 获取设备异常统计信息
     */
    public List<Map<String,Object>> getDeviceStatus(Map<String, Object> params){
        String startTime = (String)params.get("startTime");
        String endTime = (String)params.get("endTime");
        return baseMapper.getDeviceStatus(startTime,endTime);
    }

    private void getLineTreeNodeData(Map<String,Object> node, Integer inspectionType){
        List<Map<String,Object>> lines = lineService.selectByDept((Long)node.get("id"));
        logger.error("node:" + node.toString());
        logger.error("lines:" + lines.toString());


        for(Map<String,Object> line:lines){
            List<Map<String,Object>> devices = deviceService.selectByLine((Long)line.get("id"));
            line.put("children", devices);
            for(Map<String,Object> device:devices){
                List<Map<String,Object>> items = itemService.selectByDevice((Long)device.get("id"), inspectionType);
                device.put("children", items);
            }
        }


        if (node.get("type").equals("dept")){
            List<Map<String,Object>> children = deptService.queryListParentId((Long)node.get("id"));
            logger.error("children:" + children.toString());
            if(children != null && children.size() > 0 ){
                children.addAll(lines);
                node.put("children", children);
                for(Map<String, Object> m : children){
                    getLineTreeNodeData(m, inspectionType);
                }
            } else {
                node.put("children", lines);
            }
        }
    }

    /**
     * 获取设备树
     */
    public List<Map<String,Object>> findOnlyLineTree(Map<String, Object> params){
        List<Map<String,Object>> root = deptService.queryListParentId(0l);

        for(Map<String, Object> m : root){
            getOnlyLineTreeNodeData(m);
        }

        return clearNode(root);
    }

    private void getOnlyLineTreeNodeData(Map<String,Object> node){
        List<Map<String,Object>> lines = lineService.selectByDept((Long)node.get("id"));
        logger.error("node:" + node.toString());
        logger.error("lines:" + lines.toString());


        if (node.get("type").equals("dept")){
            List<Map<String,Object>> children = deptService.queryListParentId((Long)node.get("id"));
            logger.error("children:" + children.toString());
            if(children != null && children.size() > 0 ){
                children.addAll(lines);
                node.put("children", children);
                for(Map<String, Object> m : children){
                    getOnlyLineTreeNodeData(m);
                }
            } else {
                node.put("children", lines);
            }
        }
    }

    public List<Map<String,Object>> clearNode(List<Map<String,Object>> curNode){

        Iterator<Map<String,Object>> item = curNode.iterator();
        while (item.hasNext()){
            Map<String, Object> node = item.next();
            String type = (String)node.get("type");
            if(type.equals("dept")) {
                List<Map<String,Object>> children = (List<Map<String,Object>>)node.get("children");
                if (children.size() == 0){
                    item.remove();
                } else {
                    clearNode(children);
                }
            }
        }

        return curNode;

    }

    public Map<String,Object> getParentNode(Map<String,Object> curNode, Map<String,Object>parentNode){
        List<Map<String,Object>> children = (List<Map<String,Object>>)parentNode.get("children");
        Iterator<Map<String,Object>> item = children.iterator();
        while (item.hasNext()) {
            Map<String,Object> node = (Map<String,Object>)item.next();
            if(node == curNode) {
                return parentNode;
            } else {
                 getParentNode(curNode, node);
            }
        }

        return null;
    }


    @Override
    public List<Map<String,Object>> findDeviceList(Long deptId, String deviceName) {
        return baseMapper.findDeviceList(deptId, deviceName);
    }

    @Override
    public List<Map<String,Object>> selectByLine(Long lineId) {
        return baseMapper.selectByLine(lineId);
    }

    @Override
    public Integer isExist(String deviceName, String deviceCode){
        return baseMapper.isExist(deviceName, deviceCode);
    }

    @Override
    public DeviceEntity selectByDeviceName(String deviceName){
        return baseMapper.selectByDeviceName(deviceName);
    }

    @Override
    public DeviceEntity selectByDeviceCode(String deviceCode){
        return baseMapper.selectByDeviceCode(deviceCode);
    }

}
