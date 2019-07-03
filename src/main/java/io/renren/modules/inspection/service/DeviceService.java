package io.renren.modules.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.inspection.entity.DeviceEntity;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-01-21 03:10:48
 */
public interface DeviceService extends IService<DeviceEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 获取设备树
     * @param deviceName
     */
    public List<Map<String,Object>> findDeviceTree(String deviceName);

    /**
     * 获取设备树
     */
    public List<Map<String,Object>> findLineTree(Map<String, Object> params);

    /**
     * 获取设备异常统计信息
     */
    public List<Map<String,Object>> getDeviceStatus();

    /**
     * 获取设备异常统计排名
     */
    public List<Map<String,Object>> getDeviceExceptionTop(Integer deviceLevelId, String startTime,String endTime);

    /**
     * 根据部门id查询设备
     * @param deptId 部门ID
     */
    List<Map<String,Object>> findDeviceList(Long deptId, String deviceName);

    /**
     * 根据线路id查寻设备
     * @param lineId 线路id
     */
    List<Map<String,Object>> selectByLine(Long lineId);

    /**
     * 获取未绑定到巡区的设备列表
     */
    public List<DeviceEntity> findDeviceUnBind(String filterField, String key, Long zoneId);


    public List<DeviceEntity> all (Map<String, Object> params);

    public DeviceEntity selectByCode(Integer deviceId, String deviceCode);

    public Integer isExist(String deviceName, String deviceCode);

    public DeviceEntity selectByDeviceName(String deviceName);

    public DeviceEntity selectByDeviceCode(String deviceCode);
}

