package io.renren.modules.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.inspection.entity.InspectionItemEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-01-22 17:33:37
 */
public interface InspectionItemService extends IService<InspectionItemEntity> {

    InspectionItemEntity selectByGuid(String guid);

    List<Map<String,Object>> selectByDevice(Long deviceId, Integer inspectionType);

    PageUtils queryPage(Map<String, Object> params);

    List<InspectionItemEntity> all(Map<String, Object> params);

    public void save(InspectionItemEntity inspectionItemEntity);

    public void update(InspectionItemEntity inspectionItemEntity);

    public void deleteBatch(Long[] itemIds);

    public boolean updateBatchIds(Collection<? extends Serializable> Ids);

    public Integer isExist(String itemName, String deviceCode);
}

