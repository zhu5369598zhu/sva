package io.renren.modules.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.modules.inspection.entity.PeriodTurnEntity;

import java.util.List;

public interface PeriodTurnService extends IService<PeriodTurnEntity> {
    /**
     * 保存
     */
    void saveOrUpdate(Long periodId, List<Long> turnIdList);

    /**
     * 根据周期ID，获取轮次ID列表
     */
    List<Long> queryTurnIdList(Long periodId);

    /**
     * 根据轮次ID数组，批量删除
     */
    Long deleteBatch(Long[] turnIDs);
}
