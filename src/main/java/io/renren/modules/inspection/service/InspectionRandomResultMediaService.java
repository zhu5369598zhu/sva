package io.renren.modules.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.inspection.entity.InspectionRandomResultMediaEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-03-15 11:43:31
 */
public interface InspectionRandomResultMediaService extends IService<InspectionRandomResultMediaEntity> {

    PageUtils queryPage(Map<String, Object> params);
    InspectionRandomResultMediaEntity selectByGuid(String guid);
    List<InspectionRandomResultMediaEntity> selectListByResultId(@Param("result_id") Integer resultId);
}

