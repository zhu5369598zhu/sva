package io.renren.modules.inspection.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.inspection.dao.InspectionTaskDao;
import io.renren.modules.inspection.entity.ClassGroupEntity;
import io.renren.modules.inspection.entity.InspectionTaskEntity;
import io.renren.modules.inspection.entity.TurnClassGroupEntity;
import io.renren.modules.inspection.service.ClassGroupService;
import io.renren.modules.inspection.service.InspectionTaskService;
import io.renren.modules.inspection.service.TurnClassGroupService;
import io.renren.modules.sys.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service("inspectionTaskService")
public class InspectionTaskServiceImpl extends ServiceImpl<InspectionTaskDao, InspectionTaskEntity> implements InspectionTaskService {
    @Autowired
    SysDeptService deptService;
    @Autowired
    TurnClassGroupService turnClassGroupService;
    @Autowired
    ClassGroupService classGroupService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<InspectionTaskEntity> page = this.selectPage(
                new Query<InspectionTaskEntity>(params).getPage(),
                new EntityWrapper<InspectionTaskEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<Map<String,Object>> selectByDate(String inspectionDate){
        return baseMapper.selectByDate(inspectionDate);
    }

    @Override
    public PageUtils getAbsenceByDate(Map<String, Object> params){
        List<Integer> deptIds = null;
        String deptId = (String)params.get("deptId");
        String lineId = (String)params.get("lineId");
        String inspectStartTime = (String)params.get("startTime");
        String inspectEndTime = (String)params.get("endTime");

        if(deptId != null && !deptId.equals("")){
            deptIds = deptService.queryRecursiveChildByParentId(Long.parseLong(deptId));
        }
        Page<Map<String, Object>> page = new Query<Map<String, Object>>(params).getPage();
        List<Map<String, Object>> list = this.baseMapper.selectAbsenceDetailByDate(
                page,
                lineId,
                deptIds,
                inspectStartTime,
                inspectEndTime
        );
        for(Map<String, Object> item:list){
            List<String> classGroupList = new ArrayList<>();
            Long turnId = (Long)item.get("turnId");
            HashMap<String,Object> turnClassparams = new HashMap<>();
            turnClassparams.put("turn_id",turnId);
            List<TurnClassGroupEntity> turnClassGroupEntities = turnClassGroupService.selectByMap(turnClassparams);
            for(TurnClassGroupEntity turnClassGroupEntity:turnClassGroupEntities){
                ClassGroupEntity classGroupEntity = classGroupService.selectById(turnClassGroupEntity.getClassGroupId());
                if(classGroupEntity != null){
                    classGroupList.add(classGroupEntity.getName());
                }
            }
            item.put("workerList",org.apache.commons.lang.StringUtils.join(classGroupList.toArray(), '/'));
        }

        page.setRecords(list);
        return new PageUtils(page);
    }


    @Override
    public List<Map<String,Object>> getAllAbsenceByDate(Map<String, Object> params){
        List<Integer> deptIds = null;
        String deptId = (String)params.get("deptId");
        String lineId = (String)params.get("lineId");
        String inspectStartTime = (String)params.get("startTime");
        String inspectEndTime = (String)params.get("endTime");

        if(deptId != null && !deptId.equals("")){
            deptIds = deptService.queryRecursiveChildByParentId(Long.parseLong(deptId));
        }
        List<Map<String, Object>> list = this.baseMapper.selectAbsenceDetailByDate(
                lineId,
                deptIds,
                inspectStartTime,
                inspectEndTime
        );
        for(Map<String, Object> item:list){
            List<String> classGroupList = new ArrayList<>();
            Long turnId = (Long)item.get("turnId");
            HashMap<String,Object> turnClassparams = new HashMap<>();
            turnClassparams.put("turn_id",turnId);
            List<TurnClassGroupEntity> turnClassGroupEntities = turnClassGroupService.selectByMap(turnClassparams);
            for(TurnClassGroupEntity turnClassGroupEntity:turnClassGroupEntities){
                ClassGroupEntity classGroupEntity = classGroupService.selectById(turnClassGroupEntity.getClassGroupId());
                if(classGroupEntity != null){
                    classGroupList.add(classGroupEntity.getName());
                }
            }
            item.put("workerList",org.apache.commons.lang.StringUtils.join(classGroupList.toArray(), '/'));
        }

        return list;
    }

    @Override
    public Map<String,Object>  getAbsenceStatisticsByDate(Map<String, Object> params){
        List<Integer> deptIds = null;
        String deptId = (String)params.get("deptId");
        String lineId = (String)params.get("lineId");
        String inspectStartTime = (String)params.get("startTime");
        String inspectEndTime = (String)params.get("endTime");

        if(deptId != null && !deptId.equals("")){
            deptIds = deptService.queryRecursiveChildByParentId(Long.parseLong(deptId));
        }
        List<Map<String, Object>> list = this.baseMapper.selectAbsenceStatisticsByDate(
                lineId,
                deptIds,
                inspectStartTime,
                inspectEndTime
        );

        LinkedHashSet<String>  legend = new LinkedHashSet();
        LinkedHashSet<String>  category = new LinkedHashSet();
        LinkedHashSet<Long> series = new LinkedHashSet();

        HashMap<String, Object> json = new HashMap<>();
        Integer i = 0;
        for(Map<String, Object> item:list){
            legend.add((String)item.get("lineName"));
            category.add((String)item.get("lineName"));
            series.add((Long) item.get("count"));
        }

        json.put("legend", legend);
        json.put("category", category);
        json.put("series", series);

        return json;
    }
    @Override
    public PageUtils getLineByDate(Map<String, Object> params){
        List<Integer> deptIds = null;
        String deptId = (String)params.get("deptId");
        String lineId = (String)params.get("lineId");
        String inspectStartTime = (String)params.get("startTime");
        String inspectEndTime = (String)params.get("endTime");

        if(deptId != null && !deptId.equals("")){
            deptIds = deptService.queryRecursiveChildByParentId(Long.parseLong(deptId));
        }
        Page<Map<String, Object>> page = new Query<Map<String, Object>>(params).getPage();
        List<Map<String, Object>> list = this.baseMapper.selectAbsenceLineByDate(
                page,
                lineId,
                deptIds,
                inspectStartTime,
                inspectEndTime
        );
        for(Map<String, Object> item:list){
            List<String> classGroupList = new ArrayList<>();
            Long turnId = (Long)item.get("turnId");
            HashMap<String,Object> turnClassparams = new HashMap<>();
            turnClassparams.put("turn_id",turnId);
            List<TurnClassGroupEntity> turnClassGroupEntities = turnClassGroupService.selectByMap(turnClassparams);
            for(TurnClassGroupEntity turnClassGroupEntity:turnClassGroupEntities){
                ClassGroupEntity classGroupEntity = classGroupService.selectById(turnClassGroupEntity.getClassGroupId());
                if(classGroupEntity != null){
                    classGroupList.add(classGroupEntity.getName());
                }
            }
            item.put("workerList",org.apache.commons.lang.StringUtils.join(classGroupList.toArray(), '/'));
        }

        page.setRecords(list);
        return new PageUtils(page);
    }


    @Override
    public List<Map<String,Object>> getAllLineByDate(Map<String, Object> params){
        List<Integer> deptIds = null;
        String deptId = (String)params.get("deptId");
        String lineId = (String)params.get("lineId");
        String inspectStartTime = (String)params.get("startTime");
        String inspectEndTime = (String)params.get("endTime");

        if(deptId != null && !deptId.equals("")){
            deptIds = deptService.queryRecursiveChildByParentId(Long.parseLong(deptId));
        }
        List<Map<String, Object>> list = this.baseMapper.selectAbsenceLineByDate(
                lineId,
                deptIds,
                inspectStartTime,
                inspectEndTime
        );
        for(Map<String, Object> item:list){
            List<String> classGroupList = new ArrayList<>();
            Long turnId = (Long)item.get("turnId");
            HashMap<String,Object> turnClassparams = new HashMap<>();
            turnClassparams.put("turn_id",turnId);
            List<TurnClassGroupEntity> turnClassGroupEntities = turnClassGroupService.selectByMap(turnClassparams);
            for(TurnClassGroupEntity turnClassGroupEntity:turnClassGroupEntities){
                ClassGroupEntity classGroupEntity = classGroupService.selectById(turnClassGroupEntity.getClassGroupId());
                if(classGroupEntity != null){
                    classGroupList.add(classGroupEntity.getName());
                }
            }
            item.put("workerList",org.apache.commons.lang.StringUtils.join(classGroupList.toArray(), '/'));
        }

        return list;
    }

    @Override
    public Map<String,Object>  getLineStatisticsByDate(Map<String, Object> params){
        List<Integer> deptIds = null;
        String deptId = (String)params.get("deptId");
        String lineId = (String)params.get("lineId");
        String inspectStartTime = (String)params.get("startTime");
        String inspectEndTime = (String)params.get("endTime");

        if(deptId != null && !deptId.equals("")){
            deptIds = deptService.queryRecursiveChildByParentId(Long.parseLong(deptId));
        }
        List<Map<String, Object>> list = this.baseMapper.selectAbsenceLineByDate(
                lineId,
                deptIds,
                inspectStartTime,
                inspectEndTime
        );

        LinkedHashSet<String>  legend = new LinkedHashSet();
        LinkedHashSet<String>  category = new LinkedHashSet();
        LinkedHashSet series = new LinkedHashSet();

        HashMap<String, Object> inspectedHash = new HashMap();
        HashMap<String, Object> mustInspectHash = new HashMap();
        HashMap<String, Object> json = new HashMap<>();
        Long inspected = 0L;
        Long mustInspect = 0L;
        for(Map<String, Object> item:list){
            inspected += Long.parseLong(item.get("inspectedCount").toString());
            mustInspect += Long.parseLong(item.get("mustInspectCount").toString());
        }

        legend.add("已检");
        legend.add("漏检");
        inspectedHash.put("name","已检");
        inspectedHash.put("value",inspected);
        mustInspectHash.put("name","漏检");
        mustInspectHash.put("value",mustInspect);

        if(list.size() > 0){
            series.add(mustInspectHash);
            series.add(inspectedHash);
        }

        json.put("legend", legend);
        json.put("series", series);

        return json;
    }
    @Override
    public PageUtils getTurnByDate(Map<String, Object> params){
        List<Integer> deptIds = null;
        String deptId = (String)params.get("deptId");
        String lineId = (String)params.get("lineId");
        String inspectStartTime = (String)params.get("startTime");
        String inspectEndTime = (String)params.get("endTime");

        if(deptId != null && !deptId.equals("")){
            deptIds = deptService.queryRecursiveChildByParentId(Long.parseLong(deptId));
        }
        Page<Map<String, Object>> page = new Query<Map<String, Object>>(params).getPage();
        List<Map<String, Object>> list = this.baseMapper.selectAbsenceTurnByDate(
                page,
                lineId,
                deptIds,
                inspectStartTime,
                inspectEndTime
        );
        for(Map<String, Object> item:list){
            List<String> classGroupList = new ArrayList<>();
            Long turnId = (Long)item.get("turnId");
            HashMap<String,Object> turnClassparams = new HashMap<>();
            turnClassparams.put("turn_id",turnId);
            List<TurnClassGroupEntity> turnClassGroupEntities = turnClassGroupService.selectByMap(turnClassparams);
            for(TurnClassGroupEntity turnClassGroupEntity:turnClassGroupEntities){
                ClassGroupEntity classGroupEntity = classGroupService.selectById(turnClassGroupEntity.getClassGroupId());
                if(classGroupEntity != null){
                    classGroupList.add(classGroupEntity.getName());
                }
            }
            item.put("workerList",org.apache.commons.lang.StringUtils.join(classGroupList.toArray(), '/'));
        }

        page.setRecords(list);
        return new PageUtils(page);
    }


    @Override
    public List<Map<String,Object>> getAllTurnByDate(Map<String, Object> params){
        List<Integer> deptIds = null;
        String deptId = (String)params.get("deptId");
        String lineId = (String)params.get("lineId");
        String inspectStartTime = (String)params.get("startTime");
        String inspectEndTime = (String)params.get("endTime");

        if(deptId != null && !deptId.equals("")){
            deptIds = deptService.queryRecursiveChildByParentId(Long.parseLong(deptId));
        }
        List<Map<String, Object>> list = this.baseMapper.selectAbsenceTurnByDate(
                lineId,
                deptIds,
                inspectStartTime,
                inspectEndTime
        );
        for(Map<String, Object> item:list){
            List<String> classGroupList = new ArrayList<>();
            Long turnId = (Long)item.get("turnId");
            HashMap<String,Object> turnClassparams = new HashMap<>();
            turnClassparams.put("turn_id",turnId);
            List<TurnClassGroupEntity> turnClassGroupEntities = turnClassGroupService.selectByMap(turnClassparams);
            for(TurnClassGroupEntity turnClassGroupEntity:turnClassGroupEntities){
                ClassGroupEntity classGroupEntity = classGroupService.selectById(turnClassGroupEntity.getClassGroupId());
                if(classGroupEntity != null){
                    classGroupList.add(classGroupEntity.getName());
                }
            }
            item.put("workerList",org.apache.commons.lang.StringUtils.join(classGroupList.toArray(), '/'));
        }

        return list;
    }

    @Override
    public Map<String,Object>  getTurnStatisticsByDate(Map<String, Object> params){
        List<Integer> deptIds = null;
        String deptId = (String)params.get("deptId");
        String lineId = (String)params.get("lineId");
        String inspectStartTime = (String)params.get("startTime");
        String inspectEndTime = (String)params.get("endTime");

        if(deptId != null && !deptId.equals("")){
            deptIds = deptService.queryRecursiveChildByParentId(Long.parseLong(deptId));
        }
        List<Map<String, Object>> list = this.baseMapper.selectAbsenceTurnByDate(
                lineId,
                deptIds,
                inspectStartTime,
                inspectEndTime
        );

        LinkedHashSet<String>  legend = new LinkedHashSet();
        LinkedHashSet<String>  category = new LinkedHashSet();
        HashMap<String, Object> series = new HashMap();

        LinkedList<Long>  inspectedList = new LinkedList<>();
        LinkedList<Long> InspectList = new LinkedList();

        HashMap<String, Object> json = new HashMap<>();
        Integer i = 0;
        for(Map<String, Object> item:list){
            category.add((String)item.get("turnName"));
            inspectedList.add(Long.parseLong(item.get("inspectedCount").toString()));
            InspectList.add(Long.parseLong(item.get("inspectCount").toString()));
        }

        legend.add("已检");
        legend.add("应检");

        series.put("已检",inspectedList);
        series.put("应检",InspectList);

        json.put("legend", legend);
        json.put("category", category);
        json.put("series", series);

        return json;
    }

    @Override
    public PageUtils getLineByTime(Map<String, Object> params) {
        List<Integer> deptIds = null;
        String deptId = (String)params.get("deptId");
        String lineId = (String)params.get("lineId");
        String inspectStartTime = (String)params.get("startTime");
        String inspectEndTime = (String)params.get("endTime");

        if(deptId != null && !deptId.equals("")){
            deptIds = deptService.queryRecursiveChildByParentId(Long.parseLong(deptId));
        }
        Page<Map<String, Object>> page = new Query<Map<String, Object>>(params).getPage();
        List<Map<String, Object>> list = this.baseMapper.getLineByTime(
                page,
                lineId,
                deptIds,
                inspectStartTime,
                inspectEndTime
        );
        for(Map<String, Object> item:list){
            List<String> classGroupList = new ArrayList<>();
            Long turnId = (Long)item.get("turnId");
            HashMap<String,Object> turnClassparams = new HashMap<>();
            turnClassparams.put("turn_id",turnId);
            List<TurnClassGroupEntity> turnClassGroupEntities = turnClassGroupService.selectByMap(turnClassparams);
            for(TurnClassGroupEntity turnClassGroupEntity:turnClassGroupEntities){
                ClassGroupEntity classGroupEntity = classGroupService.selectById(turnClassGroupEntity.getClassGroupId());
                if(classGroupEntity != null){
                    classGroupList.add(classGroupEntity.getName());
                }
            }
            item.put("workerList",org.apache.commons.lang.StringUtils.join(classGroupList.toArray(), '/'));
        }
        page.setRecords(list);
        return new PageUtils(page);
    }
}
