package io.renren.modules.inspection.service.impl;

import io.renren.modules.inspection.entity.DeviceEntity;
import io.renren.modules.inspection.entity.ZoneEntity;
import io.renren.modules.inspection.service.DeviceService;
import io.renren.modules.inspection.service.ZoneService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.inspection.dao.ZoneDeviceDao;
import io.renren.modules.inspection.entity.ZoneDeviceEntity;
import io.renren.modules.inspection.service.ZoneDeviceService;


@Service("zoneDeviceService")
public class ZoneDeviceServiceImpl extends ServiceImpl<ZoneDeviceDao, ZoneDeviceEntity> implements ZoneDeviceService {

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private DeviceService deviceService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String zoneId = (String)params.get("zoneId");

        Page<ZoneDeviceEntity> page = this.selectPage(
                new Query<ZoneDeviceEntity>(params).getPage(),
                new EntityWrapper<ZoneDeviceEntity>()
                        .eq( zoneId != null , "zone_id", zoneId)
                        .orderBy("order_num")
        );

        for(ZoneDeviceEntity zoneDeviceEntity : page.getRecords()){
            ZoneEntity zoneEntity = zoneService.selectById(zoneDeviceEntity.getZoneId());
            zoneDeviceEntity.setZoneName(zoneEntity.getZoneName());
            DeviceEntity deviceEntity = deviceService.selectById(zoneDeviceEntity.getDeviceId());
            zoneDeviceEntity.setDeviceName(deviceEntity.getDeviceName());
            zoneDeviceEntity.setDeviceCode(deviceEntity.getDeviceCode());
        }

        return new PageUtils(page);
    }
    @Override
    public List<Map<String, Object>> findDeviceByzoneId(long zoneId) {
        return baseMapper.findDeviceByZoneId(zoneId);
    }

    @Override
    public Integer insertDeviceBatch(List<ZoneDeviceEntity> zoneDeviceEntityList){
        return baseMapper.insertDeviceBatch(zoneDeviceEntityList);
    }

}
