package io.renren.modules.inspection.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.inspection.dao.ClassGroupDao;
import io.renren.modules.inspection.entity.ClassGroupEntity;
import io.renren.modules.inspection.service.ClassGroupService;
import io.renren.modules.inspection.service.ClassWorkerService;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service("classGroupService")
public class ClassGroupServiceImpl extends ServiceImpl<ClassGroupDao, ClassGroupEntity> implements ClassGroupService {

    @Autowired
    private ClassWorkerService classWorkerService;
    @Autowired
    private SysUserService sysUserService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String inspectionLineId = (String)params.get("inspectionLineId");
        Page<ClassGroupEntity> page = this.selectPage(
                new Query<ClassGroupEntity>(params).getPage(),
                new EntityWrapper<ClassGroupEntity>()
                    .eq(inspectionLineId != null, "inspection_line_id", inspectionLineId)
        );

        for(ClassGroupEntity classGroupEntity: page.getRecords()){
            List<Long> workerList = classWorkerService.queryUserIdList(classGroupEntity.getId());
            List<String> workerNameList = new ArrayList<>(workerList.size());
            if (workerList != null){
                for(Long userId :workerList){
                   SysUserEntity user =  sysUserService.selectById(userId);
                    workerNameList.add(user.getUsername());
                }
                classGroupEntity.setWorkerList(workerList);
                classGroupEntity.setWorkerNameList(workerNameList);
                classGroupEntity.setWorkerNames(org.apache.commons.lang.StringUtils.join(workerNameList.toArray(), '/'));
            }
        }

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void save(ClassGroupEntity classGroupEntity){
        this.insert(classGroupEntity);
        classWorkerService.saveOrUpdate(classGroupEntity.getId(), classGroupEntity.getWorkerList());
    }

    @Override
    @Transactional
    public void update(ClassGroupEntity classGroupEntity){
        this.updateById(classGroupEntity);
        classWorkerService.saveOrUpdate(classGroupEntity.getId(), classGroupEntity.getWorkerList());
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] classGroupIds) {
        List<Long> classGroupIdList = Arrays.asList(classGroupIds);
        /*for(Long classGroupId :classGroupIdList){
            classWorkerService.deleteByMap(new MapUtils().put("class_group_id", classGroupId));
        }*/

        this.deleteBatchIds(classGroupIdList);
    }

    @Override
    public void insertClassGroup(ClassGroupEntity classGroupEntity) {
        baseMapper.insertClassGroup(classGroupEntity);
    }

}
