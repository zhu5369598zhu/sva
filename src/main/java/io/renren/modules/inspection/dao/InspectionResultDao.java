package io.renren.modules.inspection.dao;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import io.renren.modules.inspection.entity.InspectionResultEntity;
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
 * @date 2019-02-13 11:25:57
 */
@Mapper
public interface InspectionResultDao extends BaseMapper<InspectionResultEntity> {

	public List<InspectionResultEntity> selectResultList(Pagination page, InspectionResultEntity result);
	public List<InspectionResultEntity> selectResultList(InspectionResultEntity result);
	public List<Map<String,Object>> selectExceptionGroupByDevice(InspectionResultEntity result);
	public List<Map<String,Object>> selectExceptionGroupByItem(InspectionResultEntity result);
	public List<Map<String,Object>> selectExceptionGroupByTime(@Param("startTime") String startTime,@Param("type") String type);
	InspectionResultEntity selectByGuid(String guid);
	InspectionResultEntity selectByAppResultGuid(String appResultGuid);
	void deleteByAppResultGuid(String appResultGuid);
}
