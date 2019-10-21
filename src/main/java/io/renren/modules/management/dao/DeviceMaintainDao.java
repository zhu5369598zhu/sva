package io.renren.modules.management.dao;

import io.renren.modules.management.entity.DeviceMaintainEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 设备日常维护记录表
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-10-09 15:39:27
 */
@Mapper
public interface DeviceMaintainDao extends BaseMapper<DeviceMaintainEntity> {
	
}
