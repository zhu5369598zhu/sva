package io.renren.modules.inspection.dao;

import io.renren.modules.inspection.entity.InspectionItemEntity;
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
 * @date 2019-01-22 17:33:37
 */
@Mapper
public interface InspectionItemDao extends BaseMapper<InspectionItemEntity> {
    /**
     * 根据ItemID，获取扩展ID列表
     */
    List<Long> queryExtraIdList(Long turnId);

    InspectionItemEntity selectByGuid(String guid);

    List<Map<String,Object>> selectByDevice(@Param("deviceId") Long deviceId, @Param("inspectionType") Integer inspectionType);

    /**
     * 根据ItemID数组，批量删除
     */
    Long deleteBatch(Long[] classGroupIds);

    Integer isExist(@Param("itemName") String itemName, @Param("deviceCode") String deviceCode);
}
