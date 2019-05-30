package io.renren.modules.inspection.dao;

import io.renren.modules.inspection.entity.PdaEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-01-21 03:10:49
 */
@Mapper
public interface PdaDao extends BaseMapper<PdaEntity> {
    public PdaEntity selectByMac(String mac);

}
