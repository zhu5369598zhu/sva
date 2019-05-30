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
@TableName("tb_device_pic")
public class DevicePicEntity implements Serializable {
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
	 * 图片数据
	 */
	private byte[] data;
	/**
	 * 上传时间
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
	 * 设置：图片数据
	 */
	public void setData(byte[] data) {
		this.data = data;
	}
	/**
	 * 获取：图片数据
	 */
	public byte[] getData() {
		return data;
	}
	/**
	 * 设置：上传时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：上传时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
}
