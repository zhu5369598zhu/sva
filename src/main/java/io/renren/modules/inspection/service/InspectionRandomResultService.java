package io.renren.modules.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.inspection.entity.InspectionRandomResultEntity;
import io.renren.modules.inspection.entity.InspectionResultEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-03-15 11:43:31
 */
public interface InspectionRandomResultService extends IService<InspectionRandomResultEntity> {

    InspectionRandomResultEntity selectByGuid(String guid);

    InspectionRandomResultEntity selectByAppResultGuid(String appResultGuid);

    void deleteByAppResultGuid(String appResultGuid);

    PageUtils queryPage(Map<String, Object> params);

    String insertResult(InspectionRandomResultEntity result);

    PageUtils selectResultPage(Map<String, Object> params);

    List<InspectionRandomResultEntity> selectAll(Map<String, Object> params);
}

