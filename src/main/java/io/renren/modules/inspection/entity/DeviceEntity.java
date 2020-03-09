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
@TableName("tb_device")
public class DeviceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 设备id
	 */
	@TableId
	private Integer deviceId;
	/**
	 * guid
	 */
	private String guid;
	/**
	 * 设备名称
	 */
	private String deviceName;
	/**
	 * 设备编号
	 */
	private String deviceNum;
	/**
	 * 设备编码
	 */
	private String deviceCode;
	/**
	 * 设备规格
	 */
	private String deviceUnit;
	/**
	 * 生产厂家
	 */
	private String deviceManufacturer;
	/**
	 * 供货商
	 */
	private String deviceSupplier;
	/**
	 * 设备等级
	 */
	private Integer deviceLevel;
	/**
	 * 巡检时设备状态
	 */
	private Integer deviceStatus;
	/**
	 * 设备等级
	 */
	@TableField(exist = false)
	private String deviceLevelName;
	/**
	 * 所属机构
	 */
	private Long deviceDept;
	/**
	 * 所属机构
	 */
	@TableField(exist = false)
	private String deptName;
	/**
	 * 设备模型
	 */
	private String deviceModel;
	/**
	 * 责任人
	 */
	private String deviceMaster;
	/**
	 * 特征1
	 */
	private String featureOne;
	/**
	 * 特征1内容
	 */
	private String oneContent;
	/**
	 * 特征2
	 */
	private String featureTwo;
	/**
	 * 特征2内容
	 */
	private String twoContent;
	/**
	 * 特征3
	 */
	private String featureThree;
	/**
	 * 特征3内容
	 */
	private String threeContent;
	/**
	 * 特征4
	 */
    private String featureFour;
	/**
	 * 特征4内容
	 */
    private String fourContent;
	/**
	 * 是否巡检
	 */
	private Integer isInspect;
	/**
	 * 是否巡检
	 */
	@TableField(exist = false)
	private String isInspectName;
	/**
	 * 是否在看板显示
	 */
	private Integer isShowDashboard;
	/**
	 * 是否在看板显示
	 */
	@TableField(exist = false)
	private String isShowDashboardName;
	/**
	 * 生产日期
	 */
	private Date manufactureDate;
	/**
	 * 所属巡区
	 */
	@TableField(exist = false)
	private String zones;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 是否删除
	 */
	private Integer isDelete;

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
	 * 设置：设备编号
	 */
	public void setDeviceNum(String deviceNum) {
		this.deviceNum = deviceNum;
	}
	/**
	 * 获取：设备编号
	 */
	public String getDeviceNum() {
		return deviceNum;
	}
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
	 * 设置：设备规格
	 */
	public void setDeviceUnit(String deviceUnit) {
		this.deviceUnit = deviceUnit;
	}
	/**
	 * 获取：设备规格
	 */
	public String getDeviceUnit() {
		return deviceUnit;
	}
	/**
	 * 设置：生产厂家
	 */
	public void setDeviceManufacturer(String deviceManufacturer) {
		this.deviceManufacturer = deviceManufacturer;
	}
	/**
	 * 获取：生产厂家
	 */
	public String getDeviceManufacturer() {
		return deviceManufacturer;
	}
	/**
	 * 设置：供货商
	 */
	public void setDeviceSupplier(String deviceSupplier) {
		this.deviceSupplier = deviceSupplier;
	}
	/**
	 * 获取：供货商
	 */
	public String getDeviceSupplier() {
		return deviceSupplier;
	}
	/**
	 * 设置：设备等级
	 */
	public void setDeviceLevel(Integer deviceLevel) {
		this.deviceLevel = deviceLevel;
	}
	/**
	 * 获取：设备等级
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
	public void setDeviceDept(Long deviceDept) {
		this.deviceDept = deviceDept;
	}
	/**
	 * 获取：所属机构
	 */
	public Long getDeviceDept() {
		return deviceDept;
	}
	/**
	 * 设置：所属机构
	 */
	public void setDeptName(String deptName) { this.deptName = deptName; }
	/**
	 * 获取：所属机构
	 */
	public String getDeptName() { return deptName; }
	/**
	 * 设置：设备模型
	 */
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	/**
	 * 获取：设备模型
	 */
	public String getDeviceModel() {
		return deviceModel;
	}
	/**
	 * 设置：责任人
	 */
	public void setDeviceMaster(String deviceMaster) {
		this.deviceMaster = deviceMaster;
	}
	/**
	 * 获取：责任人
	 */
	public String getDeviceMaster() {
		return deviceMaster;
	}
	/**
	 * 设置：特征1
	 */
	public void setFeatureOne(String featureOne) {
		this.featureOne = featureOne;
	}
	/**
	 * 获取：特征1
	 */
	public String getFeatureOne() {
		return featureOne;
	}
	/**
	 * 设置：特征1内容
	 */
	public void setOneContent(String oneContent) {
		this.oneContent = oneContent;
	}
	/**
	 * 获取：特征1内容
	 */
	public String getOneContent() {
		return oneContent;
	}
	/**
	 * 设置：特征2
	 */
	public void setFeatureTwo(String featureTwo) {
		this.featureTwo = featureTwo;
	}
	/**
	 * 获取：特征2
	 */
	public String getFeatureTwo() {
		return featureTwo;
	}
	/**
	 * 设置：特征2内容
	 */
	public void setTwoContent(String twoContent) {
		this.twoContent = twoContent;
	}
	/**
	 * 获取：特征2内容
	 */
	public String getTwoContent() {
		return twoContent;
	}
	/**
	 * 设置：特征3
	 */
	public void setFeatureThree(String featureThree) {
		this.featureThree = featureThree;
	}
	/**
	 * 获取：特征3
	 */
	public String getFeatureThree() {
		return featureThree;
	}
	/**
	 * 设置：特征3内容
	 */
	public void setThreeContent(String threeContent) {
		this.threeContent = threeContent;
	}
	/**
	 * 获取：特征3内容
	 */
	public String getThreeContent() {
		return threeContent;
	}
	/**
	 * 设置：特征4
	 */
	public void setFeatureFour(String featureFour) {
		this.featureFour = featureFour;
	}
	/**
	 * 获取：特征3
	 */
	public String getFeatureFour() {
		return featureFour;
	}
	/**
	 * 设置：特征4内容
	 */
	public void setFourContent(String fourContent) {
		this.fourContent = fourContent;
	}
	/**
	 * 获取：特征4内容
	 */
	public String getFourContent() {
		return fourContent;
	}
	/**
	 * 设置：是否巡检
	 */
	public void setIsInspect(Integer isInspect) {
		this.isInspect = isInspect;
	}
	/**
	 * 获取：是否巡检
	 */
	public Integer getIsInspect() {
		return isInspect;
	}
	/**
	 * 设置：是否在看板显示
	 */
	public void setIsShowDashboard(Integer isShowDashboard) {
		this.isShowDashboard = isShowDashboard;
	}
	/**
	 * 获取：是否在看板显示
	 */
	public Integer getIsShowDashboard() {
		return isShowDashboard;
	}
	/**
	 * 设置：生产日期
	 */
	public void setManufactureDate(Date manufactureDate) {
		this.manufactureDate = manufactureDate;
	}
	/**
	 * 获取：生产日期
	 */
	public Date getManufactureDate() {
		return manufactureDate;
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

	/**
	 *
	 * 获取: 是否巡检
	 */
	public String getIsInspectName() {
		return isInspectName;
	}
	/**
	 *
	 * 设置: 是否巡检
	 */
	public void setIsInspectName(String isInspectName) {
		this.isInspectName = isInspectName;
	}
	/**
	 *
	 * 获取: 是否看板显示
	 */
	public String getIsShowDashboardName() {
		return isShowDashboardName;
	}
	/**
	 *
	 * 设置: 是否看板显示
	 */
	public void setIsShowDashboardName(String isShowDashboardName) {
		this.isShowDashboardName = isShowDashboardName;
	}
	/**
	 *
	 * 获取: 所属巡区
	 */
	public String getZones() {
		return zones;
	}
	/**
	 *
	 * 设置: 所属巡区
	 */
	public void setZones(String zones) {
		this.zones = zones;
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
	/**
	 * 获取：设备巡检时设备状态
	 */
	public Integer getDeviceStatus() {
		return deviceStatus;
	}
	/**
	 * 设置：设备巡检时设备状态
	 */
	public void setDeviceStatus(Integer deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
}
