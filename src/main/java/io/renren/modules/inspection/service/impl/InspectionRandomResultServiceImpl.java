package io.renren.modules.inspection.service.impl;

import io.renren.common.utils.MapUtils;
import io.renren.modules.inspection.entity.InspectionRandomResultMediaEntity;
import io.renren.modules.inspection.service.InspectionRandomResultMediaService;
import io.renren.modules.setting.entity.InspectionTypeEntity;
import io.renren.modules.setting.service.InspectionTypeService;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.inspection.dao.InspectionRandomResultDao;
import io.renren.modules.inspection.entity.InspectionRandomResultEntity;
import io.renren.modules.inspection.service.InspectionRandomResultService;


@Service("inspectionRandomResultService")
public class InspectionRandomResultServiceImpl extends ServiceImpl<InspectionRandomResultDao, InspectionRandomResultEntity> implements InspectionRandomResultService {

    @Autowired
    private InspectionTypeService inspectionTypeService;
    @Autowired
    private SysUserService userService;
    @Autowired
    private InspectionRandomResultMediaService mediaService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<InspectionRandomResultEntity> page = this.selectPage(
                new Query<InspectionRandomResultEntity>(params).getPage(),
                new EntityWrapper<InspectionRandomResultEntity>()
        );

        List<InspectionRandomResultEntity> inspectionRandomResultEntities = page.getRecords();

        for(InspectionRandomResultEntity result:inspectionRandomResultEntities){
            InspectionTypeEntity inspectionTypeEntity = inspectionTypeService.selectById(result.getInspectionTypeId());
            result.setInspectionType(inspectionTypeEntity.getName());
            result.setUnit(inspectionTypeEntity.getUnit());

            SysUserEntity user = userService.selectById(result.getUserId());
            result.setUsername(user.getUsername());
        }

        return new PageUtils(page);
    }

    @Override
    public List<InspectionRandomResultEntity> selectAll(Map<String, Object> params) {
        String factory = (String)params.get("factory");
        String dept = (String)params.get("dept");
        String workshop = (String)params.get("workshop");
        String deviceName = (String)params.get("deviceName");
        String deviceCode = (String)params.get("deviceCode");
        String name = (String)params.get("name");
        String username = (String)params.get("username");
        String inspectionTypeId = (String)params.get("inspectionTypeId");
        String startTime = (String)params.get("startTime");
        String endTime = (String)params.get("endTime");

        InspectionRandomResultEntity resultEntity = new InspectionRandomResultEntity();
        if(factory != null){
            resultEntity.setFactory(factory);
        }
        if (dept != null){
            resultEntity.setDept(dept);
        }
        if(workshop != null){
            resultEntity.setWorkshop(workshop);
        }
        if(deviceName != null){
            resultEntity.setDeviceName(deviceName);
        }
        if(deviceCode !=null ){
            resultEntity.setDeviceCode(deviceCode);
        }
        if(name != null){
            resultEntity.setName(name);
        }
        if(username != null){
            resultEntity.setUsername(username);
        }
        if(inspectionTypeId != null && !inspectionTypeId.equals("")) {
            resultEntity.setInspectionTypeId(Integer.parseInt(inspectionTypeId));
        }
        if(startTime != null && !startTime.equals("")){
            resultEntity.setStartTime(startTime);
        }
        if(endTime != null && !endTime.equals("")){
            resultEntity.setEndTime(endTime);
        }

        List<InspectionRandomResultEntity> resultList = this.baseMapper.selectResultList(resultEntity);

        for(InspectionRandomResultEntity result: resultList){
            result.setDeviceName(result.getDeviceName() + "[" + result.getDeviceCode() + "]");
        }
        return resultList;
    }

    @Override
    public PageUtils selectResultPage(Map<String, Object> params) {
        String factory = (String)params.get("factory");
        String dept = (String)params.get("dept");
        String workshop = (String)params.get("workshop");
        String deviceName = (String)params.get("deviceName");
        String deviceCode = (String)params.get("deviceCode");
        String name = (String)params.get("name");
        String username = (String)params.get("username");
        String inspectionTypeId = (String)params.get("inspectionTypeId");
        String startTime = (String)params.get("startTime");
        String endTime = (String)params.get("endTime");

        InspectionRandomResultEntity resultEntity = new InspectionRandomResultEntity();
        if(factory != null){
            resultEntity.setFactory(factory);
        }
        if (dept != null){
            resultEntity.setDept(dept);
        }
        if(workshop != null){
            resultEntity.setWorkshop(workshop);
        }
        if(deviceName != null){
            resultEntity.setDeviceName(deviceName);
        }
        if(deviceCode !=null ){
            resultEntity.setDeviceCode(deviceCode);
        }
        if(name != null){
            resultEntity.setName(name);
        }
        if(username != null){
            resultEntity.setUsername(username);
        }
        if(inspectionTypeId != null && !inspectionTypeId.equals("")) {
            resultEntity.setInspectionTypeId(Integer.parseInt(inspectionTypeId));
        }
        if(startTime != null && !startTime.equals("")){
            resultEntity.setStartTime(startTime);
        }
        if(endTime != null && !endTime.equals("")){
            resultEntity.setEndTime(endTime);
        }

        Page<InspectionRandomResultEntity> page = new Query<InspectionRandomResultEntity>(params).getPage();
        List<InspectionRandomResultEntity> list = this.baseMapper.selectResultList(
                page,
                resultEntity
        );

        for(InspectionRandomResultEntity result: list){
            List<InspectionRandomResultMediaEntity> mediaEntityList = mediaService.selectListByResultId(result.getId());
            result.setMedias(mediaEntityList);
            result.setDeviceName(result.getDeviceName() + "[" + result.getDeviceCode() + "]");
        }
        page.setRecords(list);
        return new PageUtils(page);
    }

    @Override
    public InspectionRandomResultEntity selectByGuid(String guid){
        return baseMapper.selectByGuid(guid);
    }

    @Override
    public InspectionRandomResultEntity selectByAppResultGuid(String appResultGuid){
        return baseMapper.selectByAppResultGuid(appResultGuid);
    }

    @Override
    public void deleteByAppResultGuid(String appResultGuid) {
        InspectionRandomResultEntity result = this.selectByAppResultGuid(appResultGuid);
        if(result != null){
            mediaService.deleteByMap(new MapUtils().put("result_id", result.getId()));
        }
        baseMapper.deleteByAppResultGuid(appResultGuid);
    }

    @Override
    public String insertResult(InspectionRandomResultEntity result){
        SysUserEntity user = userService.selectByGuid(result.getUserGuid());
        if(user != null){
            result.setUserId(user.getUserId().intValue());
        } else {
            return "";
        }

        InspectionTypeEntity type = inspectionTypeService.selectById(result.getInspectionTypeId());
        if(type == null){
            return "";
        }

        result.setGuid(UUID.randomUUID().toString());
        result.setCreateTime(new java.util.Date());

        Integer id = this.baseMapper.insert(result);
        if(id > 0){
            return result.getGuid();
        }

        return "";
    }

}
