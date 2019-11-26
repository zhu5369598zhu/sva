package io.renren.modules.inspection.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.inspection.dao.LineZoneDao;
import io.renren.modules.inspection.entity.LineZoneEntity;
import io.renren.modules.inspection.entity.ZoneEntity;
import io.renren.modules.inspection.service.LineZoneService;
import io.renren.modules.inspection.service.ZoneService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Service("lineZoneService")
public class LineZoneServiceImpl extends ServiceImpl<LineZoneDao, LineZoneEntity> implements LineZoneService {

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private LineZoneService lineZoneService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String lineId = (String)params.get("lineId");
        Page<LineZoneEntity> page = this.selectPage(
                new Query<LineZoneEntity>(params).getPage(),
                new EntityWrapper<LineZoneEntity>()
                    .eq(lineId != null, "line_id", lineId)
        );

        for(LineZoneEntity lineZoneEntity :page.getRecords()){
            ZoneEntity zoneEntity = zoneService.selectById(lineZoneEntity.getZoneId());
            lineZoneEntity.setZoneName(zoneEntity.getZoneName());
            lineZoneEntity.setZoneCode(zoneEntity.getZoneCode());
        }

        return new PageUtils(page);
    }

    @Override
    public Integer insertZoneBatch(List<LineZoneEntity> lineZoneEntityList){
        return baseMapper.insertZoneBatch(lineZoneEntityList);
    }


    /**
     * 获取未绑定巡线到的巡区列表
     */
    @Override
    public List<ZoneEntity> findZoneUnBind(String filterField, String key, Long dept_id, Long lineId){
        List<ZoneEntity> zoneEntityList = zoneService.selectList(
                new EntityWrapper<ZoneEntity>()
                    .like(StringUtils.isNotBlank(key), filterField, key)
                    .eq(dept_id != null, "dept_id", dept_id)
                    .eq("is_delete", 0)
        );

        List<LineZoneEntity> lineZoneEntityList = lineZoneService.selectList(
                new EntityWrapper<LineZoneEntity>()
                    .eq(lineId != null,"line_id", lineId)
        );

        Iterator<ZoneEntity> item = zoneEntityList.iterator();
        while(item.hasNext()){
            ZoneEntity zoneEntity = item.next();
            for(LineZoneEntity lineZoneEntity :lineZoneEntityList){
                if(zoneEntity.getZoneId().equals(lineZoneEntity.getZoneId())){
                    item.remove();
                }
            }
        }

        return zoneEntityList;
    }

}
