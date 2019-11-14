package io.renren.modules.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.inspection.entity.InspectionTaskEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-08-11 16:55:04
 */
public interface InspectionTaskService extends IService<InspectionTaskEntity> {
    List<Map<String,Object>> selectByDate(String inspectionDate);
    PageUtils getAbsenceByDate(Map<String, Object> params);
    List<Map<String,Object>> getAllAbsenceByDate(Map<String, Object> params);
    Map<String,Object> getAbsenceStatisticsByDate(Map<String, Object> params);
    PageUtils getLineByDate(Map<String, Object> params);
    List<Map<String,Object>> getAllLineByDate(Map<String, Object> params);
    Map<String,Object> getLineStatisticsByDate(Map<String, Object> params);
    PageUtils getTurnByDate(Map<String, Object> params);
    List<Map<String,Object>> getAllTurnByDate(Map<String, Object> params);
    Map<String,Object> getTurnStatisticsByDate(Map<String, Object> params);
    PageUtils queryPage(Map<String, Object> params);
    PageUtils getLineByTime(Map<String, Object> params);
}

