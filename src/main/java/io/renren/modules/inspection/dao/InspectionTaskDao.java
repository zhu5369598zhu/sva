package io.renren.modules.inspection.dao;

import io.renren.modules.inspection.entity.InspectionTaskEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-08-11 16:55:04
 */
@Mapper
public interface InspectionTaskDao extends BaseMapper<InspectionTaskEntity> {
    List<Map<String,Object>> selectByDate(@Param("inspectDate") String inspectionDate);
    List<Map<String,Object>> selectListByDate(@Param("inspectionStartDate") String inspectionStartDate,@Param("inspectionEndDate")  String inspectionEndDate);
	
}
