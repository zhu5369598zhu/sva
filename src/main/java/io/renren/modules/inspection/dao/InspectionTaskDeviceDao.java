package io.renren.modules.inspection.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import io.renren.modules.inspection.entity.InspectionTaskDeviceEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
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
public interface InspectionTaskDeviceDao extends BaseMapper<InspectionTaskDeviceEntity> {
    List<Map<String,Object>> selectMissingStatisticsByDate(@Param("lineId") String lineId, @Param("deptIds") List<Integer> deptIds, @Param("inspectStartDate") String inspectStartDate,@Param("inspectEndDate")  String inspectEndDate);
    List<Map<String,Object>> selectMissingDetailByDate(@Param("lineId") String lineId, @Param("deptIds") List<Integer> deptIds, @Param("inspectStartDate") String inspectStartDate, @Param("inspectEndDate")  String inspectEndDate);
    List<Map<String,Object>> selectMissingDetailByDate(Pagination page, @Param("lineId") String lineId, @Param("deptIds") List<Integer> deptIds, @Param("inspectStartDate") String inspectStartDate, @Param("inspectEndDate")  String inspectEndDate);
    List<Map<String,Object>> selectMissingLineByDate(@Param("lineId") String lineId, @Param("deptIds") List<Integer> deptIds,  @Param("inspectStartDate") String inspectStartDate, @Param("inspectEndDate")  String inspectEndDate);
    List<Map<String,Object>> selectMissingLineByDate(Pagination page, @Param("lineId") String lineId, @Param("deptIds") List<Integer> deptIds, @Param("inspectStartDate") String inspectStartDate, @Param("inspectEndDate")  String inspectEndDate);
    List<Map<String,Object>> selectMissingTurnByDate(@Param("lineId") String lineId, @Param("deptIds") List<Integer> deptIds, @Param("inspectStartDate") String inspectStartDate,@Param("inspectEndDate")  String inspectEndDate);
    List<Map<String,Object>> selectMissingTurnByDate(Pagination page, @Param("lineId") String lineId, @Param("deptIds") List<Integer> deptIds, @Param("inspectStartDate") String inspectStartDate,@Param("inspectEndDate")  String inspectEndDate);
    Map<String, Object> selectByParams(@Param("lineId") String lineId, @Param("turnId") String turnId, @Param("turnStartTime") String turnStartTime, @Param("turnEndTime") String turnEndTime);
    List<Map<String, Object>> getDeviceByTime(Page<Map<String, Object>> page,@Param("lineId") String lineId,@Param("deptIds") List<Integer> deptIds,@Param("inspectStartDate") String inspectStartTime,@Param("inspectEndDate") String inspectEndTime);
    List<Map<String, Object>> getAllDeviceByTime(String lineId, List<Integer> deptIds, String inspectStartTime, String inspectEndTime);
}
