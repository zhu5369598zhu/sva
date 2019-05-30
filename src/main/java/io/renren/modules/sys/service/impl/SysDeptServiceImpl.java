package io.renren.modules.sys.service.impl;

import io.renren.modules.inspection.service.DeviceService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.SysDeptDao;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.service.SysDeptService;


@Service("sysDeptService")
public class SysDeptServiceImpl extends ServiceImpl<SysDeptDao, SysDeptEntity> implements SysDeptService {

    @Autowired
    private SysDeptService deptService;

    @Autowired
    private DeviceService deviceService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");

        Page<SysDeptEntity> page = this.selectPage(
                new Query<SysDeptEntity>(params).getPage(),
                new EntityWrapper<SysDeptEntity>()
                        .like(StringUtils.isNotBlank(name),"name", name)
        );

        return new PageUtils(page);
    }

    @Override
    public List<Map<String,Object>> queryListParentId(Long parentId) {
        return baseMapper.queryListParentId(parentId);
    }

    @Override
    public List<Integer> queryRecursiveChildByParentId(Long parentId){
        List<Integer> deptIds = new ArrayList<Integer>();
        deptIds.add(parentId.intValue());
        List<Map<String,Object>> depts = this.queryListParentId(parentId);
        for(Map<String,Object> dept:depts) {
            Long deptId = (Long)dept.get("id");
            deptIds.add(deptId.intValue());
            deptIds.addAll(queryRecursiveChildByParentId(deptId));
        }

        return deptIds;
    }

    @Override
    public SysDeptEntity selectByName(String deptName){
        return baseMapper.selectByName(deptName);
    }
}
