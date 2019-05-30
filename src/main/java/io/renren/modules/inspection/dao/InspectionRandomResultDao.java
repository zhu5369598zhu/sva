package io.renren.modules.inspection.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import io.renren.modules.inspection.entity.InspectionRandomResultEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-03-15 11:43:31
 */
@Mapper
public interface InspectionRandomResultDao extends BaseMapper<InspectionRandomResultEntity> {
    public List<InspectionRandomResultEntity> selectResultList(Pagination page, InspectionRandomResultEntity result);
    public List<InspectionRandomResultEntity> selectResultList(InspectionRandomResultEntity result);
    InspectionRandomResultEntity selectByGuid(String guid);
    InspectionRandomResultEntity selectByAppResultGuid(String appResultGuid);
    void deleteByAppResultGuid(String appResultGuid);
}
