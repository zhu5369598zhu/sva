package io.renren.modules.inspection.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-01-21 03:10:48
 */
@TableName("tb_zone_device")
public class ZoneDeviceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 巡区id
	 */
	@TableId
	private Integer id;
	/**
	 * 巡区id
	 */
	private Integer zoneId;
	/**
	 * 巡区名称
	 */
	@TableField(exist = false)
	private String zoneName;
	/**
	 * 设备id
	 */
	private Integer deviceId;
	/**
	 * 设备名称
	 */
	@TableField(exist = false)
	private String deviceName;
	/**
	 * 设备编码
	 */
	@TableField(exist = false)
	private String deviceCode;
	/**
	 * 巡检排序
	 */
	private Integer orderNum;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 设置：ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：ID
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 设置：巡区id
	 */
	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}
	/**
	 * 获取：巡区id
	 */
	public Integer getZoneId() {
		return zoneId;
	}
	/**
	 * 获取：巡区名称
	 */
	public String getZoneName() {
		return zoneName;
	}
	/**
	 * 设置：巡区id
	 */
	public void setZoneName(String zoneName) { this.zoneName = zoneName; }
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
	 * 设置：设备名称
	 */
	public void setDeviceName(String deviceName) { this.deviceName = deviceName; }
	/**
	 * 获取：设备名称
	 */
	public String getDeviceName() { return deviceName; }
	/**
	 * 设置：设备编码
	 */
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	/**
	 * 获取：设备编码
	 */
	public String getDeviceCode() {
		return deviceCode;
	}
	/**
	 * 设置：巡检排序
	 */
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	/**
	 * 获取：巡检排序
	 */
	public Integer getOrderNum() {
		return orderNum;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
}
