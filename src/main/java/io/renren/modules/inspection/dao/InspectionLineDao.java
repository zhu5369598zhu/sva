package io.renren.modules.inspection.dao;

import io.renren.modules.inspection.entity.InspectionLineEntity;
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
 * @date 2019-01-29 17:34:03
 */
@Mapper
public interface InspectionLineDao extends BaseMapper<InspectionLineEntity> {

    public List<Map<String,Object>> selectByDept(Long deptId);

    public List<InspectionLineEntity> selectByMac(@Param("mac") String mac);
	
}
