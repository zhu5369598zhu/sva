package io.renren.modules.inspection.service.impl;

import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.service.SysDeptService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.inspection.dao.ZoneDao;
import io.renren.modules.inspection.entity.ZoneEntity;
import io.renren.modules.inspection.service.ZoneService;


@Service("zoneService")
public class ZoneServiceImpl extends ServiceImpl<ZoneDao, ZoneEntity> implements ZoneService {

    @Autowired
    private SysDeptService sysDeptService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String zoneName = (String)params.get("zoneName");
        String deptId = (String) params.get("deptId");

        Page<ZoneEntity> page = this.selectPage(
                new Query<ZoneEntity>(params).getPage(),
                new EntityWrapper<ZoneEntity>()
                        .eq( deptId != null , "dept_id", deptId)
                        .eq("is_delete",0)
                        .like(StringUtils.isNotBlank(zoneName),"zone_name", zoneName)
        );

        for(ZoneEntity zoneEntity : page.getRecords()){
            SysDeptEntity sysDeptEntity = sysDeptService.selectById(zoneEntity.getDeptId());
            if(sysDeptEntity !=null){
                zoneEntity.setDeptName(sysDeptEntity.getName());
            }
        }

        return new PageUtils(page);
    }

    @Override
    public List<ZoneEntity> all(Map<String, Object> params) {
        String zoneName = (String)params.get("zoneName");
        String deptId = (String) params.get("deptId");

        List<ZoneEntity> list = this.selectList(
                new EntityWrapper<ZoneEntity>()
                        .eq( deptId != null , "dept_id", deptId)
                        .eq("is_delete",0)
                        .like(StringUtils.isNotBlank(zoneName),"zone_name", zoneName)
        );

        for(ZoneEntity zoneEntity : list){
            SysDeptEntity sysDeptEntity = sysDeptService.selectById(zoneEntity.getDeptId());
            zoneEntity.setDeptName(sysDeptEntity.getName());
        }

        return list;
    }

    @Override
    public Integer isExist(String zoneName, String zoneCode){
        return baseMapper.isExist(zoneName, zoneCode);
    }

    @Override
    public ZoneEntity selectByName(String zoneName){
        return baseMapper.selectByName(zoneName);
    }

    @Override
    public ZoneEntity selectByZoneCode(String zoneCode){
        return baseMapper.selectByZoneCode(zoneCode);
    }
}
