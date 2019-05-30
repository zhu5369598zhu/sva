package io.renren.modules.setting.dao;

import io.renren.modules.setting.entity.DeviceLevelEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-01-21 19:32:48
 */
@Mapper
public interface DeviceLevelDao extends BaseMapper<DeviceLevelEntity> {
    DeviceLevelEntity selectByName(@Param("levelName") String levelName);
}
