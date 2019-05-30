package io.renren.modules.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.inspection.entity.LineZoneEntity;
import io.renren.modules.inspection.entity.ZoneEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-02-01 17:02:21
 */
public interface LineZoneService extends IService<LineZoneEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Integer insertZoneBatch(List<LineZoneEntity> lineZoneEntityList);

    /**
     * 获取未绑定巡线到的巡区列表
     */
    public List<ZoneEntity> findZoneUnBind(String filterField, String key, Long deptId, Long lineId);
}

