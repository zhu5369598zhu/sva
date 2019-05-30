package io.renren.modules.inspection.dao;

import io.renren.modules.inspection.entity.InspectionLinePublishEntity;
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
public interface InspectionLinePublishDao extends BaseMapper<InspectionLinePublishEntity> {
    public InspectionLinePublishEntity selectByParams(@Param("lineId") Integer lineId, @Param("pdaId") Integer pdaId);
    public Integer insertPublishBatch(List<InspectionLinePublishEntity> publishList);
    public Integer updateDownload(@Param("lineId") Integer lineId, @Param("isDownload") Integer isDownload);
}
