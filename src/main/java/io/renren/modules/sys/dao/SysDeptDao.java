package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.SysDeptEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 部门管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-01-17 17:04:27
 */
@Mapper
public interface SysDeptDao extends BaseMapper<SysDeptEntity> {
    /**
     * 根据父部门，查询子部门
     * @param parentId 父部门ID
     */
    List<Map<String,Object>> queryListParentId(Long parentId);

    SysDeptEntity selectByName(@Param("deptName") String deptName);
}
