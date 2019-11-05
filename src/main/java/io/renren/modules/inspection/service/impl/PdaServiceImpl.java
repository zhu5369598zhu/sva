package io.renren.modules.inspection.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.inspection.dao.PdaDao;
import io.renren.modules.inspection.entity.InspectionLineEntity;
import io.renren.modules.inspection.entity.InspectionLinePublishEntity;
import io.renren.modules.inspection.entity.PdaEntity;
import io.renren.modules.inspection.service.InspectionLinePublishService;
import io.renren.modules.inspection.service.InspectionLineService;
import io.renren.modules.inspection.service.PdaService;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.service.SysDeptService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Service("pdaService")
public class PdaServiceImpl extends ServiceImpl<PdaDao, PdaEntity> implements PdaService {

    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private InspectionLinePublishService publishService;
    @Autowired
    InspectionLineService lineService;

    @Autowired
    private SysDeptService deptService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String pdaName = (String)params.get("pdaName");
        String deptId = (String) params.get("deptId");
        List<Integer> deptIds = null;
        if(deptId != null && !deptId.equals("")){
            deptIds = deptService.queryRecursiveChildByParentId(Long.parseLong(deptId));
        }

        Page<PdaEntity> page = this.selectPage(
                new Query<PdaEntity>(params).getPage(),
                new EntityWrapper<PdaEntity>()
                        .eq("is_delete", 0)
                        //.eq(deptId!=null,"dept_id",deptId)
                        .in(deptIds != null ,"dept_id",deptIds)
                        .like(StringUtils.isNotBlank(pdaName),"pda_name", pdaName)
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
                        .eq( deptId != null , "dept_id", deptId)
                        .eq("is_delete",0)
                        .like(StringUtils.isNotBlank(key),filterField, key)
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
