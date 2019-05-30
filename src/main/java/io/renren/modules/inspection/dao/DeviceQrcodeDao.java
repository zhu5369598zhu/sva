package io.renren.modules.inspection.dao;

import io.renren.modules.inspection.entity.DeviceQrcodeEntity;
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
public interface DeviceQrcodeDao extends BaseMapper<DeviceQrcodeEntity> {
    DeviceQrcodeEntity selectByDeviceId(Integer deviceId);
}
