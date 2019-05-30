package io.renren.modules.app.controller;

import io.renren.common.annotation.SysLog;
import io.renren.common.exception.RRException;
import io.renren.common.utils.R;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.app.annotation.LoginUser;
import io.renren.modules.app.service.InspectionService;
import io.renren.modules.inspection.entity.InspectionItemEntity;
import io.renren.modules.inspection.entity.InspectionResultEntity;
import io.renren.modules.inspection.entity.InspectionResultMediaEntity;
import io.renren.modules.inspection.entity.PdaEntity;
import io.renren.modules.inspection.service.InspectionItemService;
import io.renren.modules.inspection.service.InspectionResultMediaService;
import io.renren.modules.inspection.service.InspectionResultService;
import io.renren.modules.inspection.service.PdaService;
import io.renren.modules.sys.entity.AppUpgradeEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.AppUpgradeService;
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
@RequestMapping("/app/inspection/")
@Api("APP巡检相关接口")
public class AppInspectionController {
    @Autowired
    private InspectionService inspectionService;
    @Autowired
    private InspectionResultService resultService;
    @Autowired
    private InspectionResultMediaService inspectionResultMediaService;
    @Autowired
    private InspectionItemService itemService;
    @Autowired
    private PdaService pdaService;
    @Autowired
    private AppUpgradeService upgradeService;

    @GetMapping("upgrade")
    @ApiOperation("获取升级信息")
    public R upgrade(){
        Map<String,Object> upgradeJson = new HashMap<>();
        AppUpgradeEntity upgrade = upgradeService.selectById(1);
        if (upgrade == null) {
            return R.error(400,"服务器故障，请联系管理员");
        } else {
            StringBuffer url = new StringBuffer("http://");
            if (upgrade.getIsDomain().equals(0)){
                url.append(upgrade.getLocalhost());
            }else{
                url.append(upgrade.getDomain());
            }
            url.append("/sva/sys/appupgrade/app.apk");
            upgradeJson.put("isMust", upgrade.getIsMust());
            upgradeJson.put("apk", url.toString());
            upgradeJson.put("versionName", upgrade.getAppVersion());
            upgradeJson.put("versionCode", upgrade.getAppVersionCode());
        }
        return R.ok().put("upgrade", upgradeJson);
    }

    @Login
    @GetMapping("download")
    @ApiOperation("下载巡检基础信息")
    public R donload(@LoginUser SysUserEntity user, @RequestParam("pdamac") String pdaMac){
        PdaEntity pda = pdaService.selectByMac(pdaMac);
        if (pda == null) {
            return R.error(400,"pda设备mac地址没有登记");
        }
        HashMap<String, Object> downloadList = inspectionService.download(user, pdaMac);

        return R.ok().put("download", downloadList);
    }

    @Login
    @PostMapping("save")
    @ApiOperation("上传巡检结果")
    public R save (@RequestBody InspectionResultEntity inspectionResult){
        String appResultGuid = (String)inspectionResult.getAppResultGuid();
        if(appResultGuid == null) {
            return R.error(400, "appResultGuid不能为空");
        }
        String itemGuid = (String)inspectionResult.getItemGuid();
        if(itemGuid == null){
            return R.error(400, "itemGuid不能为空");
        }
        String userGuid = (String)inspectionResult.getUserGuid();
        if(userGuid == null){
            return R.error(400, "userGuid不能为空");
        }
        String turnGuid = (String)inspectionResult.getTurnGuid();
        if(turnGuid == null){
            return R.error(400, "turnGuid不能为空");
        }
        if(inspectionResult.getIsCheck() == null){
            return R.error(400, "isCheck不能为空");
        }
        if(inspectionResult.getIsHere() == null){
            return R.error(400, "isHere不能为空");
        }
        if(inspectionResult.getIsOk() == null){
            return R.error(400, "isOk不能为空");
        }
        if(inspectionResult.getExceptionId() == null){
            return R.error(400, "exceptionId不能为空");
        }
        if(inspectionResult.getStatusId() == null){
            return R.error(400, "statusId不能为空");
        }
        if(inspectionResult.getStartTime() == null){
            return R.error(400, "startTime不能为空");
        }
        if(inspectionResult.getEndTime() == null){
            return R.error(400, "endTime不能为空");
        }
        resultService.deleteByAppResultGuid(appResultGuid);
        String guid = resultService.insertResult(inspectionResult);
        if(guid.equals("")){
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

        InspectionResultEntity resultEntity = resultService.selectByGuid(result);

        if(resultEntity == null){
            return R.error(400, "巡检结果没有找到");
        } else {
            //上传文件
            InspectionResultMediaEntity mediaEntity = new InspectionResultMediaEntity();
            mediaEntity.setGuid(UUID.randomUUID().toString());
            mediaEntity.setType(type);
            mediaEntity.setResultId(resultEntity.getId());
            mediaEntity.setContent(file.getBytes());
            mediaEntity.setCreateTime(new Date());
            boolean isOk = inspectionResultMediaService.insert(mediaEntity);

            if (isOk) {
                return R.ok().put("guid", mediaEntity.getGuid()).put("result_guid", result);
            } else {
                return R.error(500, "服务器内部错误");
            }
        }
    }
}
