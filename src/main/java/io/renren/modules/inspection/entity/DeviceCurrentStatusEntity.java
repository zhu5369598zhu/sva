package io.renren.modules.inspection.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-12-03 10:55:02
 */
@TableName("tb_device_current_status")
public class DeviceCurrentStatusEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private Integer id;
	/**
	 * 设备id
	 */
	private Integer deviceId;
	/**
	 * 设备状态(1运行状态，2备用状态，3检修状态)
	 */
	private String deviceState;
	/**
	 * 当前日期
	 */
	private Date currentDate;
	/**
	 * 轮次Guid
	 */
	@TableField(exist = false)
	private String turnGuid;
	/**
	 * 用户Guid
	 */
	@TableField(exist = false)
	private String userGuid;
	/**
	 * 设备Guid
	 */
	@TableField(exist = false)
	private String deviceGuid;
	/**
	 * 设置：主键id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：主键id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：设备id
	 */
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	/**
	 * 获取：设备id
	 */
	public Integer getDeviceId() {
		return deviceId;
	}
	/**
	 * 设置：设备状态(1运行状态，2备用状态，3检修状态)
	 */
	public void setDeviceState(String deviceState) {
		this.deviceState = deviceState;
	}
	/**
	 * 获取：设备状态(1运行状态，2备用状态，3检修状态)
	 */
	public String getDeviceState() {
		return deviceState;
	}
	/**
	 * 设置：当前日期
	 */
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
	/**
	 * 获取：当前日期
	 */
	public Date getCurrentDate() {
		return currentDate;
	}
	/**
	 * 获取：轮次Guid
	 */
	public String getTurnGuid() {
		return turnGuid;
	}
	/**
	 * 设置：轮次Guid
	 */
	public void setTurnGuid(String turnGuid) {
		this.turnGuid = turnGuid;
	}
	/**
	 * 获取：用户Guid
	 */
	public String getUserGuid() {
		return userGuid;
	}
	/**
	 * 设置：用户Guid
	 */
	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}
	/**
	 * 获取：设备Guid
	 */
	public String getDeviceGuid() {
		return deviceGuid;
	}
	/**
	 * 设置：设备Guid
	 */
	public void setDeviceGuid(String deviceGuid) {
		this.deviceGuid = deviceGuid;
	}
}
