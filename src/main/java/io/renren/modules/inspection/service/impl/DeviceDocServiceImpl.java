package io.renren.modules.inspection.service.impl;

import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.inspection.dao.DeviceDocDao;
import io.renren.modules.inspection.entity.DeviceDocEntity;
import io.renren.modules.inspection.service.DeviceDocService;


@Service("deviceDocService")
public class DeviceDocServiceImpl extends ServiceImpl<DeviceDocDao, DeviceDocEntity> implements DeviceDocService {

    @Autowired
    private SysUserService userService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String deviceId = (String)params.get("deviceId");
        String category = (String)params.get("category");
        Page<DeviceDocEntity> page = this.selectPage(
                new Query<DeviceDocEntity>(params).getPage(),
                new EntityWrapper<DeviceDocEntity>()
                .eq("device_id", deviceId)
                .eq("category", category)
                .orderBy("create_time", false)
        );

        for(DeviceDocEntity doc : page.getRecords()){
            SysUserEntity user = userService.selectById(doc.getUserId());
            doc.setUserName(user.getUsername());
        }

        return new PageUtils(page);
    }

    @Override
    public DeviceDocEntity selectByGuid(String guid){
        return this.baseMapper.selectByGuid(guid);
    }

    @Override
    public void deleteByGuid(String guid){
        this.baseMapper.deleteByGuid(guid);
    }

}
