package io.renren.modules.inspection.dao;

import io.renren.modules.inspection.entity.InspectionRandomResultMediaEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-03-15 11:43:31
 */
@Mapper
public interface InspectionRandomResultMediaDao extends BaseMapper<InspectionRandomResultMediaEntity> {
    InspectionRandomResultMediaEntity selectByGuid(String guid);
    List<InspectionRandomResultMediaEntity> selectListByResultId(@Param("result_id") Integer resultId);
}
