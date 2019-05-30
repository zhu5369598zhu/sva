package io.renren.modules.inspection.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.renren.modules.inspection.entity.PeriodTurnEntity;
import io.renren.modules.inspection.entity.TurnClassGroupEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-01-29 17:34:03
 */
@Mapper
public interface PeriodTurnDao extends BaseMapper<PeriodTurnEntity> {
    /**
     * 根据周期ID，获取轮次ID列表
     */
    List<Long> queryTurnIdList(Long periodId);


    /**
     * 根据轮次ID数组，批量删除
     */
    Long deleteBatch(Long[] turnId);
}
