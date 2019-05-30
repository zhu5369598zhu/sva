package io.renren.modules.inspection.service.impl;

import io.renren.modules.inspection.entity.InspectionLineEntity;
import io.renren.modules.inspection.entity.InspectionLinePublishEntity;
import io.renren.modules.inspection.service.InspectionLinePublishService;
import io.renren.modules.inspection.service.InspectionLineService;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.service.SysDeptService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.inspection.dao.PdaDao;
import io.renren.modules.inspection.entity.PdaEntity;
import io.renren.modules.inspection.service.PdaService;


@Service("pdaService")
public class PdaServiceImpl extends ServiceImpl<PdaDao, PdaEntity> implements PdaService {

    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private InspectionLinePublishService publishService;
    @Autowired
    InspectionLineService lineService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String pdaName = (String)params.get("pdaName");
        String deptId = (String) params.get("deptId");

        Page<PdaEntity> page = this.selectPage(
                new Query<PdaEntity>(params).getPage(),
                new EntityWrapper<PdaEntity>()
                        .like(StringUtils.isNotBlank(pdaName),"pda_name", pdaName)
                        .eq( deptId != null , "dept_id", deptId)
        );

        for(PdaEntity pdaEntity : page.getRecords()){
            SysDeptEntity sysDeptEntity = sysDeptService.selectById(pdaEntity.getDeptId());
            pdaEntity.setDeptName(sysDeptEntity.getName());
        }
        return new PageUtils(page);
    }

    @Override
    public PdaEntity selectByMac(String mac){
        return baseMapper.selectByMac(mac);
    }

    @Override
    public List<PdaEntity> findPdaUnBind(String filterField, String key, Long lineId){
        InspectionLineEntity line = lineService.selectById(lineId);
        Integer deptId = null;
        if (line != null) {
            deptId = line.getDeptId();
        }
        Integer isInspect = 1;
        List<PdaEntity> pdaList = this.selectList(
                new EntityWrapper<PdaEntity>()
                        .like(StringUtils.isNotBlank(key),filterField, key)
                        .eq( deptId != null , "dept_id", deptId)
        );

        HashMap<String, Object> publishParams = new HashMap<String, Object>();
        publishParams.put("line_id", lineId);
        List<InspectionLinePublishEntity> publishEntities = publishService.selectByMap(publishParams);

        Iterator<PdaEntity> item = pdaList.iterator();
        while (item.hasNext()){
            PdaEntity pda = item.next();
            for (InspectionLinePublishEntity publish : publishEntities) {
                if (pda.getPdaId().equals(publish.getPdaId())) {
                    item.remove();
                }
            }
        }

        return pdaList;
    }
}
