package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.UserPicDao;
import io.renren.modules.sys.entity.UserPicEntity;
import io.renren.modules.sys.service.UserPicService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("userPicService")
public class UserPicServiceImpl extends ServiceImpl<UserPicDao, UserPicEntity> implements UserPicService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<UserPicEntity> page = this.selectPage(
                new Query<UserPicEntity>(params).getPage(),
                new EntityWrapper<UserPicEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void deleteByGuid(String guid) {
        baseMapper.deleteByGuid(guid);
    }

    @Override
    public List<Map<String, Object>> selectByUserId(Integer userId) {
        return baseMapper.selectByUserId(userId);
    }

    @Override
    public UserPicEntity selectByGuid(String uuid) {
        return baseMapper.selectByGuid(uuid);
    }

}
