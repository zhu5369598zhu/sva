package io.renren.modules.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.DeviceExceptionDao;
import io.renren.modules.sys.entity.DeviceExceptionEntity;
import io.renren.modules.sys.service.DeviceExceptionService;
import io.renren.modules.sys.service.SysDeptService;


@Service("deviceExceptionService")
public class DeviceExceptionServiceImpl extends ServiceImpl<DeviceExceptionDao, DeviceExceptionEntity> implements DeviceExceptionService {

	@Autowired
	private DeviceExceptionDao deviceExceptionDao;
	
	/*@Autowired
    private SysDeptService deptService;*/
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DeviceExceptionEntity> page = this.selectPage(
                new Query<DeviceExceptionEntity>(params).getPage(),
                new EntityWrapper<DeviceExceptionEntity>()
        );

        return new PageUtils(page);
    }
    // 回显 异常列表
    @Override
	public DeviceExceptionEntity checkList(Map<String, Object> params) {
    	String levelId = (String)params.get("levelId");
    	// String levelName = (String)params.get("levelName");
    	String deviceId = (String)params.get("deviceId");
    	// String deviceName = (String)params.get("deviceName");
    	String deptId = (String)params.get("deptId");
    	String type = (String)params.get("type");
    	HashMap<String,Object> hashMap = new HashMap<String, Object>();
    	hashMap.put("levelId", levelId);
    	hashMap.put("deptId", deptId);
    	DeviceExceptionEntity deviceExceptionEntity = baseMapper.selectByDeptIdLevelId(hashMap);
    	
    	if(type.equals("level")) { // 根据设备等级来回显
    		if(deviceExceptionEntity == null) {
    			return null;
    		}else {
    			String exceptionIds = deviceExceptionEntity.getExceptionIds();
    			String[] tablecheck = exceptionIds.split(",");
    			deviceExceptionEntity.setTablecheck(tablecheck);
    			return deviceExceptionEntity;
    		}
    	}else if(type.equals("device")){// 根据单个设备 来回显
    		hashMap.put("deviceId", deviceId);
    		DeviceExceptionEntity deviceException = baseMapper.selectByDeptIdLevelIdDeviceId(hashMap);
    		if(deviceException == null) {
    			return null;
    		}else {
    			String exceptionIds = deviceException.getExceptionIds();
    			String[] tablecheck = exceptionIds.split(",");
    			deviceException.setTablecheck(tablecheck);
    			return deviceException;
    		}
    		
    	}
    	
    	
		return null;
	}

	@Override
	public List<Map<String, Object>> findDeviceTree(String deviceName) {
		List<Map<String,Object>> root = deviceExceptionDao.queryListParentId(0l);
		
		for(Map<String, Object> m : root){
			getTreeNodeData(m , deviceName);
        }
		
		return clearNode(root);
	}
	
	private void getTreeNodeData(Map<String,Object> node, String deviceName){
		// 查看该id 下有没有 设备等级
		List<Map<String,Object>> levels = this.findDeviceLeveList((Long)node.get("deptId"), deviceName);
		for(Map<String,Object> level: levels) {
			// 查看该 设备等级下 有没有 设备
			Integer levelId = (Integer)level.get("id");
			List<Map<String,Object>> devices = this.findLevelDevice(levelId, (Long)node.get("deptId"));
			level.put("children", devices);
		}
		
		if (node.get("type").equals("dept")){
            List<Map<String,Object>> children = deviceExceptionDao.queryListParentId((Long)node.get("deptId"));
            if(children != null && children.size() > 0 ){
                children.addAll(levels);
                node.put("children", children);
                for(Map<String, Object> m : children){
                	if(m.get("type").equals("dept")) { // 如果是部门才 继续循环，是设备等级 就循环下一个部门
                		getTreeNodeData(m, deviceName);
                	}
                }
            } else {
                node.put("children", levels);
            }
        }
    }
	
	
	private List<Map<String, Object>> findDevice(Long deviceId) {
		HashMap<String,Object> hashMap = new HashMap<String,Object>();
		hashMap.put("deviceId", deviceId);
		return baseMapper.findDevice(hashMap);
	}

	public List<Map<String, Object>> findLevelDevice(Integer levelId, Long deptId) {
		HashMap<String,Object> hashMap = new HashMap<String,Object>();
		hashMap.put("levelId", levelId);
		hashMap.put("deptId", deptId);
		return baseMapper.findLevelDevice(hashMap);
	}

	public List<Map<String,Object>> findDeviceLeveList(Long deptId, String deviceName){
		HashMap<String,Object> hashMap = new HashMap<String,Object>();
		hashMap.put("deptId", deptId);
		hashMap.put("deviceName", deviceName);
		return baseMapper.findDeviceLeveList(hashMap);
	}
	
    public List<Map<String,Object>> findDeviceList(Integer deptId, String deviceName) {
    	HashMap<String, Object> hashMap = new HashMap<String, Object>();
    	hashMap.put("deptId", deptId);
    	hashMap.put("deviceName", deviceName);
        return baseMapper.findDeviceList(hashMap);
    }
    
    public List<Map<String,Object>> clearNode(List<Map<String,Object>> curNode){

        Iterator<Map<String,Object>> item = curNode.iterator();
        while (item.hasNext()){
            Map<String, Object> node = item.next();
            String type = (String)node.get("type");
            if(type.equals("dept")) {
                List<Map<String,Object>> children = (List<Map<String,Object>>)node.get("children");
                if (children.size() == 0){
                    item.remove();
                } else {
                    clearNode(children);
                }
            }
        }

        return curNode;

    }

	@Override
	public List<Map<String, Object>> findMessageTreeList(Map<String, Object> params) {
		String deviceSmsType =  (String)params.get("deviceSmsType");
		List<Map<String,Object>> root = deviceExceptionDao.queryListParentId(0l);
		for (Map<String, Object> m : root) {
			getMessageTreeNodeData(m,deviceSmsType);
		}
		
		return clearNode(root);
	}
    // 获取短信推送列表
	private void getMessageTreeNodeData(Map<String, Object> node, String deviceSmsType) {
		
		if(deviceSmsType.equals("1")) { // 按照用户推广
			// 查看该id 下有没有 用户
			List<Map<String,Object>> users = this.findDeptUserList((Long)node.get("deptId"));                                         
			if (node.get("type").equals("dept")){
	            List<Map<String,Object>> children = deviceExceptionDao.queryListParentId((Long)node.get("deptId"));
	            if(children != null && children.size() > 0 ){
	                children.addAll(users);
	                node.put("children", children);
	                for(Map<String, Object> m : children){
	                	if(m.get("type").equals("dept")) { // 如果是部门才 继续循环，是设备等级 就循环下一个部门
	                		getMessageTreeNodeData(m,deviceSmsType);
	                	}
	                }
	            } else {
	                node.put("children", users);
	            }
	        }
		}else if(deviceSmsType.equals("2")) { // 按照角色推广
			// 查看该 id 下有没有 角色
			List<Map<String, Object>> roles = this.findDeptRoleList((Long)node.get("deptId"));
			for(Map<String, Object> role: roles) {
				// 查看该 角色下 的用户 
				Long roleId = (Long)role.get("roleId");
				List<Map<String,Object>> users = this.findDeptRoleUserList((Long)node.get("deptId"),roleId);
				role.put("children", users);
			}
			if(node.get("type").equals("dept")) {
				List<Map<String,Object>> children = deviceExceptionDao.queryListParentId((Long)node.get("deptId"));
				if(children != null && children.size() > 0 ){
	                children.addAll(roles);
	                node.put("children", children);
	                for(Map<String, Object> m : children){
	                	if(m.get("type").equals("dept")) { // 如果是部门才 继续循环，是设备等级 就循环下一个部门
	                		getMessageTreeNodeData(m,deviceSmsType);
	                	}
	                }
	            } else {
	                node.put("children", roles);
	            }
				
			}
			
		}
		
		
	}
    // 查看该角色下面的用户
	private List<Map<String, Object>> findDeptRoleUserList(Long deptId, Long roleId) {
		HashMap<String,Object> hashMap = new HashMap<String,Object>();
		hashMap.put("deptId", deptId);
		hashMap.put("roleId", roleId);
		return baseMapper.findDeptRoleUserList(hashMap);
	}
	// 根据部门得到用户
	private List<Map<String, Object>> findDeptUserList(Long deptId) {
		HashMap<String,Object> hashMap = new HashMap<String, Object>();
		hashMap.put("deptId", deptId);
		return baseMapper.findDeptUserList(hashMap);
	}
	// 根据部门得到角色
	private List<Map<String, Object>> findDeptRoleList(Long deptId) {
		HashMap<String,Object> hashMap = new HashMap<String, Object>();
		hashMap.put("deptId", deptId);
		return baseMapper.findDeptRoleList(hashMap);
	}
	@Override
	public DeviceExceptionEntity changSmsType(Map<String, Object> params) {
		HashMap<String,Object> hashMap = new HashMap<String,Object>();
		String id = (String)params.get("id");
    	String deviceSmsType = (String)params.get("deviceSmsType");
    	hashMap.put("id", id);
    	hashMap.put("deviceSmsType", deviceSmsType);
    	DeviceExceptionEntity deviceException = baseMapper.changSmsType(hashMap);
    	if(deviceException!=null) {
			String smsUserIds = deviceException.getSmsUserIds();
			String[] split = smsUserIds.split(",");
			List<Long> list = new ArrayList<Long>(); 
			for(String s: split) {
				list.add(Long.parseLong(s));
			}
			deviceException.setUserIdList(list); 
    	}
		return deviceException;
	}
	// 根据设备id 查询数据记录
	@Override
	public DeviceExceptionEntity findDeviceExceptionBydeviceId(Map<String, Object> hashMap) {
		
		return baseMapper.findDeviceExceptionBydeviceId(hashMap);
	}
	// 根据设备等级 和 部门去查询
	@Override
	public DeviceExceptionEntity findDeviceExceptionByDeptidDeviceLevel(Map<String, Object> hashMap) {
		
		return baseMapper.findDeviceExceptionByDeptidDeviceLevel(hashMap);
	}
	@Override
	public void insertSms(HashMap<String, Object> map) {
		
		baseMapper.insertSms(map);
	}
	
	
	

}
