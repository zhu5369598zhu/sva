package io.renren.modules.inspection.dao;

import io.renren.modules.inspection.entity.DeviceDocEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-04-26 12:20:16
 */
@Mapper
public interface DeviceDocDao extends BaseMapper<DeviceDocEntity> {
    DeviceDocEntity selectByGuid(String guid);
    void deleteByGuid(String guid);
}
