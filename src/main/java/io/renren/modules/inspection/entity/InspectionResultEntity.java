package io.renren.modules.inspection.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-02-13 11:25:57
 */
@TableName("tb_inspection_result")
public class InspectionResultEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer id;
	/**
	 * guid
	 */
	private String guid;
	/**
	 * app生成的guid
	 */
	private String appResultGuid;
	/**
	 * 巡检项id
	 */
	private Integer itemId;
	/**
	 * 巡检项guid
	 */
	@TableField(exist = false)
	private String itemGuid;
	/**
	 * 巡检项名称
	 */
	@TableField(exist = false)
	private String itemName;
	/**
	 * 设备名称
	 */
	@TableField(exist = false)
	private String deviceName;
	/**
	 * 设备id
	 */
	@TableField(exist = false)
	private Integer deviceId;
	/**
	 * 设备编码
	 */
	@TableField(exist = false)
	private String deviceCode;
	/**
	 * 机构id
	 */
	@TableField(exist = false)
	private Integer deptId;
	/**
	 * 线路id
	 */
	@TableField(exist = false)
	private Integer lineId;
	/**
	 * 机构id列表
	 */
	@TableField(exist = false)
	private List<Integer> deptIds;
	/**
	 * 是否正常
	 */
	private Integer isOk;
	/**
	 * 是否正常名称
	 */
	private String isOkName;
	/**
	 *是否到位
	 */
	private Integer isHere;
	/**
	 * 是否到位名称
	 */
	private String isHereName;
	/**
	 * 巡检类型
	 */
	@TableField(exist = false)
	private String inspectionType;
	/**
	 * 巡检类型id
	 */
	@TableField(exist = false)
	private Integer inspectionTypeId;
	/**
	 * 单位
	 */
	@TableField(exist = false)
	private String unit;
	/**
	 * 异常id
	 */
	private Integer exceptionId;
	/**
	 * 异常名称
	 */
	@TableField(exist = false)
	private String exceptionName;
	/**
	 * 异常guid
	 */
	@TableField(exist = false)
	private String exceptionGuid;
	/**
	 * 检时设备状态
	 */
	private Integer statusId;
	/**
	 * 检时设备状态
	 */
	@TableField(exist = false)
	private String deviceStatus;
	/**
	 * 巡检结果
	 */
	private String result;
	/**
	 * 轮次
	 */
	private Integer turnId;
	/**
	 * 轮次guid
	 */
	@TableField(exist = false)
	private String turnGuid;
	/**
	 * 轮次名称
	 */
	@TableField(exist = false)
	private String turnName;
	/**
	 * 巡检员id
	 */
	private Integer userId;
	/**
	 * 巡检员guid
	 */
	@TableField(exist = false)
	private String userGuid;
	/**
	 * 巡检员用户名
	 */
	@TableField(exist = false)
	private String username;
	/**
	 * 设备等级
	 */
	@TableField(exist = false)
	private String deviceLevel;
	/**
	 * 设备等级id
	 */
	@TableField(exist = false)
	private Integer deviceLevelId;
	/**
	 * 是否跳过巡检
	 */
	private Integer isCheck;
	/**
	 * 是否跳过巡检
	 */
	@TableField(exist = false)
	private String isCheckName;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 开始巡检时间
	 */
	private Date startTime;
	/**
	 * 结束巡检时间
	 */
	private Date endTime;
	/**
	 * 缺陷处理状态
	 */
	private Integer status;
	/**
	 * 上传时间
	 */
	private Date createTime;
	/**
	 * 上限
	 */
	private Double upLimit;
	/**
	 * 上上限
	 */
	private Double upupLimit;
	/**
	 * 下限
	 */
	private Double downLimit;
	/**
	 * 下下限
	 */
	private Double downdownLimit;
	/**
	 * 阀值
	 */
	@TableField(exist = false)
	private String limits;
	/**
	 * 附件
	 */
	private List<InspectionResultMediaEntity> medias;
	/**
	 * 工单确认人
	 */
	@TableField(exist = false)
	private String orderConfirmer;
	/**
	 * 工单确认人 id
	 */
	@TableField(exist = false)
	private Integer orderConfirmerId;
	/**
	 * 工单确认时间
	 */
	@TableField(exist = false)
	private Date confirmedTime;
	/**
	 * 工单状态
	 */
	@TableField(exist = false) 
	private Integer orderStatus;
	/**
	 * 工单状态名称
	 */
	@TableField(exist = false)
	private String orderStatusName;
	/**
	 * 缺陷工单主键id
	 */
	@TableField(exist = false)
	private Integer defectiveId;
	/**
	 * 缺陷工单编号
	 */
	@TableField(exist = false)
	private String defectiveNumber;
	/**
	 * 挂起原因
	 */
	private String hangUp;
	/**
	 * 设置：id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：item的id
	 */
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	/**
	 * 获取：item的id
	 */
	public Integer getItemId() {
		return itemId;
	}
	/**
	 * 设置：机构id
	 */
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	/**
	 * 获取：机构id
	 */
	public Integer getDeptId() {
		return deptId;
	}
	/**
	 * 设置：机构id
	 */
	public void setDeptIds(List<Integer> deptIds) {
		this.deptIds = deptIds;
	}
	/**
	 * 获取：机构id
	 */
	public List<Integer> getDeptIds() {
		return deptIds;
	}
	/**
	 * 设置：item的名称
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	/**
	 * 获取：item的id
	 */
	public String getItemName() {
		return itemName;
	}
	/**
	 * 设置：是否正常
	 */
	public void setIsOk(Integer isOk) {
		this.isOk = isOk;
	}
	/**
	 * 获取：是否正常
	 */
	public Integer getIsOk() {
		return isOk;
	}
	/**
	 * 设置：异常id
	 */
	public void setExceptionId(Integer exceptionId) {
		this.exceptionId = exceptionId;
	}
	/**
	 * 获取：异常id
	 */
	public Integer getExceptionId() {
		return exceptionId;
	}
	/**
	 * 设置：异常名称
	 */
	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}
	/**
	 * 获取：异常名称
	 */
	public String getExceptionName() {
		return exceptionName;
	}
	/**
	 * 设置：异常guid
	 */
	public void setExceptionGuid(String exceptionGuid) {
		this.exceptionGuid = exceptionGuid;
	}
	/**
	 * 获取：异常guid
	 */
	public String getExceptionGuid() {
		return exceptionGuid;
	}
	/**
	 * 设置：巡检结果
	 */
	public void setResult(String result) {
		this.result = result;
	}
	/**
	 * 获取：巡检结果
	 */
	public String getResult() {
		return result;
	}
	/**
	 * 设置：轮次id
	 */
	public void setTurnId(Integer turnId) { this.turnId = turnId; }
	/**
	 * 获取：轮次id
	 */
	public Integer getTurnId() {
		return turnId;
	}
	/**
	 * 设置：轮次名称
	 */
	public void setTurnName(String turnName) {
		this.turnName = turnName;
	}
	/**
	 * 获取：轮次名称
	 */
	public String getTurnName() { return turnName; }
	/**
	 * 设置：巡检人员
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 获取：巡检人员名称
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * 设置：巡检人员
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 获取：巡检人员名称
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 设置：是否跳过巡检
	 */
	public void setIsCheck(Integer isCheck) {
		this.isCheck = isCheck;
	}
	/**
	 * 获取：是否跳过巡检
	 */
	public Integer getIsCheck() {
		return isCheck;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：开始巡检时间
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取：开始巡检时间
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 设置：结束巡检时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取：结束巡检时间
	 */
	public Date getEndTime() {
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
	 * 设置：设备状态id
	 */
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	/**
	 * 获取：设备状态id
	 */
	public Integer getStatusId() {
		return statusId;
	}
	/**
	 * 设置：设备状态id
	 */
	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	/**
	 * 获取：设备状态id
	 */
	public String getDeviceStatus() {
		return deviceStatus;
	}
	/**
	 * 设置：设备名称
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	/**
	 * 获取：设备名称
	 */
	public String getDeviceName() {
		return deviceName;
	}
	/**
	 * 设置：巡检类型
	 */
	public void setInspectionType(String inspectionType) {
		this.inspectionType = inspectionType;
	}
	/**
	 * 获取：巡检类型
	 */
	public String getInspectionType() {
		return inspectionType;
	}
	/**
	 * 设置：单位
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * 获取：单位
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * 设置：设备等级
	 */
	public void setDeviceLevel(String deviceLevel) {
		this.deviceLevel = deviceLevel;
	}
	/**
	 * 获取：设备等级
	 */
	public String getDeviceLevel() {
		return deviceLevel;
	}
	/**
	 * 设置：设备等级id
	 */
	public void setDeviceLevelId(Integer deviceLevelId) {
		this.deviceLevelId = deviceLevelId;
	}
	/**
	 * 获取：设备等级
	 */
	public Integer getDeviceLevelId() {
		return deviceLevelId;
	}
	/**
	 * 设置：巡检类型id
	 */
	public void setInspectionTypeId(Integer inspectionTypeId) {
		this.inspectionTypeId = inspectionTypeId;
	}
	/**
	 * 获取：巡检类型id
	 */
	public Integer getInspectionTypeId() {
		return inspectionTypeId;
	}
	/**
	 * 设置：设备编码
	 */
	public void setDeviceCode(String deviceCode) { this.deviceCode = deviceCode; }
	/**
	 * 获取：设备编码
	 */
	public String getDeviceCode() {
		return deviceCode;
	}
	/**
	 * 设置：是否到位
	 */
	public void setIsHere(Integer isHere) {
		this.isHere = isHere;
	}
	/**
	 * 获取：是否到位
	 */
	public Integer getIsHere() {
		return isHere;
	}
	/**
	 * 设置：guid
	 */
	public void setGuid(String guid) { this.guid = guid; }
	/**
	 * 获取：guid
	 */
	public String getGuid() {
		return guid;
	}
	/**
	 * 设置：guid
	 */
	public void setAppResultGuid(String appResultGuid) { this.appResultGuid = appResultGuid; }
	/**
	 * 获取：guid
	 */
	public String getAppResultGuid() {
		return appResultGuid;
	}
	/**
	 * 设置：巡检项guid
	 */
	public void setItemGuid(String itemGuid) { this.itemGuid = itemGuid; }
	/**
	 * 获取：巡检项guid
	 */
	public String getItemGuid() {
		return itemGuid;
	}

	/**
	 * 设置：用户guid
	 */
	public void setUserGuid(String userGuid) { this.userGuid = userGuid; }
	/**
	 * 获取：用户guid
	 */
	public String getUserGuid() {
		return userGuid;
	}

	/**
	 * 设置：轮次guid
	 */
	public void setTurnGuid(String turnGuid) { this.turnGuid = turnGuid; }
	/**
	 * 获取：轮次guid
	 */
	public String getTurnGuid() {
		return turnGuid;
	}

	/**
	 * 设置：是否正常
	 */
	public void setIsOkName(String isOkName) { this.isOkName = isOkName; }
	/**
	 * 获取：是否正常
	 */
	public String getIsOkName() {
		return isOkName;
	}

	/**
	 * 设置：是否到位
	 */
	public void setIsHereName(String isHereName) { this.isHereName = isHereName; }
	/**
	 * 获取：是否到位
	 */
	public String getIsHereName() {
		return isHereName;
	}

	/**
	 * 设置：是否跳过
	 */
	public void setIsCheckName(String isCheckName) { this.isCheckName = isCheckName; }
	/**
	 * 获取：轮次guid
	 */
	public String getIsCheckName() {
		return isCheckName;
	}
	/**
	 * 设置：阀值
	 */
	public void setLimits(String limits) { this.limits = limits; }
	/**
	 * 获取：阀值
	 */
	public String getLimits() {
		return limits;
	}
	/**
	 * 设置：上限
	 */
	public void setUpLimit(Double upLimit) { this.upLimit = upLimit; }
	/**
	 * 获取：上限
	 */
	public Double getUpLimit() {
		return upLimit;
	}
	/**
	 * 设置：上上限
	 */
	public void setUpupLimit(Double upupLimit) { this.upupLimit = upupLimit; }
	/**
	 * 获取：上上限
	 */
	public Double getUpupLimit() {
		return upupLimit;
	}
	/**
	 * 设置：下限
	 */
	public void setDownLimit(Double downLimit) { this.downLimit = downLimit; }
	/**
	 * 获取：下限
	 */
	public Double getDownLimit() {
		return downLimit;
	}
	/**
	 * 设置：下下限
	 */
	public void setDowndownLimit(Double downLimit) { this.downLimit = downLimit; }
	/**
	 * 获取：下下限
	 */
	public Double getDowndownLimit() {
		return downLimit;
	}
	/**
	 * 设置：媒体
	 */
	public void setMedias(List<InspectionResultMediaEntity> medias) { this.medias = medias; }
	/**
	 * 获取：媒体
	 */
	public List<InspectionResultMediaEntity> getMedias() {
		return medias;
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
	 * 设置：缺陷处理状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：缺陷处理状态
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置： 工单确认人
	 */
	public void setOrderConfirmer(String orderConfirmer) {
		this.orderConfirmer = orderConfirmer;
	}
	/**
	 * 获取：工单确认人
	 */
	public String getOrderConfirmer() {
		return orderConfirmer;
	}
	/**
	 * 设置： 工单确认人 id
	 */
	public void setOrderConfirmerId(Integer orderConfirmerId) {
		this.orderConfirmerId = orderConfirmerId;
	}
	/**
	 * 获取：工单确认人 id
	 */
	public Integer getOrderConfirmerId() {
		return orderConfirmerId;
	}
	/**
	 * 设置： 工单确认时间
	 */
	public void setConfirmedTime(Date confirmedTime) {
		this.confirmedTime = confirmedTime;
	}
	/**
	 * 获取：工单确认时间
	 */
	public Date getConfirmedTime() {
		return confirmedTime;
	}
	/**
	 * 设置： 工单状态
	 */
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	/**
	 * 获取：工单状态
	 */
	public Integer getOrderStatus() {
		return orderStatus;
	}
	/**
	 * 设置： 工单状态 名称
	 */
	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}
	/**
	 * 获取：工单状态 名称
	 */
	public String getOrderStatusName() {
		return orderStatusName;
	}
	/**
	 * 设置： 缺陷工单主键id
	 */
	public void setDefectiveId(Integer defectiveId) {
		this.defectiveId = defectiveId;
	}
	/**
	 * 获取：缺陷工单主键id
	 */
	public Integer getDefectiveId() {
		return defectiveId;
	}
	/**
	 * 设置： 缺陷工单编号
	 */
	public void setDefectiveNumber(String defectiveNumber) { this.defectiveNumber = defectiveNumber; }
	/**
	 * 获取：缺陷工单编号
	 */
	public String getDefectiveNumber() { return defectiveNumber;}
	/**
	 * 设置：挂起原因
	 */
	public void setHangUp(String hangUp) { this.hangUp = hangUp; }
	/**
	 * 获取：挂起原因
	 */
	public String getHangUp() { return hangUp; }
}
