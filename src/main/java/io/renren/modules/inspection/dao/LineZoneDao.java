package io.renren.modules.inspection.dao;

import io.renren.modules.inspection.entity.LineZoneEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-02-01 17:02:21
 */
@Mapper
public interface LineZoneDao extends BaseMapper<LineZoneEntity> {

    Integer insertZoneBatch(List<LineZoneEntity> lineZoneEntityList);
	
}
