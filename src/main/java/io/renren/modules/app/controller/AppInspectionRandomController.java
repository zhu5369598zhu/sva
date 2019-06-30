package io.renren.modules.app.controller;

import io.renren.common.annotation.SysLog;
import io.renren.common.exception.RRException;
import io.renren.common.utils.R;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.app.annotation.LoginUser;
import io.renren.modules.app.service.InspectionService;
import io.renren.modules.inspection.entity.*;
import io.renren.modules.inspection.service.*;
import io.renren.modules.setting.entity.InspectionTypeEntity;
import io.renren.modules.setting.service.InspectionTypeService;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;


/**
 * 巡检相关接口
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-11-17
 */
@RestController
@RequestMapping("/app/inspection/random/")
@Api("APP临时巡检相关接口")
public class AppInspectionRandomController {
    @Autowired
    private InspectionService inspectionService;
    @Autowired
    private InspectionRandomResultService resultService;
    @Autowired
    private InspectionRandomResultMediaService mediaService;
    @Autowired
    private InspectionTypeService typeService;
    @Autowired
    private SysUserService userService;


    @Login
    @PostMapping("save")
    @ApiOperation("上传巡检结果")
    public R save (@RequestBody InspectionRandomResultEntity inspectionResult){
        String appResultGuid = (String)inspectionResult.getAppResultGuid();
        if(appResultGuid == null) {
            return R.error(400, "appResultGuid不能为空");
        }
        String factory = (String)inspectionResult.getFactory();
        if(factory == null) {
            return R.error(400, "工厂名称不能为空");
        }
        String dept = (String)inspectionResult.getDept();
        if(dept == null) {
            return R.error(400, "部门不能为空");
        }
        String workshop = (String)inspectionResult.getWorkshop();
        if(workshop == null) {
            return R.error(400, "车间不能为空");
        }
        String deviceName = (String)inspectionResult.getDeviceName();
        if(deviceName == null) {
            return R.error(400, "设备名称不能为空");
        }
        String deviceCode = (String)inspectionResult.getDeviceCode();
        if(deviceCode == null) {
            return R.error(400, "设备编码不能为空");
        }
        String result = inspectionResult.getResult();
        if(result == null) {
            return R.error(400, "巡检结果不能为空");
        }
        if(inspectionResult.getStartTime() == null){
            return R.error(400, "startTime不能为空");
        }
        if(inspectionResult.getEndTime() == null){
            return R.error(400, "endTime不能为空");
        }

        Integer inspectionTypeId = inspectionResult.getInspectionTypeId();
        if(inspectionTypeId == null) {
            return R.error(400, "巡检类型不能为空");
        }
        InspectionTypeEntity type = typeService.selectById(inspectionTypeId);
        if(type == null){
            return R.error(400, "巡检类型没有找到");
        }
        String userGuid = (String)inspectionResult.getUserGuid();
        if(userGuid == null) {
            return R.error(400, "userGuid不能为空");
        }
        SysUserEntity user = userService.selectByGuid(userGuid);
        if (user == null){
            return R.error(400, "userGuid错误，没有找到对应用户");
        }
        inspectionResult.setGuid(UUID.randomUUID().toString());
        inspectionResult.setCreateTime(new Date());
        resultService.deleteByAppResultGuid(appResultGuid);
        String guid = resultService.insertResult(inspectionResult);
        if(guid == null){
            return R.error(500, "服务器内部错误");
        } else {
            return R.ok().put("guid", guid);
        }
    }

    @Login
    @PostMapping("/upload")
    @ApiOperation("上传巡检结果")
    public R upload(@RequestParam("file") MultipartFile file, @RequestParam("type") String type , @RequestParam("result") String result) throws Exception {
        if (file.isEmpty()) {
            throw new RRException("媒体文件不能为空");
        }

        InspectionRandomResultEntity resultEntity = resultService.selectByGuid(result);

        if(resultEntity == null){
            System.out.println("random巡检结果没有找到：" + result);
            return R.error(400, "巡检结果没有找到");
        } else {
            //上传文件
            InspectionRandomResultMediaEntity mediaEntity = new InspectionRandomResultMediaEntity();
            mediaEntity.setGuid(UUID.randomUUID().toString());
            mediaEntity.setType(type);
            mediaEntity.setResultId(resultEntity.getId());
            mediaEntity.setContent(file.getBytes());
            mediaEntity.setCreateTime(new Date());
            boolean isOk = mediaService.insert(mediaEntity);

            if (isOk) {
                return R.ok().put("guid", mediaEntity.getGuid()).put("result_guid", result);
            } else {
                return R.error(500, "服务器内部错误");
            }
        }
    }
}
