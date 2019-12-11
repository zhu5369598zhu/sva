package io.renren.modules.inspection.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.inspection.dao.InspectionTaskDeviceDao;
import io.renren.modules.inspection.entity.ClassGroupEntity;
import io.renren.modules.inspection.entity.InspectionTaskDeviceEntity;
import io.renren.modules.inspection.entity.TurnClassGroupEntity;
import io.renren.modules.inspection.service.ClassGroupService;
import io.renren.modules.inspection.service.InspectionTaskDeviceService;
import io.renren.modules.inspection.service.TurnClassGroupService;
import io.renren.modules.sys.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service("inspectionTaskDeviceService")
public class InspectionTaskDeviceServiceImpl extends ServiceImpl<InspectionTaskDeviceDao, InspectionTaskDeviceEntity> implements InspectionTaskDeviceService {
    @Autowired
    SysDeptService deptService;
    @Autowired
    TurnClassGroupService turnClassGroupService;
    @Autowired
    ClassGroupService classGroupService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<InspectionTaskDeviceEntity> page = this.selectPage(
                new Query<InspectionTaskDeviceEntity>(params).getPage(),
                new EntityWrapper<InspectionTaskDeviceEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public Map<String, Object> selectByParams(Map<String, Object> params) {

        //String lineId = (String)((Number)params.get("line_id")).intValue();
        String lineId = params.get("line_id").toString();
        String turnId = params.get("turn_id").toString();
        String turnStartTime = params.get("turn_start_time").toString();
        String turnEndTime = params.get("turn_end_time").toString();
        return this.baseMapper.selectByParams(lineId, turnId, turnStartTime, turnEndTime);
    }

    @Override
    public PageUtils getMissingByDate(Map<String, Object> params){
        List<Integer> deptIds = null;
        String deptId = (String)params.get("deptId");
        String lineId = (String)params.get("lineId");
        String inspectStartTime = (String)params.get("startTime");
        String inspectEndTime = (String)params.get("endTime");

        if(deptId != null && !deptId.equals("")){
            deptIds = deptService.queryRecursiveChildByParentId(Long.parseLong(deptId));
        }
        Page<Map<String, Object>> page = new Query<Map<String, Object>>(params).getPage();
        List<Map<String, Object>> list = this.baseMapper.selectMissingDetailByDate(
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
    public List<Map<String,Object>> getAllMissingByDate(Map<String, Object> params){
        List<Integer> deptIds = null;
        String deptId = (String)params.get("deptId");
        String lineId = (String)params.get("lineId");
        String inspectStartTime = (String)params.get("startTime");
        String inspectEndTime = (String)params.get("endTime");

        if(deptId != null && !deptId.equals("")){
            deptIds = deptService.queryRecursiveChildByParentId(Long.parseLong(deptId));
        }
        List<Map<String, Object>> list = this.baseMapper.selectMissingDetailByDate(
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
    public Map<String,Object>  getMissingStatisticsByDate(Map<String, Object> params){
        List<Integer> deptIds = null;
        String deptId = (String)params.get("deptId");
        String lineId = (String)params.get("lineId");
        String inspectStartTime = (String)params.get("startTime");
        String inspectEndTime = (String)params.get("endTime");

        if(deptId != null && !deptId.equals("")){
            deptIds = deptService.queryRecursiveChildByParentId(Long.parseLong(deptId));
        }
        List<Map<String, Object>> list = this.baseMapper.selectMissingStatisticsByDate(
                lineId,
                deptIds,
                inspectStartTime,
                inspectEndTime
        );

        LinkedHashSet<String> legend = new LinkedHashSet();
        LinkedHashSet<String>  category = new LinkedHashSet();
        LinkedList<Long> series = new LinkedList();

        HashMap<String, Object> json = new HashMap<>();
        Integer i = 0;
        for(Map<String, Object> item:list){
            legend.add((String)item.get("deviceName"));
            category.add((String)item.get("deviceName"));
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
        List<Map<String, Object>> list = this.baseMapper.selectMissingLineByDate(
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
        List<Map<String, Object>> list = this.baseMapper.selectMissingLineByDate(
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
        List<Map<String, Object>> list = this.baseMapper.selectMissingLineByDate(
                lineId,
                deptIds,
                inspectStartTime,
                inspectEndTime
        );

        LinkedHashSet<String>  legend = new LinkedHashSet();
        LinkedHashSet<String>  category = new LinkedHashSet();
        LinkedList series = new LinkedList();

        HashMap<String, Object> inspectedHash = new HashMap();
        HashMap<String, Object> missInspectHash = new HashMap();
        HashMap<String, Object> json = new HashMap<>();
        Long inspected = 0L;
        Long missInspect = 0L;
        for(Map<String, Object> item:list){
            inspected += Long.parseLong(item.get("inspectedItemCount").toString());
            missInspect += Long.parseLong(item.get("inspectMissItemCount").toString());
        }

        legend.add("已检");
        legend.add("漏检");
        inspectedHash.put("name","已检");
        inspectedHash.put("value",inspected);
        missInspectHash.put("name","漏检");
        missInspectHash.put("value",missInspect);

        if(list.size() > 0){
            series.add(missInspectHash);
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
        List<Map<String, Object>> list = this.baseMapper.selectMissingTurnByDate(
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
        List<Map<String, Object>> list = this.baseMapper.selectMissingTurnByDate(
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
        List<Map<String, Object>> list = this.baseMapper.selectMissingTurnByDate(
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
        LinkedList<Long> InspectMissList = new LinkedList();

        HashMap<String, Object> json = new HashMap<>();
        Integer i = 0;
        for(Map<String, Object> item:list){
            category.add((String)item.get("turnName"));
            inspectedList.add(Long.parseLong(item.get("inspectedItemCount").toString()));
            InspectList.add(Long.parseLong(item.get("inspectItemCount").toString()));
            InspectMissList.add(Long.parseLong(item.get("inspectMissItemCount").toString()));
        }

        legend.add("已检");
        legend.add("漏检");
        legend.add("应检");

        series.put("已检",inspectedList);
        series.put("漏检",InspectMissList);
        series.put("应检",InspectList);

        json.put("legend", legend);
        json.put("category", category);
        json.put("series", series);

        return json;
    }

    @Override
    public PageUtils getDeviceDate(Map<String, Object> params) {
        List<Integer> deptIds = null;
        String deptId = (String)params.get("deptId");
        String lineId = (String)params.get("lineId");
        String inspectStartTime = (String)params.get("startTime");
        String inspectEndTime = (String)params.get("endTime");
        inspectEndTime = inspectEndTime + " 23:59:59";

        if(deptId != null && !deptId.equals("")){
            deptIds = deptService.queryRecursiveChildByParentId(Long.parseLong(deptId));
        }
        Page<Map<String, Object>> page = new Query<Map<String, Object>>(params).getPage();
        List<Map<String, Object>> list = this.baseMapper.getDeviceByTime(
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
    public List<Map<String, Object>> getAllDeviceDate(Map<String, Object> params) {
        List<Integer> deptIds = null;
        String deptId = (String)params.get("deptId");
        String lineId = (String)params.get("lineId");
        String inspectStartTime = (String)params.get("startTime");
        String inspectEndTime = (String)params.get("endTime");
        inspectEndTime = inspectEndTime + " 23:59:59";

        if(deptId != null && !deptId.equals("")){
            deptIds = deptService.queryRecursiveChildByParentId(Long.parseLong(deptId));
        }
        Page<Map<String, Object>> page = new Query<Map<String, Object>>(params).getPage();
        List<Map<String, Object>> list = this.baseMapper.getAllDeviceByTime(
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
}
