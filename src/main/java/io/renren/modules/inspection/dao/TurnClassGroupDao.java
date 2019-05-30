package io.renren.modules.inspection.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
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
public interface TurnClassGroupDao extends BaseMapper<TurnClassGroupEntity> {
    /**
     * 根据轮次ID，获取班组ID列表
     */
    List<Long> queryClassGroupIdList(Long turnId);


    /**
     * 根据班组ID数组，批量删除
     */
    Long deleteBatch(Long[] classGroupIds);
}
