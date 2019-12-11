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
@TableName("tb_inspection_task")
public class InspectionTaskEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自动增加
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
	private Long turnId;
	/**
	 * 应检设备数
	 */
	private Integer inspectDeviceCount;
	/**
	 * 已检设备数
	 */
	private Integer inspectedDeviceCount;
	/**
	 * 应检设备id 集合
	 */
	private String inspectDevices;
	/**
	 * 已检设备id 集合
	 */
	private String inspectedDevices;
	/**
	 * 应检项数
	 */
	private Integer inspectItemCount;
	/**
	 * 已检项数
	 */
	private Integer inspectedItemCount;
	/**
	 * 是否已巡检
	 */
	private Integer isInspected;
	/**
	 * 是否跨度
	 */
	private Integer isSpan;
	/**
	 * 巡检跨度结束时间
	 */
	private Date inspectionSpanEndDate;
	/**
	 * 巡检开始时间
	 */
	private String turnStartTime;
	/**
	 * 巡检结束时间
	 */
	private String turnEndTime;
    /**
	 * 员工实际巡检开始实际
	 * */
	private String inspectedStartTime;
    /**
	 * 员工实际巡检结束时间
	 * */
	private String inspectedEndTime;


	/**
	 * 设置：自动增加
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：自动增加
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
	 * 设置：应检设备数
	 */
	public void setInspectDeviceCount(Integer inspectDeviceCount) {
		this.inspectDeviceCount = inspectDeviceCount;
	}
	/**
	 * 获取：应检设备数
	 */
	public Integer getInspectDeviceCount() {
		return inspectDeviceCount;
	}
	/**
	 * 设置：已检设备数
	 */
	public void setInspectedDeviceCount(Integer inspectedDeviceCount) {
		this.inspectedDeviceCount = inspectedDeviceCount;
	}
	/**
	 * 获取：已检设备数
	 */
	public Integer getInspectedDeviceCount() {
		return inspectedDeviceCount;
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
	 * 设置：是否跨度
	 */
	public void setIsSpan(Integer isSpan) {
		this.isSpan = isSpan;
	}
	/**
	 * 获取：是否跨度
	 */
	public Integer getIsSpan() {
		return isSpan;
	}
	/**
	 * 设置：巡检跨度结束时间
	 */
	public void setInspectionSpanEndDate(Date inspectionSpanEndDate) {
		this.inspectionSpanEndDate = inspectionSpanEndDate;
	}

	/**
	 * 获取：巡检跨度结束时间
	 */
	public Date getInspectionSpanEndDate() {
		return inspectionSpanEndDate;
	}

	/**
	 * 设置：巡检周期id
	 */
	public void setTurnStartTime(String turnStartTime){
		this.turnStartTime = turnStartTime;
	}

	/**
	 * 获取：巡检周期id
	 */
	public String getTurnStartTime(){
		return this.turnStartTime;
	}

	/**
	 * 设置：巡检周期id
	 */
	public void setTurnEndTime(String turnEndTime){
		this.turnEndTime = turnEndTime;
	}

	/**
	 * 获取：巡检周期id
	 */
	public String getTurnEndTime(){
		return turnEndTime;
	}
	/**
	 * 获取：员工实际开始巡检时间
	* */
	public String getInspectedStartTime() {
		return inspectedStartTime;
	}
    /**
	 * 设置：员工实际开始巡检时间
	 * */
	public void setInspectedStartTime(String inspectedStartTime) {
		this.inspectedStartTime = inspectedStartTime;
	}
    /**
	 * 获取：员工实际结束巡检时间
	 * */
	public String getInspectedEndTime() {
		return inspectedEndTime;
	}
    /**
	 * 设置：员工实际结束巡检时间
	 * */
	public void setInspectedEndTime(String inspectedEndTime) {
		this.inspectedEndTime = inspectedEndTime;
	}
	/**
	 * 获取：应检设备id 集合
	 * */
	public String getInspectDevices() {
		return inspectDevices;
	}
	/**
	 * 设置：应检设备id 集合
	 * */
	public void setInspectDevices(String inspectDevices) {
		this.inspectDevices = inspectDevices;
	}
	/**
	 * 获取：已检设备id 集合
	 * */
	public String getInspectedDevices() {
		return inspectedDevices;
	}
	/**
	 * 设置：已检设备id 集合
	 * */
	public void setInspectedDevices(String inspectedDevices) {
		this.inspectedDevices = inspectedDevices;
	}
}
