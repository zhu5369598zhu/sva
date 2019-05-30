package io.renren.modules.setting.dao;

import io.renren.modules.setting.entity.InspectionTypeEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-01-22 11:36:34
 */
@Mapper
public interface InspectionTypeDao extends BaseMapper<InspectionTypeEntity> {

    InspectionTypeEntity selectByName(@Param("name") String name);
	
}
