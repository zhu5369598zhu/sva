package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.DeviceExceptionEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-06-24 16:21:45
 */
@Mapper
public interface DeviceExceptionDao extends BaseMapper<DeviceExceptionEntity> {

	List<Map<String, Object>> findDeviceList(HashMap<String, Object> hashMap);

	List<Map<String, Object>> findDeviceLeveList(HashMap<String, Object> hashMap);

	List<Map<String, Object>> findLevelDevice(HashMap<String, Object> hashMap);

	List<Map<String, Object>> findDevice(HashMap<String, Object> hashMap);

	List<Map<String, Object>> findDeptUserList(HashMap<String, Object> hashMap);

	DeviceExceptionEntity selectByDeptIdLevelId(HashMap<String, Object> hashMap);

	DeviceExceptionEntity selectByDeptIdLevelIdDeviceId(HashMap<String, Object> hashMap);

	List<Map<String, Object>> findDeptRoleList(HashMap<String, Object> hashMap);

	List<Map<String, Object>> queryListParentId(Long parentId);

	List<Map<String, Object>> findDeptRoleUserList(HashMap<String, Object> hashMap);

	DeviceExceptionEntity changSmsType(HashMap<String, Object> hashMap);

	DeviceExceptionEntity findDeviceExceptionBydeviceId(Map<String, Object> hashMap);

	DeviceExceptionEntity findDeviceExceptionByDeptidDeviceLevel(Map<String, Object> hashMap);

	void insertSms(HashMap<String, Object> map);




	

	
	
}
