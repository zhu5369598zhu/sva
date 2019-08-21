package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.DeviceExceptionEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-06-24 16:21:45
 */
public interface DeviceExceptionService extends IService<DeviceExceptionEntity> {

    PageUtils queryPage(Map<String, Object> params);

	List<Map<String, Object>> findDeviceTree(String deviceName);

	List<Map<String, Object>> findMessageTreeList(Map<String, Object> params);

	DeviceExceptionEntity checkList(Map<String, Object> params);

	DeviceExceptionEntity changSmsType(Map<String, Object> params);

	DeviceExceptionEntity findDeviceExceptionBydeviceId(Map<String, Object> hashMap);

	DeviceExceptionEntity findDeviceExceptionByDeptidDeviceLevel(Map<String, Object> hashMap);

	void insertSms(HashMap<String, Object> map); 

}

