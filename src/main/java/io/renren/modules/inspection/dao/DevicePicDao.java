package io.renren.modules.inspection.dao;

import io.renren.modules.inspection.entity.DevicePicEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-04-26 12:20:16
 */
@Mapper
public interface DevicePicDao extends BaseMapper<DevicePicEntity> {
    DevicePicEntity selectByGuid(String guid);
    void deleteByGuid(String guid);
    List<Map<String, Object>> selectByDeviceId (Integer deviceId);
}
