package io.renren.modules.management.dao;

import io.renren.modules.management.entity.OrderRecordEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 工单操作记录
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-05-27 15:40:37
 */
@Mapper
public interface OrderRecordDao extends BaseMapper<OrderRecordEntity> {
	
}
