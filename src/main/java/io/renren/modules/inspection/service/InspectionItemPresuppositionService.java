package io.renren.modules.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.modules.inspection.entity.InspectionItemPresuppositionEntity;

import java.util.List;

public interface InspectionItemPresuppositionService extends IService<InspectionItemPresuppositionEntity> {

    /**
     * 保存扩展
     */
    void saveOrUpdate(Integer ItemId, List<InspectionItemPresuppositionEntity> extraList);

    /**
     * 根据巡检项Id，获取扩展Id列表
     */
    List<Integer> queryPresuppositionIdList(Integer itemId);

    /**
     * 根据扩展ID数组，批量删除
     */
    Integer deleteBatch(Integer[] presuppositionIds);

    InspectionItemPresuppositionEntity selectByGuid(String guid);

}
