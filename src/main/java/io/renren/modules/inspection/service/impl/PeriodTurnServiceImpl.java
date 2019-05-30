package io.renren.modules.inspection.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.MapUtils;
import io.renren.modules.inspection.dao.PeriodTurnDao;
import io.renren.modules.inspection.dao.TurnClassGroupDao;
import io.renren.modules.inspection.entity.PeriodTurnEntity;
import io.renren.modules.inspection.entity.TurnClassGroupEntity;
import io.renren.modules.inspection.service.PeriodTurnService;
import io.renren.modules.inspection.service.TurnClassGroupService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-01-29 17:34:03
 */
@Service("periodTurnService")
public class PeriodTurnServiceImpl extends ServiceImpl<PeriodTurnDao, PeriodTurnEntity> implements PeriodTurnService {

    @Override
    public void saveOrUpdate(Long periodId, List<Long> turnIdList) {
        this.deleteByMap(new MapUtils().put("period_id", periodId));

        if(turnIdList == null || turnIdList.size() == 0){
            return;
        }

        List<PeriodTurnEntity> list = new ArrayList<>(turnIdList.size());
        for(Long turnId : turnIdList){
            PeriodTurnEntity periodTurnEntity = new PeriodTurnEntity();
            periodTurnEntity.setPeriodId(periodId);
            periodTurnEntity.setTurnId(turnId);

            list.add(periodTurnEntity);
        }

        this.insertBatch(list);
    }

    @Override
    public List<Long> queryTurnIdList(Long periodId) { return baseMapper.queryTurnIdList(periodId); }

    @Override
    public Long deleteBatch(Long[] turnIds) { return baseMapper.deleteBatch(turnIds); }
}
