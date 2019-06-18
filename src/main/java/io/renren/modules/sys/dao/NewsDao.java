package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.NewsEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 消息表
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-05-10 08:42:48
 */
@Mapper
public interface NewsDao extends BaseMapper<NewsEntity> {
	
}
