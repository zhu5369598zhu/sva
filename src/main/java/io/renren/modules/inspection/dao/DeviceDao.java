package io.renren.modules.inspection.dao;

import io.renren.modules.inspection.entity.DeviceEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-01-21 03:10:48
 */
@Mapper
public interface DeviceDao extends BaseMapper<DeviceEntity> {

    List<Map<String,Object>> findDeviceList(@Param("deptId") Long deptId, @Param("deviceName") String deviceName);

    List<Map<String,Object>> selectByLine(@Param("lineId") Long lineId);

    DeviceEntity selectByCode(@Param("deviceId") Integer deviceId, @Param("deviceCode") String deviceCode);

    Integer isExist(@Param("deviceName") String deviceName, @Param("deviceCode") String deviceCode);

    DeviceEntity selectByDeviceName(@Param("deviceName") String deviceName);

    DeviceEntity selectByDeviceCode(@Param("deviceCode") String deviceCode);

    List<Map<String,Object>> getDeviceStatus();

    List<Map<String,Object>> getDeviceExceptionTop(@Param("levelIds") ArrayList<Integer> levelIds, @Param("startTime") String startTime, @Param("endTime") String endTime);

}
