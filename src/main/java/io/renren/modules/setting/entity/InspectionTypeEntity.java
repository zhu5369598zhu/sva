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
@TableName("tb_inspection_type")
public class InspectionTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 巡检类型ID
	 */
	@TableId
	private Integer id;
	/**
	 * 类型名称
	 */
	private String name;
	/**
	 * 类型单位
	 */
	private String unit;
	/**
	 * 排序
	 */
	private Integer orderNum;

	/**
	 * 设置：巡检类型ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：巡检类型ID
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：类型名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：类型名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：类型单位
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * 获取：类型单位
	 */
	public String getUnit() {
		return unit;
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
