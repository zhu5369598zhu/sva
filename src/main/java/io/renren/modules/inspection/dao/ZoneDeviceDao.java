package io.renren.modules.inspection.dao;

import io.renren.modules.inspection.entity.ZoneDeviceEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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
public interface ZoneDeviceDao extends BaseMapper<ZoneDeviceEntity> {

    List<Map<String, Object>> findDeviceByZoneId(Long zoneId);

    Integer insertDeviceBatch(List<ZoneDeviceEntity> zoneDeviceEntityList);
}
