package io.renren.modules.setting.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-01-22 11:36:34
 */
@TableName("tb_device_status")
public class DeviceStatusEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 设备状态ID
	 */
	@TableId
	private Integer id;
	/**
	 * guid
	 */
	private String guid;
	/**
	 * 状态名称
	 */
	private String name;
	/**
	 * 排序
	 */
	private Integer orderNum;

	/**
	 * 设置：设备状态ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：设备状态ID
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
	 * 设置：状态名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：状态名称
	 */
	public String getName() {
		return name;
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
}
