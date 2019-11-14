package io.renren.modules.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.inspection.entity.InspectionTaskDeviceEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-08-11 16:55:04
 */
public interface InspectionTaskDeviceService extends IService<InspectionTaskDeviceEntity> {
    PageUtils getMissingByDate(Map<String, Object> params);
    List<Map<String,Object>> getAllMissingByDate(Map<String, Object> params);
    Map<String,Object> getMissingStatisticsByDate(Map<String, Object> params);
    PageUtils getLineByDate(Map<String, Object> params);
    List<Map<String,Object>> getAllLineByDate(Map<String, Object> params);
    Map<String,Object> getLineStatisticsByDate(Map<String, Object> params);
    PageUtils getTurnByDate(Map<String, Object> params);
    List<Map<String,Object>> getAllTurnByDate(Map<String, Object> params);
    Map<String,Object> getTurnStatisticsByDate(Map<String, Object> params);
    PageUtils queryPage(Map<String, Object> params);
    Map<String,Object> selectByParams(Map<String, Object> params);
    PageUtils getDeviceDate(Map<String, Object> params);
}

