package io.renren.modules.inspection.dao;

import io.renren.modules.inspection.entity.ClassWorkerEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
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
public interface ClassWorkerDao extends BaseMapper<ClassWorkerEntity> {
    /**
     * 根据班组ID，获取用户ID列表
     */
    List<Long> queryUserIdList(Long classGroupId);


    /**
     * 根据用户ID数组，批量删除
     */
    Long deleteBatch(Long[] userIds);
	
}
