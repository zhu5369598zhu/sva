package io.renren.modules.inspection.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.modules.inspection.dao.DeviceCurrentStatusDao;
import io.renren.modules.inspection.entity.*;
import io.renren.modules.inspection.service.*;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service("deviceCurrentStatusService")
public class DeviceCurrentStatusServiceImpl extends ServiceImpl<DeviceCurrentStatusDao, DeviceCurrentStatusEntity> implements DeviceCurrentStatusService {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private InspectionTaskService taskService;

    @Autowired
    private InspectionTaskDeviceService taskDeviceService;

    @Autowired
    private SysUserService userService;

    @Autowired
    private InspectedDateService inspectedDateService;

    @Autowired
    private TurnService turnService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DeviceCurrentStatusEntity> page = this.selectPage(
                new Query<DeviceCurrentStatusEntity>(params).getPage(),
                new EntityWrapper<DeviceCurrentStatusEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public R updateDeviceStatus(DeviceCurrentStatusEntity deviceCurrentStatusEntity) {
        String userGuid = deviceCurrentStatusEntity.getUserGuid();
        if(userGuid == null){
            return R.error(1,"用户userGuid不能为空");
        }
        Map<String,Object> userHashMap = new HashMap<String,Object>();
        userHashMap.put("guid", userGuid);
        userHashMap.put("is_delete", 0);
        List<SysUserEntity> users = userService.selectByMap(userHashMap);
        SysUserEntity user = null;
        if(users.size() > 0){
            user = users.get(0);
        }else{
            return R.error(1,"用户不能为空");
        }
        String turnGuid = deviceCurrentStatusEntity.getTurnGuid();
        if(turnGuid == null){
            return R.error(1,"轮次Guid不能为空");
        }
        HashMap<String, Object> turnMap = new HashMap<>();
        turnMap.put("guid", turnGuid);
        List<TurnEntity> turnEntityList = turnService.selectByMap(turnMap);
        TurnEntity turnEntity =null;
        if(turnEntityList.size()>0){
            turnEntity = turnEntityList.get(0);
        }else {
            return R.error(1,"轮次不能为空");
        }
        String deviceGuid = deviceCurrentStatusEntity.getDeviceGuid();
        if (deviceGuid == null){
            return R.error(1,"设备Guid 不能为空");
        }
        HashMap<String, Object> deviceMap = new HashMap<>();
        deviceMap.put("guid", deviceCurrentStatusEntity.getDeviceGuid());
        List<DeviceEntity> deviceEntityList = deviceService.selectByMap(deviceMap);
        DeviceEntity device = null;
        Integer deviceId = 0 ;
        if(deviceEntityList.size()>0){
            device = deviceEntityList.get(0);
            deviceId = deviceEntityList.get(0).getDeviceId();
        }else {
            return R.error(1,"设备不能为空");
        }

        HashMap<String, Object> dateMap = new HashMap<>();
        dateMap.put("user_id", user.getUserId());
        List<InspectedDateEntity> inspectedDateList = inspectedDateService.selectByMap(dateMap);
        if(inspectedDateList.size()>0){
            if(inspectedDateList.get(0).getInspectionDate().compareTo(deviceCurrentStatusEntity.getCurrentDate())>0){ // 左侧时间大于右侧参数时间
                return R.error(1,"上传的巡检时间不能小于最后一次上传的巡检时间");
            }else {
                InspectedDateEntity inspectedDateEntity = inspectedDateList.get(0);
                inspectedDateEntity.setInspectionDate(deviceCurrentStatusEntity.getCurrentDate());
                inspectedDateService.updateById(inspectedDateEntity);
            }
        }else { // 更改用户最后巡检时间
            InspectedDateEntity inspectedDateEntity = new InspectedDateEntity();
            inspectedDateEntity.setInspectionDate(deviceCurrentStatusEntity.getCurrentDate());
            inspectedDateEntity.setUserId(user.getUserId().intValue());
            inspectedDateService.insert(inspectedDateEntity);
        }
        // 改变设备状态
        device.setDeviceStatus(Integer.parseInt(deviceCurrentStatusEntity.getDeviceState()));
        deviceService.updateById(device);
        HashMap<String, Object> map = new HashMap<>();
        map.put("device_id", deviceId);
        List<DeviceCurrentStatusEntity> deviceCurrentStatusList = this.baseMapper.selectByMap(map);
        if(deviceCurrentStatusList.size()>0){
            DeviceCurrentStatusEntity currentStatusEntity = deviceCurrentStatusList.get(0);
            currentStatusEntity.setCurrentDate(deviceCurrentStatusEntity.getCurrentDate());
            currentStatusEntity.setDeviceState(deviceCurrentStatusEntity.getDeviceState());
            this.baseMapper.updateById(currentStatusEntity);
        }else{
            this.baseMapper.insert(deviceCurrentStatusEntity);
        }
        // 这次上传 修改 状态
        HashMap taskDeviceParams = new HashMap();
        taskDeviceParams.put("turn_id",turnEntity.getId());
        taskDeviceParams.put("device_id",deviceId);
        taskDeviceParams.put("line_id",turnEntity.getInspectionLineId());
        taskDeviceParams.put("inspection_date",deviceCurrentStatusEntity.getCurrentDate());// 巡检时间
        InspectionTaskDeviceEntity taskDeviceEntity = null;
        List<InspectionTaskDeviceEntity> taskDeviceList = taskDeviceService.selectByMap(taskDeviceParams);
        if(taskDeviceList.size()>0){
            taskDeviceEntity = taskDeviceList.get(0);
            if(taskDeviceEntity.getDeviceState().equals(deviceCurrentStatusEntity.getDeviceState())){ // 如果是相同的
            }else{// 和上次状态不同
                taskDeviceEntity.setDeviceState(Integer.parseInt(deviceCurrentStatusEntity.getDeviceState()));
                taskDeviceService.updateById(taskDeviceEntity);
            }
            HashMap taskParams = new HashMap();
            taskParams.put("line_id",turnEntity.getInspectionLineId());
            taskParams.put("turn_id",turnEntity.getId());
            taskParams.put("inspection_span_end_date",deviceCurrentStatusEntity.getCurrentDate());
            List<InspectionTaskEntity> taskList = taskService.selectByMap(taskParams);
            InspectionTaskEntity task =null;
            if(taskDeviceEntity.getDeviceState()!= 1){  // 设备不是运行状态
                taskDeviceEntity.setInspectedItems(taskDeviceEntity.getInspectItems());
                taskDeviceEntity.setInspectedItemCount(taskDeviceEntity.getInspectItemCount());
                taskDeviceEntity.setIsUpdate(1);
                taskDeviceEntity.setIsInspected(1);
                taskDeviceService.updateById(taskDeviceEntity);
                if(taskList.size()>0){
                    task = taskList.get(0);
                    if(task !=null){
                        String inspectedDevices = task.getInspectedDevices();
                        if(inspectedDevices == null){
                            task.setInspectedDevices(taskDeviceEntity.getDeviceId() + ",");
                            task.setInspectedDeviceCount(task.getInspectedDeviceCount() + 1);
                            task.setInspectedItemCount(task.getInspectedItemCount()+ taskDeviceEntity.getInspectItemCount());
                        }else{ // 判断该设备之前是否全部巡检完成
                            String[] DeviceArray = inspectedDevices.split(",");
                            boolean flag = Arrays.asList(DeviceArray).contains(taskDeviceEntity.getDeviceId().toString());
                            if(flag){
                            }else { // 如果之前没有巡检完 加入到巡检完成的集合中
                                task.setInspectedDevices(task.getInspectedDevices() + taskDeviceEntity.getDeviceId() + ",");
                                task.setInspectedDeviceCount(task.getInspectedDeviceCount() + 1);
                                Integer inspectItemCount = taskDeviceEntity.getInspectItemCount();
                                Integer inspectedItemCount = taskDeviceEntity.getInspectedItemCount();
                                task.setInspectedItemCount(task.getInspectedItemCount() + inspectItemCount - inspectedItemCount);
                            }
                        }
                    }
                    taskService.updateById(task);
                }else{
                    return R.error(1,"该轮次没有巡检设备巡检项任务");
                }
            }
        }else {
            return R.error(1,"该轮次没有巡检设备巡检项任务");
        }
        return R.ok();
    }

    @Override
    public R text(DeviceCurrentStatusEntity deviceCurrentStatusEntity) {
        String userGuid = deviceCurrentStatusEntity.getUserGuid();
        if(userGuid == null){
            return R.error(1,"用户userGuid不能为空");
        }
        Map<String,Object> userHashMap = new HashMap<String,Object>();
        userHashMap.put("guid", userGuid);
        userHashMap.put("is_delete", 0);
        List<SysUserEntity> users = userService.selectByMap(userHashMap);
        SysUserEntity user = null;
        if(users.size() > 0){
            user = users.get(0);
        }else{
            return R.error(1,"用户不能为空");
        }
        String turnGuid = deviceCurrentStatusEntity.getTurnGuid();
        if(turnGuid == null){
            return R.error(1,"轮次TurnGuid不能为空");
        }
        HashMap<String, Object> turnMap = new HashMap<>();
        turnMap.put("guid", turnGuid);
        List<TurnEntity> turnEntityList = turnService.selectByMap(turnMap);
        TurnEntity turnEntity =null;
        if(turnEntityList.size()>0){
            turnEntity = turnEntityList.get(0);
        }else {
            return R.error(1,"轮次不能为空");
        }
        String deviceGuid = deviceCurrentStatusEntity.getDeviceGuid();
        if (deviceGuid == null){
            return R.error(1,"设备Guid 不能为空");
        }
        HashMap<String, Object> deviceMap = new HashMap<>();
        deviceMap.put("guid", deviceCurrentStatusEntity.getDeviceGuid());
        List<DeviceEntity> deviceEntityList = deviceService.selectByMap(deviceMap);
        DeviceEntity device = null;
        Integer deviceId = 0 ;
        if(deviceEntityList.size()>0){
            device = deviceEntityList.get(0);
            deviceId = deviceEntityList.get(0).getDeviceId();
        }else {
            return R.error(1,"设备不能为空");
        }

        String deviceState = deviceCurrentStatusEntity.getDeviceState();
        if(deviceState ==null){
            return R.error(1,"设备状态deviceState不能为空");
        }
        Date currentDate = deviceCurrentStatusEntity.getCurrentDate();
        if(currentDate == null){
            return R.error(1,"巡检日期不能为空");
        }

        return R.ok();
    }

}
