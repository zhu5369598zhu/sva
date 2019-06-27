package io.renren.modules.inspection.dao;

import io.renren.modules.inspection.entity.ZoneEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-01-21 03:10:48
 */
@Mapper
public interface ZoneDao extends BaseMapper<ZoneEntity> {

    Integer isExist(@Param("zoneName") String zoneName, @Param("zoneCode") String zoneCode);
    ZoneEntity selectByName(@Param("zoneName") String zoneName);
    public ZoneEntity selectByZoneCode(@Param("zoneCode") String zoneCode);
}
