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
@TableName("tb_sampling_frequency")
public class SamplingFrequencyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 采样频率ID
	 */
	@TableId
	private Integer id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 排序
	 */
	private Integer orderNum;

	/**
	 * 设置：采样频率ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：采样频率ID
	 */
	public Integer getId() {
		return id;
	}
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
