package io.renren.modules.inspection.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-01-29 17:34:03
 */
@TableName("tb_inspection_line")
public class InspectionLineEntity implements Serializable {
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
	 * 线路名称
	 */
	private String name;
	/**
	 * 巡检类型
	 */
	private Integer type;
	/**
	 * 巡检类型名称
	 */
	@TableField(exist = false)
	private String typeName;
	/**
	 * 所属机构
	 */
	private Integer deptId;
	/**
	 * 所属机构名称
	 */
	@TableField(exist = false)
	private String deptName;
	/**
	 * 巡检周期
	 */
	private Integer periodType;
	/**
	 * 巡检周期名称
	 */
	@TableField(exist = false)
	private String periodTypeName;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 排序
	 */
	private Integer orderNum;

	/**
	 * 是否发布
	 */
	private Integer isPublish;
	/**
	 * 是否修改
	 */
	private Integer isUpdate;
	/**
	 * 下载状态
	 */
	@TableField(exist = false)
	private String downloadStatus;
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
	public String getGuid() {
		return guid;
	}
	/**
	 * 设置：线路名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：线路名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：巡检类型
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：巡检类型
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：巡检类型
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * 获取：巡检类型
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * 设置：所属机构
	 */
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	/**
	 * 获取：所属机构
	 */
	public Integer getDeptId() {
		return deptId;
	}
	/**
	 * 设置：所属机构名称
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	/**
	 * 获取：所属机构名称
	 */
	public String getDeptName() {
		return deptName;
	}
	/**
	 * 设置：巡检周期
	 */
	public void setPeriodType(Integer periodType) {
		this.periodType = periodType;
	}
	/**
	 * 获取：巡检周期
	 */
	public Integer getPeriodType() {
		return periodType;
	}
	/**
	 * 设置：巡检周期名称
	 */
	public void setPeriodTypeName(String periodTypeName) {
		this.periodTypeName = periodTypeName;
	}
	/**
	 * 获取：巡检周期名称
	 */
	public String getPeriodTypeName() {
		return periodTypeName;
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
	 * 设置：修改时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：修改时间
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
	 * 设置：是否发布
	 */
	public void setIsPublish(Integer isPublish) {
		this.isPublish = isPublish;
	}
	/**
	 * 获取：是否发布
	 */
	public Integer getIsPublish() {
		return isPublish;
	}
	/**
	 * 设置：是否修改
	 */
	public void setIsUpdate(Integer isUpdate) {
		this.isUpdate = isUpdate;
	}
	/**
	 * 获取：是否修改
	 */
	public Integer getIsUpdate() {
		return isUpdate;
	}
	/**
	 * 设置：下载状态
	 */
	public void setDownloadStatus(String downloadStatus) {
		this.downloadStatus = downloadStatus;
	}
	/**
	 * 获取：下载状态
	 */
	public String getDownloadStatus() {
		return downloadStatus;
	}

}
