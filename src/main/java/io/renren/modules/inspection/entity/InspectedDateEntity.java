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
 * @date 2019-12-04 11:35:22
 */
@TableName("tb_inspected_date")
public class InspectedDateEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private Integer id;
	/**
	 * 巡检人员
	 */
	private Integer userId;
	/**
	 * 上传的巡检时间
	 */
	private Date inspectionDate;

	/**
	 * 设置：主键id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：主键id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：巡检人员
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 获取：巡检人员
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * 设置：上传的巡检时间
	 */
	public void setInspectionDate(Date inspectionDate) {
		this.inspectionDate = inspectionDate;
	}
	/**
	 * 获取：上传的巡检时间
	 */
	public Date getInspectionDate() {
		return inspectionDate;
	}
}
