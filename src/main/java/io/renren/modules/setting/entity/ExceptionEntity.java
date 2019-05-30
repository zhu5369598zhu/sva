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
@TableName("tb_exception")
public class ExceptionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 异常ID
	 */
	@TableId
	private Integer id;
	/**
	 * 异常名称
	 */
	private String name;
	/**
	 * 排序
	 */
	private Integer orderNum;

	/**
	 * 设置：异常ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：异常ID
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：异常名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：异常名称
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
