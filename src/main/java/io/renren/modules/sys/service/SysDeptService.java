package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysDeptEntity;
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
public interface SysDeptService extends IService<SysDeptEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据父部门，查询子部门
     * @param parentId 父部门ID
     */
    List<Map<String,Object>> queryListParentId(Long parentId);

    List<Integer> queryRecursiveChildByParentId(Long parentId);

    SysDeptEntity selectByName(String deptName);
}

