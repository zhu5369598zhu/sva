package io.renren.modules.sys.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.sys.entity.DeviceExceptionEntity;
import io.renren.modules.sys.service.DeviceExceptionService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-06-24 16:21:45
 */
@RestController
@RequestMapping("sys/deviceexception")
public class DeviceExceptionController {
    @Autowired
    private DeviceExceptionService deviceExceptionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:deviceexception:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = deviceExceptionService.queryPage(params);

        return R.ok().put("page", page);
    }
    
    /**
     * 根据部门下的 设备等级和 设备 进行回显异常等级
     */
    @RequestMapping("/checklist")
    @RequiresPermissions("sys:deviceexception:checklist")
    public R checklist(@RequestParam Map<String, Object> params) {
    	
    	DeviceExceptionEntity deviceException = deviceExceptionService.checkList(params);
    	
    	return R.ok().put("deviceException", deviceException); 
    }
    
    /**
     * 获取部门树及下属设备
     */
    @RequestMapping("/tree")
    @RequiresPermissions("sys:deviceexception:tree")
    public R tree(@RequestParam Map<String, Object> params){
        String deviceName = (String)params.get("deviceName");
        List<Map<String,Object>> deviceTreeList = deviceExceptionService.findDeviceTree(deviceName);

        return R.ok().put("deviceTree", deviceTreeList);
    }
    /*
     * 部门推送规则
     * */
    @RequestMapping("/messagetree")
    @RequiresPermissions("sys:deviceexception:messagetree")
    public R messageTree(@RequestParam Map<String, Object> params) {
    	
    	List<Map<String,Object>> messageTreeList = deviceExceptionService.findMessageTreeList(params);
    	return R.ok().put("messageTree", messageTreeList);
    }

    
    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:deviceexception:info")
    public R info(@PathVariable("id") Integer id){
		DeviceExceptionEntity deviceException = deviceExceptionService.selectById(id);
		Integer deviceSmsType = deviceException.getDeviceSmsType();
		if(deviceSmsType ==1) {// 按照用户推送
			String smsUserIds = deviceException.getSmsUserIds();
			String[] split = smsUserIds.split(",");
			List<Long> list = new ArrayList<Long>(); 
			for(String s: split) {
				list.add(Long.parseLong(s));
			}
			deviceException.setUserIdList(list); 
		}else if(deviceSmsType ==2) {// 按照角色推送
			String smsUserIds = deviceException.getSmsUserIds();
			String[] split = smsUserIds.split(",");
			List<Long> list = new ArrayList<Long>(); 
			for(String s: split) {
				list.add(Long.parseLong(s));
			}
			deviceException.setUserIdList(list); 
		}
		
        return R.ok().put("deviceexception", deviceException);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:deviceexception:save")
    public R save(@RequestBody DeviceExceptionEntity deviceException){
    	Integer deviceSmsType = deviceException.getDeviceSmsType();
    	if(deviceSmsType ==1) { // 按照用户推送
    		List<Long> userIdList = deviceException.getUserIdList();
        	StringBuffer userbuffer = new StringBuffer();
        	for(Long user: userIdList) {
        		if(user !=null) {
        			userbuffer.append(user.toString()+",");
        		}
        	}
        	deviceException.setSmsUserIds(userbuffer.toString().substring(0,userbuffer.toString().length()-1));
    	}else if(deviceSmsType ==2) { // 按照角色推送
    		List<Long> userIdList = deviceException.getUserIdList();
    		List<Long> listTemp = new ArrayList<Long>();  
            for(int i=0;i<userIdList.size();i++){  
                if(!listTemp.contains(userIdList.get(i))){  
                    listTemp.add(userIdList.get(i));  
                }  
            }
        	StringBuffer userbuffer = new StringBuffer();
        	for(Long user: listTemp) {
        		if(user != null) {
        			userbuffer.append(user.toString()+",");
        			
        		}
        	}
        	deviceException.setSmsUserIds(userbuffer.toString().substring(0,userbuffer.toString().length()-1));
    	}
    	 
		deviceExceptionService.insert(deviceException);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:deviceexception:update")
    public R update(@RequestBody DeviceExceptionEntity deviceException){
    	Integer deviceSmsType = deviceException.getDeviceSmsType();
    	if(deviceSmsType ==1) { // 按照用户推送
    		List<Long> userIdList = deviceException.getUserIdList();
        	StringBuffer userbuffer = new StringBuffer();
        	for(Long user: userIdList) {
        		if(user !=null) {
        			userbuffer.append(user.toString()+",");
        		}
        	}
        	deviceException.setSmsUserIds(userbuffer.toString().substring(0,userbuffer.toString().length()-1));
    	}else if(deviceSmsType ==2) { // 按照角色推送
    		List<Long> userIdList = deviceException.getUserIdList();
    		List<Long> listTemp = new ArrayList<Long>();  
            for(int i=0;i<userIdList.size();i++){  
                if(!listTemp.contains(userIdList.get(i))){  
                    listTemp.add(userIdList.get(i));  
                }  
            }
        	StringBuffer userbuffer = new StringBuffer();
        	for(Long user: listTemp) {
        		if(user != null) {
        			userbuffer.append(user.toString()+",");
        		}
        	}
        	deviceException.setSmsUserIds(userbuffer.toString().substring(0,userbuffer.toString().length()-1));
    	}
    	
		deviceExceptionService.updateById(deviceException);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:deviceexception:delete")
    public R delete(@RequestBody Integer[] ids){
			deviceExceptionService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }
    
    /**
     * 切换发送方式
     */
    @RequestMapping("/changSmsType")
    @RequiresPermissions("sys:deviceexception:changSmsType")
    public R changSmsType(@RequestParam Map<String, Object> params) {
    	DeviceExceptionEntity deviceException = deviceExceptionService.changSmsType(params);
    	
    	return R.ok().put("deviceexception", deviceException);
    }
    
}
