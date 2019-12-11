package io.renren.modules.inspection.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

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
	 * 巡检设备状态
	 */
	private Integer deviceState;
	/**
	 * 应检项数
	 */
	private Integer inspectItemCount;
	/**
	 * 已检项数
	 */
	private Integer inspectedItemCount;
	/**
	 * 应检项id集合
	 */
	private String inspectItems;
	/**
	 * 已检项id集合
	 */
	private String inspectedItems;
	/**
	 * 巡检日期
	 */
	private Date inspectionDate;
	/**
	 * 是否更新
	 */
	private Integer isUpdate;
	/**
	 * 是否巡检
	 */
	private Integer isInspected;
	/**
	 * 开始巡检时间
	 */
	private String startTime;
	/**
	 * 结束巡检时间
	 */

	private String endTime;
	/**
	 * 上传时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createTime;

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
	public void setInspectItemCount(Integer inspectItemCount) {
		this.inspectItemCount = inspectItemCount;
	}
	/**
	 * 获取：应检项数
	 */
	public Integer getInspectItemCount() {
		return inspectItemCount;
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
	/**
	 * 设置：是否已巡检
	 */
	public void setIsInspected(Integer isInspected) {
		this.isInspected = isInspected;
	}
	/**
	 * 获取：是否已巡检
	 */
	public Integer getIsInspected() {
		return isInspected;
	}
	/**
	 * 设置：开始巡检时间
	 */

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取：开始巡检时间
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * 设置：结束巡检时间
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取：结束巡检时间
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * 设置：上传巡检时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：上传巡检时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 获取：应检巡检项id集合
	 */
	public String getInspectItems() {
		return inspectItems;
	}
	/**
	 * 设置：应检巡检项id集合
	 */
	public void setInspectItems(String inspectItems) {
		this.inspectItems = inspectItems;
	}
	/**
	 * 获取：已检巡检项id集合
	 */
	public String getInspectedItems() {
		return inspectedItems;
	}
	/**
	 * 设置：已检巡检项id集合
	 */
	public void setInspectedItems(String inspectedItems) {
		this.inspectedItems = inspectedItems;
	}
	/**
	 * 获取：已检设备状态
	 */
	public Integer getDeviceState() {
		return deviceState;
	}
	/**
	 * 设置：已检设备状态
	 */
	public void setDeviceState(Integer deviceState) {
		this.deviceState = deviceState;
	}
}
