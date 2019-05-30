package io.renren.modules.inspection.service.impl;

import io.renren.common.utils.MapUtils;
import io.renren.modules.inspection.entity.ClassGroupEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.inspection.dao.ClassWorkerDao;
import io.renren.modules.inspection.entity.ClassWorkerEntity;
import io.renren.modules.inspection.service.ClassWorkerService;


@Service("classWorkerService")
public class ClassWorkerServiceImpl extends ServiceImpl<ClassWorkerDao, ClassWorkerEntity> implements ClassWorkerService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ClassWorkerEntity> page = this.selectPage(
                new Query<ClassWorkerEntity>(params).getPage(),
                new EntityWrapper<ClassWorkerEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveOrUpdate(Long classGroupId, List<Long> userIdList) {
        this.deleteByMap(new MapUtils().put("class_group_id", classGroupId));

        if(userIdList == null || userIdList.size() == 0){
            return;
        }

        List<ClassWorkerEntity> list = new ArrayList<>(userIdList.size());
        for(Long userId : userIdList){
            ClassWorkerEntity classWorkerEntity = new ClassWorkerEntity();
            classWorkerEntity.setClassGroupId(classGroupId);
            classWorkerEntity.setUserId(userId);

            list.add(classWorkerEntity);
        }

        this.insertBatch(list);
    }

    @Override
    public List<Long> queryUserIdList(Long classGroupId) { return baseMapper.queryUserIdList(classGroupId); }

    @Override
    public Long deleteBatch(Long[] userIds) { return baseMapper.deleteBatch(userIds); }
}
