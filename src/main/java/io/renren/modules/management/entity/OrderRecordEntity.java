package io.renren.modules.management.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 工单操作记录
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-05-27 15:40:37
 */
@TableName("tb_order_record")
public class OrderRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 工单日志记录id
	 */
	@TableId
	private Integer recordId;
	/**
	 * 工单编号
	 */
	private String orderNumber;
	/**
	 * 缺陷工单id 
	 */
	private Integer defectiveId;
	/**
	 * 缺陷工单编号
	 */
	private String defectiveNumber;
	/**
	 * 角色的名字
	 */
	private String orderPeople;
	/**
	 * 意见（结论）
	 */
	private String orderOpinion;
	/**
	 * 操作人类型（1、填报确认人，2、受理人，3上报确定人）
	 */
	private Integer orderPeopleId;
	
	/**
	 * 操作人名字（1、填报确认人，2、受理人，3上报确定人）
	 */
	@TableField(exist = false)
	private String orderPeopleName;
	/**
	 * 0 填报工单，1缺陷工单,2 巡检缺陷工单
	 */
	private Integer orderType;
	/**
	 * 创建工单时间
	 */
	private Date createTime;
	/**
	 * 要求完成时间
	 */
	private Date requirementTime;
	/**
	 * 确认时间
	 */
	private Date confirmedTime;
	/**
	 * 实际完成时间
	 */
	private Date actualTime;
	/**
	 * 申请延期时间
	 */
	private Date delayTime;
	/**
	 * 当前时间
	 */
	private Date nowTime;

	/**
	 * 设置：工单日志记录id
	 */
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	/**
	 * 获取：工单日志记录id
	 */
	public Integer getRecordId() {
		return recordId;
	}
	/**
	 * 设置：工单编号
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	/**
	 * 获取：工单编号
	 */
	public String getOrderNumber() {
		return orderNumber;
	}
	/**
	 * 设置：缺陷工单id 
	 */
	public void setDefectiveId(Integer defectiveId) {
		this.defectiveId = defectiveId;
	}
	/**
	 * 获取：缺陷工单id 
	 */
	public Integer getDefectiveId() {
		return defectiveId;
	}
	/**
	 * 设置：缺陷工单编号
	 */
	public void setDefectiveNumber(String defectiveNumber) {
		this.defectiveNumber = defectiveNumber;
	}
	/**
	 * 获取：缺陷工单编号
	 */
	public String getDefectiveNumber() {
		return defectiveNumber;
	}
	/**
	 * 设置：角色的名字
	 */
	public void setOrderPeople(String orderPeople) {
		this.orderPeople = orderPeople;
	}
	/**
	 * 获取：角色的名字
	 */
	public String getOrderPeople() {
		return orderPeople;
	}
	/**
	 * 设置：意见（结论）
	 */
	public void setOrderOpinion(String orderOpinion) {
		this.orderOpinion = orderOpinion;
	}
	/**
	 * 获取：意见（结论）
	 */
	public String getOrderOpinion() {
		return orderOpinion;
	}
	/**
	 * 设置：操作人类型（1、填报确认人，2、受理人，3上报确定人）
	 */
	public void setOrderPeopleId(Integer orderPeopleId) {
		this.orderPeopleId = orderPeopleId;
	}
	/**
	 * 获取：操作人类型（1、填报确认人，2、受理人，3上报确定人）
	 */
	public Integer getOrderPeopleId() {
		return orderPeopleId;
	}
	/**
	 * 设置：操作人类型（1、填报确认人，2、受理人，3上报确定人）
	 */
	public void setOrderPeopleName(String orderPeopleName) {
		this.orderPeopleName = orderPeopleName;
	}
	/**
	 * 获取：操作人类型（1、填报确认人，2、受理人，3上报确定人）
	 */
	public String getOrderPeopleName() {
		return orderPeopleName;
	}
	/**
	 * 设置：0 填报工单，1缺陷工单,2 巡检缺陷工单
	 */
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	/**
	 * 获取：0 填报工单，1缺陷工单,2 巡检缺陷工单
	 */
	public Integer getOrderType() {
		return orderType;
	}
	/**
	 * 设置：创建工单时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建工单时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：要求完成时间
	 */
	public void setRequirementTime(Date requirementTime) {
		this.requirementTime = requirementTime;
	}
	/**
	 * 获取：要求完成时间
	 */
	public Date getRequirementTime() {
		return requirementTime;
	}
	/**
	 * 设置：确认时间
	 */
	public void setConfirmedTime(Date confirmedTime) {
		this.confirmedTime = confirmedTime;
	}
	/**
	 * 获取：确认时间
	 */
	public Date getConfirmedTime() {
		return confirmedTime;
	}
	/**
	 * 设置：实际完成时间
	 */
	public void setActualTime(Date actualTime) {
		this.actualTime = actualTime;
	}
	/**
	 * 获取：实际完成时间
	 */
	public Date getActualTime() {
		return actualTime;
	}
	/**
	 * 设置：申请延期时间
	 */
	public void setDelayTime(Date delayTime) {
		this.delayTime = delayTime;
	}
	/**
	 * 获取：申请延期时间
	 */
	public Date getDelayTime() {
		return delayTime;
	}
	/**
	 * 设置：当前时间
	 */
	public void setNowTime(Date nowTime) {
		this.nowTime = nowTime;
	}
	/**
	 * 获取：当前时间
	 */
	public Date getNowTime() {
		return nowTime;
	}
}
