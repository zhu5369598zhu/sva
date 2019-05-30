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
 * @date 2019-03-15 11:43:31
 */
@TableName("tb_inspection_random_result")
public class InspectionRandomResultEntity implements Serializable {
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
	 * 巡检项名称
	 */
	private String name;
	/**
	 * 车间名
	 */
	private String workshop;
	/**
	 * 工厂名
	 */
	private String factory;
	/**
	 * 部门
	 */
	private String dept;
	/**
	 * 设备名称
	 */
	private String deviceName;
	/**
	 * 设备编号
	 */
	private String deviceCode;
	/**
	 * 巡检类型
	 */
	private Integer inspectionTypeId;
	/**
	 * 巡检类型名称
	 */
	private String inspectionType;
	/**
	 * 单位
	 */
	@TableField(exist = false)
	private String unit;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 用户名
	 */
	@TableField(exist = false)
	private String userName;
	/**
	 * 用户guid
	 */
	@TableField(exist = false)
	private String userGuid;
	/**
	 * 巡检结果
	 */
	private String result;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 附件
	 */
	@TableField(exist = false)
	private List<InspectionRandomResultMediaEntity> medias;
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
	 * 设置：guid
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}
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
	 * 设置：车间名
	 */
	public void setWorkshop(String workshop) {
		this.workshop = workshop;
	}
	/**
	 * 获取：车间名
	 */
	public String getWorkshop() {
		return workshop;
	}
	/**
	 * 设置：工厂名
	 */
	public void setFactory(String factory) {
		this.factory = factory;
	}
	/**
	 * 获取：工厂名
	 */
	public String getFactory() {
		return factory;
	}
	/**
	 * 设置：部门
	 */
	public void setDept(String dept) {
		this.dept = dept;
	}
	/**
	 * 获取：部门
	 */
	public String getDept() {
		return dept;
	}
	/**
	 * 设置：巡检类型
	 */
	public void setInspectionTypeId(Integer inspectionTypeId) {
		this.inspectionTypeId = inspectionTypeId;
	}
	/**
	 * 获取：巡检类型
	 */
	public Integer getInspectionTypeId() {
		return inspectionTypeId;
	}
	/**
	 * 设置：巡检类型名称
	 */
	public void setInspectionType(String inspectionType) {
		this.inspectionType = inspectionType;
	}
	/**
	 * 获取：巡检类型名称
	 */
	public String getInspectionType() {
		return this.inspectionType;
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
	 * 设置：用户id
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * 设置：用户名
	 */
	public void setUsername(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取：用户名
	 */
	public String getUsername() {
		return userName;
	}
	/**
	 * 设置：用户guid
	 */
	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}
	/**
	 * 获取：用户guid
	 */
	public String getUserGuid() {
		return userGuid;
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
	 * 设置：开始时间
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取：开始时间
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * 设置：结束时间
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取：结束时间
	 */
	public String getEndTime() {
		return endTime;
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
	 * 设置：巡检项名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：巡检项名称
	 */
	public String getName() {
		return name;
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
	 * 设置：附件
	 */
	public void setMedias(List<InspectionRandomResultMediaEntity> medias) {
		this.medias = medias;
	}
	/**
	 * 获取：附件
	 */
	public List<InspectionRandomResultMediaEntity> getMedias() {
		return medias;
	}
}
