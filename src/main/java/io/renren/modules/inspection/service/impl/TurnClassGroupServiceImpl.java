package io.renren.modules.inspection.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.MapUtils;
import io.renren.modules.inspection.dao.TurnClassGroupDao;
import io.renren.modules.inspection.entity.TurnClassGroupEntity;
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
@Service("turnClassGroupService")
public class TurnClassGroupServiceImpl extends ServiceImpl<TurnClassGroupDao, TurnClassGroupEntity> implements TurnClassGroupService {

    @Override
    public void saveOrUpdate(Long turnId, List<Long> classGroupIdList) {
        this.deleteByMap(new MapUtils().put("turn_id", turnId));

        if(classGroupIdList == null || classGroupIdList.size() == 0){
            return;
        }

        List<TurnClassGroupEntity> list = new ArrayList<>(classGroupIdList.size());
        for(Long classGroupId : classGroupIdList){
            TurnClassGroupEntity turnClassGroupEntity = new TurnClassGroupEntity();
            turnClassGroupEntity.setTurnId(turnId);
            turnClassGroupEntity.setClassGroupId(classGroupId);

            list.add(turnClassGroupEntity);
        }
        this.insertBatch(list);
    }

    @Override
    public List<Long> queryClassGroupIdList(Long turnId) { return baseMapper.queryClassGroupIdList(turnId); }

    @Override
    public Long deleteBatch(Long[] classGroupIds) { return baseMapper.deleteBatch(classGroupIds); }
}
