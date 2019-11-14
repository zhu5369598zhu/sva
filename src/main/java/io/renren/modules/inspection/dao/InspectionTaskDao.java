package io.renren.modules.inspection.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import io.renren.modules.inspection.entity.InspectionTaskEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    List<Map<String,Object>> selectByDate(@Param("inspectDate") String inspectEndDate);
    List<Map<String,Object>> selectAbsenceStatisticsByDate(@Param("lineId") String lineId, @Param("deptIds") List<Integer> deptIds, @Param("inspectStartDate") String inspectStartDate,@Param("inspectEndDate")  String inspectEndDate);
    List<Map<String,Object>> selectAbsenceDetailByDate(@Param("lineId") String lineId, @Param("deptIds") List<Integer> deptIds, @Param("inspectStartDate") String inspectStartDate, @Param("inspectEndDate")  String inspectEndDate);
    List<Map<String,Object>> selectAbsenceDetailByDate(Pagination page, @Param("lineId") String lineId, @Param("deptIds") List<Integer> deptIds, @Param("inspectStartDate") String inspectStartDate, @Param("inspectEndDate")  String inspectEndDate);
    List<Map<String,Object>> selectAbsenceLineByDate(@Param("lineId") String lineId, @Param("deptIds") List<Integer> deptIds,  @Param("inspectStartDate") String inspectStartDate, @Param("inspectEndDate")  String inspectEndDate);
    List<Map<String,Object>> selectAbsenceLineByDate(Pagination page, @Param("lineId") String lineId, @Param("deptIds") List<Integer> deptIds, @Param("inspectStartDate") String inspectStartDate, @Param("inspectEndDate")  String inspectEndDate);
    List<Map<String,Object>> selectAbsenceTurnByDate(@Param("lineId") String lineId, @Param("deptIds") List<Integer> deptIds, @Param("inspectStartDate") String inspectStartDate,@Param("inspectEndDate")  String inspectEndDate);
    List<Map<String,Object>> selectAbsenceTurnByDate(Pagination page, @Param("lineId") String lineId, @Param("deptIds") List<Integer> deptIds, @Param("inspectStartDate") String inspectStartDate,@Param("inspectEndDate")  String inspectEndDate);
    List<Map<String, Object>> getLineByTime(Pagination page, @Param("lineId") String lineId, @Param("deptIds") List<Integer> deptIds,@Param("inspectStartDate") String inspectStartTime,@Param("inspectEndDate") String inspectEndTime);
}
