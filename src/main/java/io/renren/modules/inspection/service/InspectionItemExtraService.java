package io.renren.modules.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.modules.inspection.entity.InspectionItemExtraEntity;

import java.util.List;

public interface InspectionItemExtraService extends IService<InspectionItemExtraEntity> {

    /**
     * 保存扩展
     */
    void saveOrUpdate(Integer ItemId, List<InspectionItemExtraEntity> extraList);

    /**
     * 根据巡检项Id，获取扩展Id列表
     */
    List<Integer> queryExtraIdList(Integer itemId);

    /**
     * 根据扩展ID数组，批量删除
     */
    Integer deleteBatch(Integer[] extraIds);

    public InspectionItemExtraEntity selectByName(String name);

    InspectionItemExtraEntity selectByGuid(String guid);
}
