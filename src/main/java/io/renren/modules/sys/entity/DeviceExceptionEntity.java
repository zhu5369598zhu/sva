package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-06-24 16:21:45
 */
@TableName("tb_device_exception")
public class DeviceExceptionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 设备异常推送id
	 */
	@TableId
	private Integer id;
	/**
	 * 设备等级id
	 */
	private Integer deviceLevel;
	/**
	 * 设备等级名称
	 */
	private String deviceLevelName;
	/**
	 * 设备id
	 */
	private Integer deviceId;
	/**
	 * 设备名称
	 */
	private String deviceName;
	/**
	 * 异常id集合
	 */
	private String exceptionIds;
	/**
	 * 所属机构
	 */
	private Integer deviceDept;
	/**
	 * 推送规则（1.按照用户推送，2按照角色推送）
	 */
	private Integer deviceSmsType;
	/**
	 * 按照角色推送，推送角色id
	 */
	private Integer smsRoleId;
	/**
	 * 按照用户推送， 推送用户id集合
	 */
	private String smsUserIds;
	/**
	 * 该设备是否发送短信（0 不发送短信，1 发送短信）
	 */
	private Integer isOk;
	/**
	 * 该设备是否发送微信（0 不发送微信，1 发送微信）
	 */
	private Integer wxOk;
	/**
	 * 该设备是否发送钉钉消息（0 不发送钉钉，1 发送钉钉）
	 */
	private Integer ddOk;
	/**
	 * type 是 level 设备等级分类，device 单个设备
	 */
	private String type;
	
	@TableField(exist=false)
	private List<Long> userIdList;
	
	@TableField(exist=false)
	private String[] tablecheck;
	
	/**
	 * 设置：设备异常推送id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：设备异常推送id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：设备等级id
	 */
	public void setDeviceLevel(Integer deviceLevel) {
		this.deviceLevel = deviceLevel;
	}
	/**
	 * 获取：设备等级id
	 */
	public Integer getDeviceLevel() {
		return deviceLevel;
	}
	/**
	 * 设置：设备等级名称
	 */
	public void setDeviceLevelName(String deviceLevelName) {
		this.deviceLevelName = deviceLevelName;
	}
	/**
	 * 获取：设备等级名称
	 */
	public String getDeviceLevelName() {
		return deviceLevelName;
	}
	/**
	 * 设置：所属机构
	 */
	public void setDeviceDept(Integer deviceDept) {
		this.deviceDept = deviceDept;
	}
	/**
	 * 获取：所属机构
	 */
	public Integer getDeviceDept() {
		return deviceDept;
	}
	/**
	 * 设置：推送规则（1.按照用户推广，2按照角色推广）
	 */
	public void setDeviceSmsType(Integer deviceSmsType) {
		this.deviceSmsType = deviceSmsType;
	}
	/**
	 * 获取：推送规则（1.按照用户推广，2按照角色推广）
	 */
	public Integer getDeviceSmsType() {
		return deviceSmsType;
	}
	public Integer getDeviceId() {
		return deviceId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public String getExceptionIds() {
		return exceptionIds;
	}
	public Integer getSmsRoleId() {
		return smsRoleId;
	}
	public String getSmsUserIds() {
		return smsUserIds;
	}
	
	public Integer getWxOk() {
		return wxOk;
	}
	public void setWxOk(Integer wxOk) {
		this.wxOk = wxOk;
	}
	public Integer getIsOk() {
		return isOk;
	}
	public Integer getDdOk() { return ddOk; }
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public void setExceptionIds(String exceptionIds) {
		this.exceptionIds = exceptionIds;
	}
	public void setSmsRoleId(Integer smsRoleId) {
		this.smsRoleId = smsRoleId;
	}
	public void setSmsUserIds(String smsUserIds) {
		this.smsUserIds = smsUserIds;
	}
	public void setIsOk(Integer isOk) {
		this.isOk = isOk;
	}
	public void setDdOk(Integer ddOk) { this.ddOk = ddOk; }
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Long> getUserIdList() {
		return userIdList;
	}
	public void setUserIdList(List<Long> userIdList) {
		this.userIdList = userIdList;
	}
	public String[] getTablecheck() {
		return tablecheck;
	}
	public void setTablecheck(String[] tablecheck) {
		this.tablecheck = tablecheck;
	}
	
	
}
