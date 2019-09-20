package io.renren.modules.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.inspection.entity.InspectionTaskEntity;

import java.util.Date;
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
    List<Map<String,Object>> selectListByDate(String inspectionStartDate,String inspectionEndDate);
    PageUtils queryPage(Map<String, Object> params);
}

