package io.renren.modules.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.inspection.entity.InspectionLineEntity;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-01-29 17:34:03
 */
public interface InspectionLineService extends IService<InspectionLineEntity> {

    public PageUtils queryPage(Map<String, Object> params);

    public List<Map<String,Object>> selectByDept(Long deptId);

    public boolean updateBatchIds(Collection<? extends Serializable> Ids);

    public List<InspectionLineEntity> selectByMac(String mac);
}

