package io.renren.modules.inspection.dao;

import io.renren.modules.inspection.entity.InspectionResultMediaEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-02-25 16:42:27
 */
@Mapper
public interface InspectionResultMediaDao extends BaseMapper<InspectionResultMediaEntity> {
    InspectionResultMediaEntity selectByGuid(String guid);
    List<InspectionResultMediaEntity> selectListByResultId(@Param("result_id") Integer resultId);
}
