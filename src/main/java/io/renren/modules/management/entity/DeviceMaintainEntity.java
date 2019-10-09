package io.renren.modules.management.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备日常维护记录表
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-10-09 15:39:27
 */
@TableName("tb_device_maintain")
public class DeviceMaintainEntity implements Serializable {
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
	 * 工单编号
	 */
	private String orderNumber;
	/**
	 * 缺陷单编号
	 */
	private String defectiveNumber;
	/**
	 * 
	 */
	private String defectiveTheme;
	/**
	 * 维护时间
	 */
	private Date confirmedTime;

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
	 * 设置：工单编号
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	/**
	 * 获取：工单编号
	 */
	public String getOrderNumber() {
		return orderNumber;
	}
	/**
	 * 设置：缺陷单编号
	 */
	public void setDefectiveNumber(String defectiveNumber) {
		this.defectiveNumber = defectiveNumber;
	}
	/**
	 * 获取：缺陷单编号
	 */
	public String getDefectiveNumber() {
		return defectiveNumber;
	}
	/**
	 * 设置：
	 */
	public void setDefectiveTheme(String defectiveTheme) {
		this.defectiveTheme = defectiveTheme;
	}
	/**
	 * 获取：
	 */
	public String getDefectiveTheme() {
		return defectiveTheme;
	}
	/**
	 * 设置：维护时间
	 */
	public void setConfirmedTime(Date confirmedTime) {
		this.confirmedTime = confirmedTime;
	}
	/**
	 * 获取：维护时间
	 */
	public Date getConfirmedTime() {
		return confirmedTime;
	}
}
