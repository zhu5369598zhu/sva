package io.renren.modules.inspection.dao;

import io.renren.modules.inspection.entity.ClassGroupEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-01-29 17:34:03
 */
@Mapper
public interface ClassGroupDao extends BaseMapper<ClassGroupEntity> {

    public void insertClassGroup(ClassGroupEntity classGroupEntity);
	
}
