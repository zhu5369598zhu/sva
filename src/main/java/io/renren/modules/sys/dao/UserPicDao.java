package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.renren.modules.sys.entity.UserPicEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-10-17 16:00:30
 */
@Mapper
public interface UserPicDao extends BaseMapper<UserPicEntity> {

    void deleteByGuid(String guid);

    List<Map<String, Object>> selectByUserId(Integer userId);

    UserPicEntity selectByGuid(String uuid);
}
