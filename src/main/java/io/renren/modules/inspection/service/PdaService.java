package io.renren.modules.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.inspection.entity.PdaEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-01-21 03:10:49
 */
public interface PdaService extends IService<PdaEntity> {

    PageUtils queryPage(Map<String, Object> params);

    public PdaEntity selectByMac(String mac);

    /**
     * 获取未绑定到线路的pda列表
     */
    public List<PdaEntity> findPdaUnBind(String filterField, String key, Long lineId);
}

