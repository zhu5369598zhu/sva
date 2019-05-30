package io.renren.modules.setting.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-01-21 19:32:48
 */
@TableName("tb_device_level")
public class DeviceLevelEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 设备等级ID
	 */
	@TableId
	private Integer id;
	/**
	 * 等级
	 */
	private String name;
	/**
	 * 排序
	 */
	private Integer orderNum;

	/**
	 * 设置：设备等级ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：设备等级ID
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：等级
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：等级
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
