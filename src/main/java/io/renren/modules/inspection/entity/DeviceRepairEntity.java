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
 * @date 2019-04-26 12:20:16
 */
@TableName("tb_device_repair")
public class DeviceRepairEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer id;
	/**
	 * 维护时间
	 */
	private String repairTime;
	/**
	 * 维护描述
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	private Integer createTime;

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
	 * 设置：维护时间
	 */
	public void setRepairTime(String repairTime) {
		this.repairTime = repairTime;
	}
	/**
	 * 获取：维护时间
	 */
	public String getRepairTime() {
		return repairTime;
	}
	/**
	 * 设置：维护描述
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：维护描述
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Integer getCreateTime() {
		return createTime;
	}
}
