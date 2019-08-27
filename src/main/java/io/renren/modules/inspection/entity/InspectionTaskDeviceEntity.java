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
	private Long lineId;
	/**
	 * 轮次id
	 */
	private Long turnId;
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
	private Date inspectionDate;
	/**
	 * 是否更新
	 */
	private Integer isUpdate;


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
	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}
	/**
	 * 获取：线路id
	 */
	public Long getLineId() {
		return lineId;
	}
	/**
	 * 设置：轮次id
	 */
	public void setTurnId(Long turnId) {
		this.turnId = turnId;
	}
	/**
	 * 获取：轮次id
	 */
	public Long getTurnId() {
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
	public void setInspectionDate(Date inspectionDate) {
		this.inspectionDate = inspectionDate;
	}
	/**
	 * 获取：巡检日期
	 */
	public Date getInspectionDate() {
		return inspectionDate;
	}
	/**
	 * 设置：是否更新
	 */
	public void setIsUpdate(Integer isUpdate) {
		this.isUpdate = isUpdate;
	}
	/**
	 * 获取：是否更新
	 */
	public Integer getIsUpdate() {
		return isUpdate;
	}
}
