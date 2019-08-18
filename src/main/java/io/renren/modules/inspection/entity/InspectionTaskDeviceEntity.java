package io.renren.modules.inspection.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-08-11 16:55:04
 */
@TableName("tb_inspection_task_device")
public class InspectionTaskDeviceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增id
	 */
	@TableId
	private Integer id;
	/**
	 * 线路id
	 */
	private Integer lineId;
	/**
	 * 轮次id
	 */
	private Integer turnId;
	/**
	 * 设备id
	 */
	private Integer deviceId;
	/**
	 * 应检项数
	 */
	private Integer insepctItemCount;
	/**
	 * 已检项数
	 */
	private Integer inspectedItemCount;
	/**
	 * 巡检日期
	 */
	private Integer inspectionDate;

	/**
	 * 设置：自增id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：自增id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：线路id
	 */
	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}
	/**
	 * 获取：线路id
	 */
	public Integer getLineId() {
		return lineId;
	}
	/**
	 * 设置：轮次id
	 */
	public void setTurnId(Integer turnId) {
		this.turnId = turnId;
	}
	/**
	 * 获取：轮次id
	 */
	public Integer getTurnId() {
		return turnId;
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
	 * 设置：应检项数
	 */
	public void setInsepctItemCount(Integer insepctItemCount) {
		this.insepctItemCount = insepctItemCount;
	}
	/**
	 * 获取：应检项数
	 */
	public Integer getInsepctItemCount() {
		return insepctItemCount;
	}
	/**
	 * 设置：已检项数
	 */
	public void setInspectedItemCount(Integer inspectedItemCount) {
		this.inspectedItemCount = inspectedItemCount;
	}
	/**
	 * 获取：已检项数
	 */
	public Integer getInspectedItemCount() {
		return inspectedItemCount;
	}
	/**
	 * 设置：巡检日期
	 */
	public void setInspectionDate(Integer inspectionDate) {
		this.inspectionDate = inspectionDate;
	}
	/**
	 * 获取：巡检日期
	 */
	public Integer getInspectionDate() {
		return inspectionDate;
	}
}
