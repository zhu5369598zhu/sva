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
 * @date 2019-07-02 15:22:39
 */
@TableName("tb_order_exception")
public class OrderExceptionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 缺陷异常id
	 */
	@TableId
	private Integer id;
	/**
	 * 缺陷异常名称
	 */
	private String name;
	/**
	 * 排序
	 */
	private Integer orderNum;

	/**
	 * 设置：缺陷异常id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：缺陷异常id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：缺陷异常名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：缺陷异常名称
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
