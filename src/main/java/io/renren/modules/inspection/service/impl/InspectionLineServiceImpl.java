package io.renren.modules.inspection.service.impl;

import io.renren.modules.inspection.entity.InspectionLinePublishEntity;
import io.renren.modules.inspection.service.InspectionLinePublishService;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.service.SysDeptService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.inspection.dao.InspectionLineDao;
import io.renren.modules.inspection.entity.InspectionLineEntity;
import io.renren.modules.inspection.service.InspectionLineService;


@Service("inspectionLineService")
public class InspectionLineServiceImpl extends ServiceImpl<InspectionLineDao, InspectionLineEntity> implements InspectionLineService {

    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private InspectionLinePublishService publishService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String deptId = (String)params.get("deptId");
        String name = (String)params.get("name");
        Page<InspectionLineEntity> page = this.selectPage(
                new Query<InspectionLineEntity>(params).getPage(),
                new EntityWrapper<InspectionLineEntity>()
                        .eq(deptId!=null,"dept_id",deptId)
                        .like(StringUtils.isNotBlank(name),"name",name)

        );

        for(InspectionLineEntity inspectionLineEntity : page.getRecords()){
            if(inspectionLineEntity.getPeriodType().equals(1)){
                inspectionLineEntity.setPeriodTypeName("天");
            } else if (inspectionLineEntity.getPeriodType().equals(2)){
                inspectionLineEntity.setPeriodTypeName("周");
            } else if (inspectionLineEntity.getPeriodType().equals(3)){
                inspectionLineEntity.setPeriodTypeName("月");
            }

            if(inspectionLineEntity.getType().equals(0)){
                inspectionLineEntity.setTypeName("扫码");
            } else if (inspectionLineEntity.getType().equals(1)){
                inspectionLineEntity.setTypeName("RFID");
            }

            SysDeptEntity sysDeptEntity = sysDeptService.selectById(inspectionLineEntity.getDeptId());
            inspectionLineEntity.setDeptName(sysDeptEntity.getName());

            Integer publishCount = publishService.selectCount(
                    new EntityWrapper<InspectionLinePublishEntity>()
                    .eq("line_id", inspectionLineEntity.getId())

            );
            Integer downloadCount = publishService.selectCount(
                    new EntityWrapper<InspectionLinePublishEntity>()
                            .eq("line_id", inspectionLineEntity.getId())
                            .eq("is_download", 1)

            );

            inspectionLineEntity.setDownloadStatus(downloadCount.toString() + "/" + publishCount.toString());
        }
        return new PageUtils(page);
    }

    @Override
    public boolean updateBatchIds(Collection<? extends Serializable> Ids){
        List<InspectionLineEntity> lineEntityList = this.selectBatchIds(Ids);

        return this.updateBatchById(lineEntityList);
    }

    @Override
    public List<Map<String,Object>> selectByDept(Long deptId){
        return baseMapper.selectByDept(deptId);
    }

    @Override
    public List<InspectionLineEntity> selectByMac(String mac){
        return baseMapper.selectByMac(mac);
    }

}
