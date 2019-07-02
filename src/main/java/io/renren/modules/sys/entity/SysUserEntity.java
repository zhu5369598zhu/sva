package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:28:55
 */
@TableName("sys_user")
public class SysUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户ID
	 */
	@TableId
	private Long userId;
	/**
	 * guid
	 */
	private String guid;
	/**
	 * 用户名
	 */
	@NotBlank(message="用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String username;

	/**
	 * 密码
	 */
	@NotBlank(message="密码不能为空", groups = AddGroup.class)
	private String password;

	/**
	 * 盐
	 */
	private String salt;

	/**
	 * 邮箱
	 */
	@Email(message="邮箱格式不正确", groups = {AddGroup.class, UpdateGroup.class})
	private String email;

	/**
	 * 手机号
	 */
	private String mobile;

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
	 * 工号
	 */
	private String userCode;

	/**
	 * 企业微信号
	 */
	private String wechat;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 状态  0：禁用   1：正常
	 */
	private Integer status;
	/**
	 * 是否删除
	 */
	private Integer isDelete;

	@TableField(exist = false)
	private String statusName;

	/**
	 * 角色ID列表
	 */
	@TableField(exist=false)
	private List<Long> roleIdList;
	
	/**
	 * 创建者ID
	 */
	private Long createUserId;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 设置：
	 * @param userId 
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 获取：
	 * @return Long
	 */
	public Long getUserId() {
		return userId;
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
	 * 设置：用户名
	 * @param username 用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取：用户名
	 * @return String
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * 设置：密码
	 * @param password 密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取：密码
	 * @return String
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * 设置：邮箱
	 * @param email 邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取：邮箱
	 * @return String
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * 设置：手机号
	 * @param mobile 手机号
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 获取：手机号
	 * @return String
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 设置：部门ID
	 */
	public void setDeptId(Long deptId) { this.deptId = deptId; }

	/**
	 * 获取：部门ID
	 */
	public Long getDeptId() { return deptId; }

	/**
	 * 获取：部门名称
	 */
	public String getDeptName() { return deptName; }

	/**
	 * 设置：部门名称
	 */
	public void setDeptName(String deptName) { this.deptName = deptName; }

	/**
	 * 获取：工号
	 */
	public String getUserCode() { return userCode; }

	/**
	 * 设置：工号
	 */
	public void setUserCode(String userCode) { this.userCode = userCode; }


	/**
	 * 获取：备注
	 */
	public String getRemark() { return remark; }

	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) { this.remark = remark; }


	/**
	 * 获取：企业微信号
	 */
	public String getWechat() { return wechat; }

	/**
	 * 设置：部门名称
	 */
	public void setWechat(String wechat) { this.wechat = wechat; }

	/**
	 * 设置：状态  0：禁用   1：正常
	 * @param status 状态  0：禁用   1：正常
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取：状态  0：禁用   1：正常
	 * @return Integer
	 */
	public Integer getStatus() {
		return status;
	}
	
	/**
	 * 设置：创建时间
	 * @param createTime 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取：创建时间
	 * @return Date
	 */
	public Date getCreateTime() {
		return createTime;
	}

	public List<Long> getRoleIdList() {
		return roleIdList;
	}

	public void setRoleIdList(List<Long> roleIdList) {
		this.roleIdList = roleIdList;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
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
