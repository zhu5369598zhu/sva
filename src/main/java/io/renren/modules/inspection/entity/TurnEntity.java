package io.renren.modules.inspection.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * 
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-01-29 17:34:03
 */
@TableName("tb_turn")
public class TurnEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Long id;
	/**
	 * guid
	 */
	private String guid;
	/**
	 * 所属线路ID
	 */
	private Long inspectionLineId;
	/**
	 * 轮次
	 */
	private String name;
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
	 * 班组集合
	 */
	@TableField(exist = false)
	private List<Long> classGroupList;
	/**
	 * 班组名称集合
	 */
	@TableField(exist = false)
	private List<String> classGroupNameList;
	/**
	 * 班组名称集合
	 */
	@TableField(exist = false)
	private String classGroupNames;
	/**
	 * 设置：ID
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：ID
	 */
	public Long getId() {
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
	 * 设置：所属线路ID
	 */
	public void setInspectionLineId(Long inspectionLineId) {
		this.inspectionLineId = inspectionLineId;
	}
	/**
	 * 获取：所属线路ID
	 */
	public Long getInspectionLineId() {
		return inspectionLineId;
	}
	/**
	 * 设置：轮次
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：轮次
	 */
	public String getName() {
		return name;
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
	 * 设置：班组集合
	 */
	public void setClassGroupList(List<Long> classGroupList) {
		this.classGroupList = classGroupList;
	}
	/**
	 * 获取：班组集合
	 */
	public List<Long> getClassGroupList() {
		return classGroupList;
	}
	/**
	 * 设置：班组名称集合
	 */
	public void setClassGroupNameList(List<String> classGroupNameList) {
		this.classGroupNameList = classGroupNameList;
	}
	/**
	 * 获取：班组名称集合
	 */
	public List<String> getClassGroupNameList() {
		return classGroupNameList;
	}
	/**
	 * 设置：班组名称集合
	 */
	public void setClassGroupNames(String classGroupNames) {
		this.classGroupNames = classGroupNames;
	}
	/**
	 * 获取：班组名称集合
	 */
	public String getClassGroupNames() {
		return classGroupNames;
	}
}
