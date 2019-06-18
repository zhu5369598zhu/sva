package io.renren.modules.management.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 缺陷工单表
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-05-31 15:16:09
 */
@TableName("tb_order_defective")
public class OrderDefectiveEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 缺陷工单主键id
	 */
	@TableId
	private Integer defectiveId;
	/**
	 * 缺陷工单编号
	 */
	private String defectiveNumber;
	/**
	 * 缺陷工单主题
	 */
	private String defectiveTheme;
	/**
	 * 0 巡检缺陷异常 1 填报缺陷异常
	 */
	private Integer defectiveType;
	/**
	 * 0 巡检缺陷异常 1 填报缺陷异常
	 */
	@TableField(exist = false)
	private String defectiveTypeName;
	/**
	 * 部门id
	 */
	private Integer deptId;
	/**
	 * 部门名称
	 */
	@TableField(exist = false)
	private String deptName;
	/**
	 * 缺陷异常等级
	 */
	private Integer exceptionId;
	/**
	 * 缺陷异常名称
	 */
	@TableField(exist = false)
	private String exceptionName;
	/**
	 * 默认的工单内容
	 */
	private String orderContent;
	/**
	 * 缺陷确认人(填报)人
	 */
	private String defectiveName;
	/**
	 * 缺陷确认人(填报)人id
	 */
	private Integer defectiveNameId;
	/**
	 * 工单填报人意见
	 */
	private String defectiveNameOpinion;
	/**
	 * 填报时间
	 */
	private Date createTime;
	/**
	 * 0 拟制中 1 待确认 3 已转
	 */
	private Integer orderStatus;
	
	/**
	 *  转工单状态 名称 0 拟制中 1 待确认 3 已转
	 */
	@TableField(exist = false)
	private String orderStatusName;
	/**
	 * 工单确认人
	 */
	private String orderConfirmer;
	/**
	 * 工单确认人 id
	 */
	private Integer orderConfirmerId;
	/**
	 * 工单确认时间
	 */
	private Date confirmedTime;
	/**
	 * 工单确认人意见
	 */
	private String orderConfirmerOpinion;
	/**
	 * 工单受理人
	 */
	private String orderAcceptor;
	/**
	 * 工单受理人 用户id
	 */
	private Integer orderAcceptorId;
	/**
	 * 要求完成时间  
	 */
	private Date requirementTime;
	/**
	 * 归属设备
	 */
	private String defectiveDevice;
	/**
	 * 结果表的 id
	 */
	private Integer resultId;

	/**
	 * 设置：缺陷工单主键id
	 */
	public void setDefectiveId(Integer defectiveId) {
		this.defectiveId = defectiveId;
	}
	/**
	 * 获取：缺陷工单主键id
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
	 * 设置：缺陷工单主题
	 */
	public void setDefectiveTheme(String defectiveTheme) {
		this.defectiveTheme = defectiveTheme;
	}
	/**
	 * 获取：缺陷工单主题
	 */
	public String getDefectiveTheme() {
		return defectiveTheme;
	}
	/**
	 * 设置：0 巡检缺陷异常 1 填报缺陷异常
	 */
	public void setDefectiveType(Integer defectiveType) {
		this.defectiveType = defectiveType;
	}
	/**
	 * 获取：0 巡检缺陷异常 1 填报缺陷异常
	 */
	public Integer getDefectiveType() {
		return defectiveType;
	}
	/**
	 * 设置：0 巡检缺陷异常 名称 1 填报缺陷异常  名称
	 */
	public void setDefectiveTypeName(String defectiveTypeName) {
		this.defectiveTypeName = defectiveTypeName;
	}
	/**
	 * 获取：0 巡检缺陷异常 名称 1 填报缺陷异常 名称
	 */
	public String getDefectiveTypeName() {
		return defectiveTypeName;
	}
	
	/**
	 * 设置：部门id
	 */
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	/**
	 * 获取：部门id
	 */
	public Integer getDeptId() {
		return deptId;
	}
	/**
	 * 设置：部门名称
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	/**
	 * 获取：部门名称
	 */
	public String getDeptName() {
		return deptName;
	}
	/**
	 * 设置：缺陷异常等级
	 */
	public void setExceptionId(Integer exceptionId) {
		this.exceptionId = exceptionId;
	}
	/**
	 * 获取：缺陷异常等级
	 */
	public Integer getExceptionId() {
		return exceptionId;
	}
	/**
	 * 设置：缺陷异常等级名称
	 */
	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}
	/**
	 * 获取：缺陷异常等级名称
	 */
	public String getExceptionName() {
		return exceptionName;
	}
	/**
	 * 设置：默认的工单内容
	 */
	public void setOrderContent(String orderContent) {
		this.orderContent = orderContent;
	}
	/**
	 * 获取：默认的工单内容
	 */
	public String getOrderContent() {
		return orderContent;
	}
	/**
	 * 设置：缺陷确认人(填报)人
	 */
	public void setDefectiveName(String defectiveName) {
		this.defectiveName = defectiveName;
	}
	/**
	 * 获取：缺陷确认人(填报)人
	 */
	public String getDefectiveName() {
		return defectiveName;
	}
	/**
	 * 设置：缺陷确认人(填报)人id
	 */
	public void setDefectiveNameId(Integer defectiveNameId) {
		this.defectiveNameId = defectiveNameId;
	}
	/**
	 * 获取：缺陷确认人(填报)人id
	 */
	public Integer getDefectiveNameId() {
		return defectiveNameId;
	}
	/**
	 * 设置：工单填报人意见
	 */
	public void setDefectiveNameOpinion(String defectiveNameOpinion) {
		this.defectiveNameOpinion = defectiveNameOpinion;
	}
	/**
	 * 获取：工单填报人意见
	 */
	public String getDefectiveNameOpinion() {
		return defectiveNameOpinion;
	}
	/**
	 * 设置：填报时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：填报时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：0 拟制中 1 待确认 3 已转
	 */
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	/**
	 * 获取：0 拟制中 1 待确认 3 已转
	 */
	public Integer getOrderStatus() {
		return orderStatus;
	}
	/**
	 * 设置：转工单 设置：0 拟制中 1 待确认 3 已转   名称
	 */
	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}
	/**
	 * 获取：0 拟制中 1 待确认 3 已转 名称
	 */
	public String getOrderStatusName() {
		return orderStatusName;
	}
	
	
	/**
	 * 设置：工单确认人
	 */
	public void setOrderConfirmer(String orderConfirmer) {
		this.orderConfirmer = orderConfirmer;
	}
	/**
	 * 获取：工单确认人
	 */
	public String getOrderConfirmer() {
		return orderConfirmer;
	}
	/**
	 * 设置：工单确认人 id
	 */
	public void setOrderConfirmerId(Integer orderConfirmerId) {
		this.orderConfirmerId = orderConfirmerId;
	}
	/**
	 * 获取：工单确认人 id
	 */
	public Integer getOrderConfirmerId() {
		return orderConfirmerId;
	}
	/**
	 * 设置：工单确认时间
	 */
	public void setConfirmedTime(Date confirmedTime) {
		this.confirmedTime = confirmedTime;
	}
	/**
	 * 获取：工单确认时间
	 */
	public Date getConfirmedTime() {
		return confirmedTime;
	}
	/**
	 * 设置：工单确认人意见
	 */
	public void setOrderConfirmerOpinion(String orderConfirmerOpinion) {
		this.orderConfirmerOpinion = orderConfirmerOpinion;
	}
	/**
	 * 获取：工单确认人意见
	 */
	public String getOrderConfirmerOpinion() {
		return orderConfirmerOpinion;
	}
	/**
	 * 设置：工单受理人
	 */
	public void setOrderAcceptor(String orderAcceptor) {
		this.orderAcceptor = orderAcceptor;
	}
	/**
	 * 获取：工单受理人
	 */
	public String getOrderAcceptor() {
		return orderAcceptor;
	}
	/**
	 * 设置：工单受理人 id
	 */
	public void setOrderAcceptorId(Integer orderAcceptorId) {
		this.orderAcceptorId = orderAcceptorId;
	}
	/**
	 * 获取：工单受理人 id
	 */
	public Integer getOrderAcceptorId() {
		return orderAcceptorId;
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
	 * 设置：归属设备
	 */
	public void setDefectiveDevice(String defectiveDevice) {
		this.defectiveDevice = defectiveDevice;
	}
	/**
	 * 获取：归属设备
	 */
	public String getDefectiveDevice() {
		return defectiveDevice;
	}
	/**
	 * 设置：结果表的id
	 */
	public void setResultId(Integer resultId) {
		this.resultId = resultId;
	}
	/**
	 * 获取：结果表的id
	 */
	public Integer getResultId() {
		return resultId;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
