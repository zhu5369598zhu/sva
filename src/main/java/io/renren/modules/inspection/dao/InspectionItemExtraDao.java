package io.renren.modules.inspection.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.renren.modules.inspection.entity.InspectionItemExtraEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InspectionItemExtraDao extends BaseMapper<InspectionItemExtraEntity> {
    /**
     * 根据巡检项ID，获取扩展ID列表
     */
    List<Integer> queryExtraIdList(Integer itemId);


    /**
     * 根据扩展ID数组，批量删除
     */
    Integer deleteBatch(Integer[] ExtraIds);

    InspectionItemExtraEntity selectByName(@Param("name") String name);
    InspectionItemExtraEntity selectByGuid(@Param("guid") String guid);
}
