package io.renren.modules.inspection.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-01-29 17:34:03
 */
@TableName("tb_class_group")
public class ClassGroupEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
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
	 * 名称
	 */
	private String name;
	/**
	 * 用户集合
	 */
	@TableField(exist = false)
	private List<Long> workerList;
	/**
	 * 用户名集合
	 */
	@TableField(exist = false)
	private List<String> workerNameList;
	/**
	 * 用户名集合
	 */
	@TableField(exist = false)
	private String workerNames;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 设置：id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：id
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
	public Long getInspectionLineId() { return inspectionLineId; }
	/**
	 * 设置：名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：用户
	 */
	public void setWorkerList(List<Long> workerList) {
		this.workerList = workerList;
	}
	/**
	 * 获取：用户
	 */
	public List<Long> getWorkerList() { return workerList; }
	/**
	 * 设置：用户名集合
	 */
	public void setWorkerNames(String workerNames) {
		this.workerNames = workerNames;
	}
	/**
	 * 获取：用户名集合
	 */
	public String getWorkerNames() { return workerNames; }
	/**
	 * 设置：用户名集合
	 */
	public void setWorkerNameList(List<String> workerNameList) {
		this.workerNameList = workerNameList;
	}
	/**
	 * 获取：用户名集合
	 */
	public List<String> getWorkerNameList() { return workerNameList; }
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
}
