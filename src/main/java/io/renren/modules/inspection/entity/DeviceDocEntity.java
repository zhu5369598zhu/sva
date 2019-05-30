package io.renren.modules.inspection.entity;

import com.baomidou.mybatisplus.annotations.TableField;
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
@TableName("tb_device_doc")
public class DeviceDocEntity implements Serializable {
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
	 * 设备id
	 */
	private Integer deviceId;
	/**
	 * 0出长资料，1调试资料，2保养资料，3维修资料
	 */
	private Integer category;
	/**
	 * 文档内容
	 */
	private byte[] data;
	/**
	 * 文档名称
	 */
	private String filename;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 用户名
	 */
	@TableField(exist = false)
	private String userName;
	/**
	 * 创建时间
	 */
	private Date createTime;

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
	 * 设置：0出长资料，1调试资料，2保养资料，3维修资料
	 */
	public void setCategory(Integer category) {
		this.category = category;
	}
	/**
	 * 获取：0出长资料，1调试资料，2保养资料，3维修资料
	 */
	public Integer getCategory() {
		return category;
	}
	/**
	 * 设置：文档内容
	 */
	public void setData(byte[] data) {
		this.data = data;
	}
	/**
	 * 获取：文档内容
	 */
	public byte[] getData() {
		return data;
	}
	/**
	 * 设置：文档名称
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
	/**
	 * 获取：文档名称
	 */
	public String getFilename() {
		return filename;
	}
	/**
	 * 设置：用户id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 设置：用户名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取：用户名
	 */
	public String getUserName() {
		return userName;
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
}
