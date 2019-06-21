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
 * @date 2019-01-21 03:10:49
 */
@TableName("tb_pda")
public class PdaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 设备ID
	 */
	@TableId
	private Integer pdaId;
	/**
	 * guid
	 */
	private String guid;
	/**
	 * 部门ID
	 */
	private Integer deptId;
	/**
	 * 部门名称
	 */
	@TableField(exist=false)
	private String deptName;
	/**
	 * 设备名称
	 */
	private String pdaName;
	/**
	 * MAC地址
	 */
	private String pdaMac;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 是否删除
	 */
	private Integer isDelete;
	/**
	 * 设置：设备ID
	 */
	public void setPdaId(Integer pdaId) {
		this.pdaId = pdaId;
	}
	/**
	 * 获取：设备ID
	 */
	public Integer getPdaId() {
		return pdaId;
	}
	/**
	 * 设置：部门ID
	 */
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	/**
	 * 获取：部门ID
	 */
	public Integer getDeptId() {
		return deptId;
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
	 * 获取：部门名称
	 */
	public String getDeptName() { return deptName; }

	/**
	 * 设置：部门名称
	 */
	public void setDeptName(String deptName) { this.deptName = deptName; }
	/**
	 * 设置：设备名称
	 */
	public void setPdaName(String pdaName) {
		this.pdaName = pdaName;
	}
	/**
	 * 获取：设备名称
	 */
	public String getPdaName() {
		return pdaName;
	}
	/**
	 * 设置：MAC地址
	 */
	public void setPdaMac(String pdaMac) {
		this.pdaMac = pdaMac;
	}
	/**
	 * 获取：MAC地址
	 */
	public String getPdaMac() {
		return pdaMac;
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
