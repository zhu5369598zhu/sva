package io.renren.modules.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.inspection.entity.InspectionResultMediaEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-02-25 16:42:27
 */
public interface InspectionResultMediaService extends IService<InspectionResultMediaEntity> {

    PageUtils queryPage(Map<String, Object> params);
    InspectionResultMediaEntity selectByGuid(String guid);
    List<InspectionResultMediaEntity> selectListByResultId(@Param("result_id") Integer resultId);
}

