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
@TableName("tb_zone")
public class ZoneEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 巡区id
	 */
	@TableId
	private Integer zoneId;
	/**
	 * guid
	 */
	private String guid;
	/**
	 * 部门ID
	 */
	private Long deptId;
	/**
	 * 部门名称
	 */
	@TableField(exist=false)
	private String deptName;
	/**
	 * 巡区名称
	 */
	private String zoneName;
	/**
	 * 巡区编码
	 */
	private String zoneCode;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 排序
	 */
	private Integer orderNum;

	/**
	 * 是否删除
	 */
	private Integer isDelete;


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
	 * 设置：部门ID
	 */
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	/**
	 * 获取：部门ID
	 */
	public Long getDeptId() {
		return deptId;
	}
	/**
	 * 获取：部门名称
	 */
	public String getDeptName() { return deptName; }
	/**
	 * 设置：部门名称
	 */
	public void setDeptName(String deptName) { this.deptName = deptName; }
	/**
	 * 设置：巡区名称
	 */
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	/**
	 * 获取：巡区名称
	 */
	public String getZoneName() {
		return zoneName;
	}
	/**
	 * 设置：巡区编码
	 */
	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}
	/**
	 * 获取：巡区编码
	 */
	public String getZoneCode() {
		return zoneCode;
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
