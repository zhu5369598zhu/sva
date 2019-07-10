package io.renren.modules.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.inspection.entity.InspectionResultEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-02-13 11:25:57
 */
public interface InspectionResultService extends IService<InspectionResultEntity> {

    InspectionResultEntity selectByGuid(String guid);

    InspectionResultEntity selectByAppResultGuid(String appResultGuid);

    void deleteByAppResultGuid(String appResultGuid);

    PageUtils queryPage(Map<String, Object> params);

    String insertResult(InspectionResultEntity result);

    PageUtils selectResultPage(Map<String, Object> params);

    List<InspectionResultEntity> selectAll(Map<String, Object> params);

    public Map<String,Object> selectException(Map<String, Object> params);

    Map<String,Object> selectByItem(Map<String, Object> params);

    Map<String,Object> selectExceptionGroupByDevice(Map<String, Object> params);

    Map<String,Object> selectExceptionGroupByTime(Map<String, Object> params);

    Map<String,Object> selectExceptionGroupByItem(Map<String, Object> params);

}

