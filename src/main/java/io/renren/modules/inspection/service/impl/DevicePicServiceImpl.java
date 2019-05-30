package io.renren.modules.inspection.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.inspection.dao.DevicePicDao;
import io.renren.modules.inspection.entity.DevicePicEntity;
import io.renren.modules.inspection.service.DevicePicService;


@Service("devicePicService")
public class DevicePicServiceImpl extends ServiceImpl<DevicePicDao, DevicePicEntity> implements DevicePicService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DevicePicEntity> page = this.selectPage(
                new Query<DevicePicEntity>(params).getPage(),
                new EntityWrapper<DevicePicEntity>()
        );

        return new PageUtils(page);
    }
    @Override
    public DevicePicEntity selectByGuid(String guid){
        return baseMapper.selectByGuid(guid);
    }

    @Override
    public void deleteByGuid(String guid) {
        baseMapper.deleteByGuid(guid);
    }

    @Override
    public List<Map<String, Object>> selectByDeviceId (Integer deviceId){
        return baseMapper.selectByDeviceId(deviceId);
    }

}
