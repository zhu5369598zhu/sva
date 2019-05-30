package io.renren.modules.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.inspection.entity.InspectionLinePublishEntity;
import io.renren.modules.inspection.entity.PdaEntity;
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
public interface InspectionLinePublishService extends IService<InspectionLinePublishEntity> {

    PageUtils queryPage(Map<String, Object> params);

    public InspectionLinePublishEntity selectByParams(Integer lineId, Integer pdaId);

    public Integer insertPublishBatch(List<InspectionLinePublishEntity> publishList);

    public Integer updateDownload(Integer lineId, Integer isDownload);
}

