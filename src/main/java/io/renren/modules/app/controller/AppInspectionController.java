package io.renren.modules.app.controller;

import io.renren.common.exception.RRException;
import io.renren.common.utils.DingdingSend;
import io.renren.common.utils.R;
import io.renren.common.utils.SendSms;
import io.renren.common.utils.TestMessage;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.app.annotation.LoginUser;
import io.renren.modules.app.service.InspectionService;
import io.renren.modules.inspection.entity.*;
import io.renren.modules.inspection.service.*;
import io.renren.modules.setting.entity.ExceptionEntity;
import io.renren.modules.setting.service.ExceptionService;
import io.renren.modules.sys.entity.AppUpgradeEntity;
import io.renren.modules.sys.entity.DeviceExceptionEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.AppUpgradeService;
import io.renren.modules.sys.service.DeviceExceptionService;
import io.renren.modules.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

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
	private Logger logger = LoggerFactory.getLogger(getClass());

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
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceExceptionService deviceExceptionService;
    @Autowired
	private SysUserService sysUserService; 
    @Autowired
    private ExceptionService exceptionService;
    @Autowired
    private DeviceCurrentStatusService deviceCurrentStatusService;

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
	@PostMapping("test")
	@ApiOperation("上传巡检设备状态")
	public  R test(@RequestBody DeviceCurrentStatusEntity deviceCurrentStatusEntity){
		String updateDeviceStatus = deviceCurrentStatusService.text(deviceCurrentStatusEntity);
		return R.ok().put("text",updateDeviceStatus);
	}


    @Login
	@PostMapping("devicestatus")
	@ApiOperation("上传巡检设备状态")
    public  R devicestatus(@RequestBody DeviceCurrentStatusEntity deviceCurrentStatusEntity){
		String updateDeviceStatus = deviceCurrentStatusService.updateDeviceStatus(deviceCurrentStatusEntity);
		return R.ok().put("text",updateDeviceStatus);
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
		Map<String,Object> itemHashMap = new HashMap<String,Object>();
		itemHashMap.put("guid", itemGuid);
        List<InspectionItemEntity> inspctionItems = itemService.selectByMap(itemHashMap);
		InspectionItemEntity inspctionItem = null;
        if(inspctionItems.size() > 0){
        	inspctionItem = inspctionItems.get(0);
        	try{
				Double result = Double.valueOf(inspectionResult.getResult());
				inspectionResult.setExceptionId(1);
				inspectionResult.setIsOk(1);
				if( result > inspctionItem.getUpupLimit()){
					inspectionResult.setExceptionId(3);
					inspectionResult.setIsOk(0);
				}
				if(result > inspctionItem.getUpLimit() && result < inspctionItem.getUpupLimit()){
					inspectionResult.setExceptionId(2);
					inspectionResult.setIsOk(0);
				}
				if(result < inspctionItem.getDowndownLimit()){
					inspectionResult.setExceptionId(3);
					inspectionResult.setIsOk(0);
				}
				if(result < inspctionItem.getDownLimit() && result > inspctionItem.getDowndownLimit()){
					inspectionResult.setExceptionId(2);
					inspectionResult.setIsOk(0);
				}
			}catch (Exception e) {

			}
		}
		Integer deviceId = inspctionItem.getDeviceId();
        if(deviceId == null){
        	logger.error("item not found:" + itemGuid);
		}
        DeviceEntity device = deviceService.selectById(deviceId);
        Map<String,Object> hashMap = new HashMap<String,Object>();
        hashMap.put("deviceId", deviceId);
        DeviceExceptionEntity deviceException = deviceExceptionService.findDeviceExceptionBydeviceId(hashMap);
        if(deviceException == null) {
        	hashMap.put("deptId",device.getDeviceDept());
        	hashMap.put("deviceLevel",device.getDeviceLevel());
        	hashMap.put("deviceId", 0);
        	DeviceExceptionEntity deviceExceptionEntity = deviceExceptionService.findDeviceExceptionByDeptidDeviceLevel(hashMap);
        	if(deviceExceptionEntity!=null) {
				String exceptionIds = deviceExceptionEntity.getExceptionIds();
				String[] split = exceptionIds.split(",");
				for(String s: split) {
					if(s.equals(inspectionResult.getExceptionId().toString())) {
						String smsUserIds = deviceExceptionEntity.getSmsUserIds();
						String deviceName = device.getDeviceName(); // 设备名称
						String itemName = inspctionItem.getName(); // 巡检项名称
						ExceptionEntity exceptionEntity = exceptionService.selectById(s);
						String exceptionName = exceptionEntity.getName(); // 异常等级名称
						if(deviceExceptionEntity.getIsOk()==1){ // 发送短信
							JSONObject returnJson = new JSONObject(new LinkedHashMap());
							returnJson.put("deviceName", "["+deviceName+"]");
							returnJson.put("itemName", "["+itemName+"]");
							returnJson.put("exceptionName", "["+exceptionName+"]");
							if(!"".equals(smsUserIds)) {
								String[] userIds = smsUserIds.split(",");
								for(String userId: userIds) {
									SysUserEntity userEntity = sysUserService.selectById(Integer.parseInt(userId));
									String mobile = userEntity.getMobile();
									String isOk = SendSms.deviceSend(mobile, returnJson);
									System.out.println("是否成功"+ isOk);
									HashMap<String,Object> map = new HashMap<String,Object>();
									if(isOk.equals("ok")) { // 发送短信成功
										map.put("isOk", 1);
										map.put("phone", mobile);
										map.put("content", "尊敬的用户，您的设备"+deviceName+"巡检项"+itemName+"出现"+exceptionName+"的异常等级,请您及时关注哦。");
										map.put("type", 1);
										map.put("createTiem", new Date());
										deviceExceptionService.insertSms(map); // 发送短信记录
									}else { //  发送短信失败
										map.put("isOk", 0);
										map.put("phone", mobile);
										map.put("content", "尊敬的用户，您的设备"+deviceName+"巡检项"+itemName+"出现"+exceptionName+"的异常等级,请您及时关注哦。");                        				map.put("type", 1);
										map.put("createTiem", new Date());
										deviceExceptionService.insertSms(map); // 发送短信记录
									}
								}
							}
						}
						if(deviceExceptionEntity.getWxOk() ==1) { // 发送微信
							if(!"".equals(smsUserIds)) {
								String[] userIds = smsUserIds.split(",");
								for(String userId: userIds) {
									SysUserEntity userEntity = sysUserService.selectById(Integer.parseInt(userId));
									String wechat = userEntity.getWechat();
									if(!"".equals(wechat)) {
										TestMessage.deviceSend(wechat, "尊敬的用户，您的设备["+deviceName+"]巡检项["+itemName+"]出现["+exceptionName+"]的异常等级,请您及时关注哦。");
									}
								}
							}
						}
						if(deviceExceptionEntity.getDdOk() == 1){ // 发送钉钉消息
							if(!"".equals(smsUserIds)) {
								String[] userIds = smsUserIds.split(",");
								for(String userId: userIds) {
									SysUserEntity userEntity = sysUserService.selectById(Integer.parseInt(userId));
									String mobile = userEntity.getMobile();
									DingdingSend.ordersend("尊敬的用户，您的设备"+deviceName+"巡检项"+itemName+"出现"+exceptionName+"的异常等级,请您及时关注哦", mobile);
								}
							}
						}

					}
				}
        	}
        }else {
			String exceptionIds = deviceException.getExceptionIds();
			String[] split = exceptionIds.split(",");
			for(String s: split) {
				if(s.equals(inspectionResult.getExceptionId().toString())) {
					String deviceName = device.getDeviceName(); // 设备名称
					String itemName = inspctionItem.getName(); // 巡检项名称
					ExceptionEntity exceptionEntity = exceptionService.selectById(s);
					String exceptionName = exceptionEntity.getName(); // 异常等级名称
					String smsUserIds = deviceException.getSmsUserIds();

					if(deviceException.getIsOk() == 1) { // 发送短信
						JSONObject returnJson = new JSONObject(new LinkedHashMap());
						returnJson.put("deviceName", "["+deviceName+"]");
						returnJson.put("itemName", "["+itemName+"]");
						returnJson.put("exceptionName", "["+exceptionName+"]");
						if(!"".equals(smsUserIds)) {
							String[] userIds = smsUserIds.split(",");
							for(String userId: userIds) {
								SysUserEntity userEntity = sysUserService.selectById(Integer.parseInt(userId));
								String mobile = userEntity.getMobile();
								System.out.println("数据"+ returnJson.toJSONString());
								String isOk = SendSms.deviceSend(mobile, returnJson);
								System.out.println("是否发送成功"+ isOk);
								if(isOk.equals("ok")) { // 发送短信成功
									HashMap<String,Object> map = new HashMap<String,Object>();
									map.put("isOk", 1);
									map.put("phone", mobile);
									map.put("content", "尊敬的用户，您的设备"+deviceName+"巡检项"+itemName+"出现"+exceptionName+"的异常等级,请您及时关注哦。");
									map.put("type", 1);
									map.put("createTiem", new Date());
									deviceExceptionService.insertSms(map); // 发送短信记录
								}else { // 发送短信失败
									HashMap<String,Object> map = new HashMap<String,Object>();
									map.put("isOk", 0);
									map.put("phone", mobile);
									map.put("content", "尊敬的用户，您的设备"+deviceName+"巡检项"+itemName+"出现"+exceptionName+"的异常等级,请您及时关注哦。");
									map.put("type", 1);
									map.put("createTiem", new Date());
									deviceExceptionService.insertSms(map); // 发送短信记录
								}
							}
						}
					}
					if(deviceException.getWxOk() ==1) { // 发送微信
						if(!"".equals(smsUserIds)) {
							String[] userIds = smsUserIds.split(",");
							for(String userId: userIds) {
								SysUserEntity userEntity = sysUserService.selectById(Integer.parseInt(userId));
								String wechat = userEntity.getWechat();
								if(!"".equals(wechat)) {
									TestMessage.deviceSend(wechat, "尊敬的用户，您的设备["+deviceName+"]巡检项["+itemName+"]出现["+exceptionName+"]的异常等级,请您及时关注哦。");
								}
							}
						}
					}
					if(deviceException.getDdOk()==1) { // 发送钉钉消息
						if (!"".equals(smsUserIds)) {
							String[] userIds = smsUserIds.split(",");
							for (String userId : userIds) {
								SysUserEntity userEntity = sysUserService.selectById(Integer.parseInt(userId));
								String mobile = userEntity.getMobile();
								DingdingSend.ordersend("尊敬的用户，您的设备" + deviceName + "巡检项" + itemName + "出现" + exceptionName + "的异常等级,请您及时关注哦", mobile);
							}
						}
					}
				}
			}
        }
        resultService.deleteByAppResultGuid(appResultGuid);
        String guid = resultService.insertResult(inspectionResult);
        //特列处理，对guid生成失败的错误进行提示
        if(guid.length() < 30){
            return R.error(500, guid);
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
            return R.error(400, "巡检结果没有找到,上传附件失败");
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
