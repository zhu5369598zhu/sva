package io.renren.modules.inspection.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.renren.modules.inspection.entity.InspectionItemExtraEntity;
import io.renren.modules.inspection.entity.InspectionItemPresuppositionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InspectionItemPresuppositionDao extends BaseMapper<InspectionItemPresuppositionEntity> {
    /**
     * 根据巡检项ID，获取扩展ID列表
     */
    List<Integer> queryPresuppositionIdList(Integer itemId);


    /**
     * 根据扩展ID数组，批量删除
     */
    Integer deleteBatch(Integer[] PresuppositionIds);

    InspectionItemPresuppositionEntity selectByGuid(@Param("guid") String guid);
}
