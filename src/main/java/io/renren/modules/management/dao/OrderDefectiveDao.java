package io.renren.modules.management.dao;

import io.renren.modules.management.entity.OrderDefectiveEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 缺陷工单表
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-05-31 15:16:09
 */
@Mapper
public interface OrderDefectiveDao extends BaseMapper<OrderDefectiveEntity> {

	List<OrderDefectiveEntity> selectOrderDefective(HashMap<String, Object> entityMap);

	
}
 