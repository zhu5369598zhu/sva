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
 * @date 2019-01-22 17:33:37
 */
@TableName("tb_inspection_item")
public class InspectionItemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Integer id;
	/**
	 * guid
	 */
	private String guid;
	/**
	 * 所属设备
	 */
	private Integer deviceId;
	/**
	 * 所属设备名称
	 */
	@TableField(exist = false)
	private String deviceName;
	/**
	 * 所属设备编码
	 */
	@TableField(exist = false)
	private String deviceCode;
	/**
	 * 巡检类型
	 */
	private Integer inspectionType;
	/**
	 * 巡检类型名称
	 */
	@TableField(exist = false)
	private String inspectionTypeName;
	/**
	 * 巡检类型单位
	 */
	@TableField(exist = false)
	private String inspectionTypeUnit;
	/**
	 * 巡检项
	 */
	private String name;
	/**
	 * 检时状态
	 */
	private Integer inspectionStatus;
	/**
	 * 检时状态名称
	 */
	@TableField(exist = false)
	private String inspectionStatusName;
	/**
	 * 单位
	 */
	private String unit;
	/**
	 * 发射率
	 */
	private Double emissivity;
	/**
	 * 采样频率
	 */
	private Integer frequency;
	/**
	 * 采样频率名称
	 */
	@TableField(exist = false)
	private String frequencyName;
	/**
	 * 采样点数
	 */
	private Integer precision;
	/**
	 * 采样点数名称
	 */
	@TableField(exist = false)
	private String precisionName;
	/**
	 * 上上限
	 */
	private Double upupLimit;
	/**
	 * 是否启用上上限
	 */
	private Integer upupUsed;
	/**
	 * 上限
	 */
	private Double upLimit;
	/**
	 * 是否启用上限
	 */
	private Integer upUsed;
	/**
	 * 下下限
	 */
	private Double downdownLimit;
	/**
	 * 是否启用下下限
	 */
	private Integer downdownUsed;
	/**
	 * 下限
	 */
	private Double downLimit;
	/**
	 * 是否启用下限
	 */
	private Integer downUsed;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 扩展
	 */
	@TableField(exist = false)
	private List<InspectionItemExtraEntity> extraList;
	/**
	 * 扩展
	 */
	@TableField(exist = false)
	private String extras;
	/**
	 * 扩展异常
	 */
	@TableField(exist = false)
	private String extraExceptions;
	/**
	 * 预设
	 */
	@TableField(exist = false)
	private List<InspectionItemPresuppositionEntity> presuppositionList;
	/**
	 * 预设
	 */
	@TableField(exist = false)
	private String presuppositions;
	/**
	 * 是否巡检
	 */
	private Integer isCheck;
	/**
	 * 默认转速
	 */
	private Integer defaultRpm;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 排序
	 */
	private Integer orderNum;

	/**
	 * 是否删除
	 */
	private Integer isDelete;


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
	 * 设置：guid
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}
	/**
	 * 获取：guid
	 */
	public String getGuid() { return guid; }
	/**
	 * 设置：所属设备
	 */
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	/**
	 * 获取：所属设备
	 */
	public Integer getDeviceId() {
		return deviceId;
	}
	/**
	 * 设置：所属设备名称
	 */
	public void setDeviceName(String deviceName) { this.deviceName = deviceName; }
	/**
	 * 获取：所属设备名称
	 */
	public String getDeviceName() {
		return deviceName;
	}
	/**
	 * 设置：所属设备编码
	 */
	public void setDeviceCode(String deviceCode) { this.deviceCode = deviceCode; }
	/**
	 * 获取：所属设备编码
	 */
	public String getDeviceCode() {
		return deviceCode;
	}
	/**
	 * 设置：巡检类型
	 */
	public void setInspectionType(Integer inspectionType) {
		this.inspectionType = inspectionType;
	}
	/**
	 * 获取：巡检类型
	 */
	public Integer getInspectionType() {
		return inspectionType;
	}
	/**
	 * 获取：巡检类型名称
	 */
	public String getInspectionTypeName() {
		return inspectionTypeName;
	}
	/**
	 * 设置：巡检类型名称
	 */
	public void setInspectionTypeName(String inspectionTypeName) { this.inspectionTypeName = inspectionTypeName; }
	/**
	 * 获取：巡检类型单位
	 */
	public String getInspectionTypeUnit() {
		return inspectionTypeUnit;
	}
	/**
	 * 设置：巡检类型单位
	 */
	public void setInspectionTypeUnit(String inspectionTypeUnit) { this.inspectionTypeUnit = inspectionTypeUnit; }
	/**
	 * 设置：巡检项
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：巡检项
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：检时状态
	 */
	public void setInspectionStatus(Integer inspectionStatus) {
		this.inspectionStatus = inspectionStatus;
	}
	/**
	 * 获取：检时状态
	 */
	public Integer getInspectionStatus() {
		return inspectionStatus;
	}
	/**
	 * 设置：巡检状态名称
	 */
	public void setInspectionStatusName(String inspectionStatusName) { this.inspectionStatusName = inspectionStatusName; }
	/**
	 * 获取：巡检状态名称
	 */
	public String getInspectionStatusName() {
		return inspectionStatusName;
	}
	/**
	 * 设置：发射率
	 */
	public void setEmissivity(Double emissivity) {
		this.emissivity = emissivity;
	}
	/**
	 * 获取：发射率
	 */
	public Double getEmissivity() {
		return emissivity;
	}
	/**
	 * 设置：采样频率
	 */
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	/**
	 * 获取：采样频率
	 */
	public Integer getFrequency() {
		return frequency;
	}
	/**
	 * 设置：采样频率名称
	 */
	public void setFrequencyName(String frequencyName) { this.frequencyName = frequencyName; }
	/**
	 * 获取：采样频率名称
	 */
	public String getFrequencyName() {
		return frequencyName;
	}
	/**
	 * 设置：采样点数
	 */
	public void setPrecision(Integer precision) {
		this.precision = precision;
	}
	/**
	 * 获取：采样点数
	 */
	public Integer getPrecision() {
		return precision;
	}
	/**
	 * 设置：采样点数名称
	 */
	public void setPrecisionName(String precisionName) { this.precisionName = precisionName; }
	/**
	 * 获取：采样点数名称
	 */
	public String getPrecisionName() {
		return precisionName;
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
	 * 设置：下下限
	 */
	public void setDowndownLimit(Double downdownLimit) {
		this.downdownLimit = downdownLimit;
	}
	/**
	 * 获取：下下限
	 */
	public Double getDowndownLimit() {
		return downdownLimit;
	}
	/**
	 * 设置：下限
	 */
	public void setDownLimit(Double downLimit) {
		this.downLimit = downLimit;
	}
	/**
	 * 获取：下限
	 */
	public Double getDownLimit() {
		return downLimit;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() { return remark; }
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) { this.remark = remark; }
	/**
	 * 获取：扩展
	 */
	public List<InspectionItemExtraEntity> getExtraList() { return this.extraList; };
	/**
	 * 设置：扩展
	 */
	public void setExtraList(List<InspectionItemExtraEntity> extraList) { this.extraList = extraList; }
	/**
	 * 设置：是否巡检
	 */
	public Integer getIsCheck() { return isCheck; };
	/**
	 * 获取：是否巡检
	 */
	public void setIsCheck(Integer isCheck) { this.isCheck = isCheck; }
	/**
	 * 设置：默认转速
	 */
	public Integer getDefaultRpm() { return defaultRpm; };
	/**
	 * 设置：默认转速
	 */
	public void setDefaultRpm(Integer defaultRpm) { this.defaultRpm = defaultRpm; };
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
	/**
	 * 设置：更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：排序
	 */
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	/**
	 * 获取：排序
	 */
	public Integer getOrderNum() {
		return orderNum;
	}

	/**
	 * 获取：单位
	 */
	public String getUnit() { return unit; }

	/**
	 * 设置：单位
	 */
	public void setUnit(String unit){ this.unit = unit; }
	/**
	 * 获取：扩展
	 */
	public String getExtras() { return extras; }

	/**
	 * 设置：扩展
	 */
	public void setExtras(String extras){ this.extras = extras; }

	/**
	 * 获取：预设
	 */
	public String getPresuppositions() { return presuppositions; }

	/**
	 * 设置：预设
	 */
	public void setPresuppositions(String presuppositions){ this.presuppositions = presuppositions; }
	/**
	 * 获取：扩展异常
	 */
	public String getExtraExceptions() { return extraExceptions; }

	/**
	 * 设置：预设
	 */
	public void setExtraExceptions(String extraExceptions){ this.extraExceptions = extraExceptions; }

	/**
	 * 获取：预设
	 */
	public List<InspectionItemPresuppositionEntity> getPresuppositionList() { return presuppositionList; }

	/**
	 * 设置：预设
	 */
	public void setPresuppositionList(List<InspectionItemPresuppositionEntity> presuppositionList){ this.presuppositionList = presuppositionList; }

	public Integer getUpupUsed() {
		return upupUsed;
	}

	public Integer getUpUsed() {
		return upUsed;
	}

	public Integer getDowndownUsed() {
		return downdownUsed;
	}

	public Integer getDownUsed() {
		return downUsed;
	}

	public void setUpupUsed(Integer upupUsed) {
		this.upupUsed = upupUsed;
	}

	public void setUpUsed(Integer upUsed) {
		this.upUsed = upUsed;
	}

	public void setDowndownUsed(Integer downdownUsed) {
		this.downdownUsed = downdownUsed;
	}

	public void setDownUsed(Integer downUsed) {
		this.downUsed = downUsed;
	}


	/**
	 * 设置：是否删除
	 */
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	/**
	 * 获取：是否删除
	 */
	public Integer getIsDelete() {
		return isDelete;
	}
}
