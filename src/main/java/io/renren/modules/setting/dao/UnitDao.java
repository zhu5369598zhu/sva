package io.renren.modules.setting.dao;

import io.renren.modules.setting.entity.UnitEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-01-29 17:38:32
 */
@Mapper
public interface UnitDao extends BaseMapper<UnitEntity> {
    UnitEntity selectByName(@Param("name") String name);
}
