package io.renren.modules.management.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;

import java.io.Serializable;
import java.util.Date;

/**
 * 工单管理表
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-05-21 11:00:21
 */
@TableName("tb_order_management")
public class OrderManagementEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 管理工单主键id
	 */
	@TableId
	private Integer orderId;
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
	 * 工单主题
	 */
	private String orderName;
	/**
	 * 所属机构
	 */
	private Integer deptId;
	/**
	 * 缺陷等级
	 */
	private Integer exceptionId;
	/**
	 * 缺陷等级 名称
	 */
	@TableField(exist = false)
	private String exceptionName;
	/**
	 * 所属机构名称
	 */
	@TableField(exist = false)
	private String deptName;
	/**
	 * 缺陷确认人(填报)人
	 */
	private String defectiveName;
	/**
	 * 工单默认内容
	 */
	private String orderContent;
	/**
	 * 工单填报人
	 */
	private String orderApplicant;
	/**
	 * 工单填报人用户id
	 */
	private Integer orderApplicantId;
	/**
	 * 工单填报人意见
	 */
	private String orderApplicantOpinion;
	/**
	 * 工单受理人
	 */
	private String orderAcceptor;
	/**
	 * 工单受理人 用户id
	 */
	private Integer orderAcceptorId;
	/**
	 * 工单受理人意见（结论）
	 */
	private String orderAcceptorOpinion;
	/**
	 * 工单确认人
	 */
	private String orderConfirmer;
	/**
	 * 工单确认人 用户id
	 */
	private Integer orderConfirmerId;
	/**
	 * 工单确认人意见（结论）
	 */
	private String orderConfirmerOpinion;
	/**
	 * 创建时间  （下发时间）
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
	@TableField(strategy= FieldStrategy.IGNORED)
	private Date delayTime;
	/**
	 * 处理结果
	 */
	private String processingResult;
	/**
	 * 0拟制中，1代受理，2待上报，3待确认，4待结算(待完结)，5已完结
	 */
	private Integer orderStatus;
	/**
	 * 状态名称
	 */
	@TableField(exist = false)
	private String orderStatusName;
	/**
	 * 0 填报工单，1缺陷工单，2巡检缺陷工单
	 */
	private Integer orderType;
	/**
	 * 0 填报工单名称，1缺陷工单名称，2巡检缺陷工单名称
	 */
	@TableField(exist = false)
	private String orderTypeName;
	/**
	 * 缺陷等级id
	 */
	private Integer levelId;
	/**
	 * 使用的备件
	 */
	private String orderDevice;
	/**
	 * 归属设备
	 */
	@TableField(exist = false)
	private String defectiveDevice;

	/**
	 * 设置：管理工单主键id
	 */
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	/**
	 * 获取：管理工单主键id
	 */
	public Integer getOrderId() {
		return orderId;
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
	 * 设置：工单主题
	 */
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	/**
	 * 获取：工单主题
	 */
	public String getOrderName() {
		return orderName;
	}
	/**
	 * 设置：所属机构
	 */
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	/**
	 * 获取：所属机构
	 */
	public Integer getDeptId() {
		return deptId;
	}
	/**
	 * 设置：缺陷等级
	 */
	public void setExceptionId(Integer exceptionId) {
		this.exceptionId = exceptionId;
	}
	/**
	 * 获取：缺陷等级
	 */
	public Integer getExceptionId() {
		return exceptionId;
	}
	/**
	 * 设置：缺陷等级名称
	 */
	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}
	/**
	 * 获取：缺陷等级名称
	 */
	public String getExceptionName() {
		return exceptionName;
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
	 * 设置：工单默认内容
	 */
	public void setOrderContent(String orderContent) {
		this.orderContent = orderContent;
	}
	/**
	 * 获取：工单默认内容
	 */
	public String getOrderContent() {
		return orderContent;
	}
	/**
	 * 设置：工单填报人
	 */
	public void setOrderApplicant(String orderApplicant) {
		this.orderApplicant = orderApplicant;
	}
	/**
	 * 获取：工单填报人
	 */
	public String getOrderApplicant() {
		return orderApplicant;
	}
	/**
	 * 设置：工单填报人用户id
	 */
	public void setOrderApplicantId(Integer orderApplicantId) {
		this.orderApplicantId = orderApplicantId;
	}
	/**
	 * 获取：工单填报人用户id
	 */
	public Integer getOrderApplicantId() {
		return orderApplicantId;
	}
	/**
	 * 设置：工单填报人意见
	 */
	public void setOrderApplicantOpinion(String orderApplicantOpinion) {
		this.orderApplicantOpinion = orderApplicantOpinion;
	}
	/**
	 * 获取：工单填报人意见
	 */
	public String getOrderApplicantOpinion() {
		return orderApplicantOpinion;
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
	 * 设置：工单受理人 用户id
	 */
	public void setOrderAcceptorId(Integer orderAcceptorId) {
		this.orderAcceptorId = orderAcceptorId;
	}
	/**
	 * 获取：工单受理人 用户id
	 */
	public Integer getOrderAcceptorId() {
		return orderAcceptorId;
	}
	/**
	 * 设置：工单受理人意见（结论）
	 */
	public void setOrderAcceptorOpinion(String orderAcceptorOpinion) {
		this.orderAcceptorOpinion = orderAcceptorOpinion;
	}
	/**
	 * 获取：工单受理人意见（结论）
	 */
	public String getOrderAcceptorOpinion() {
		return orderAcceptorOpinion;
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
	 * 设置：工单确认人 用户id
	 */
	public void setOrderConfirmerId(Integer orderConfirmerId) {
		this.orderConfirmerId = orderConfirmerId;
	}
	/**
	 * 获取：工单确认人 用户id
	 */
	public Integer getOrderConfirmerId() {
		return orderConfirmerId;
	}
	/**
	 * 设置：工单确认人意见（结论）
	 */
	public void setOrderConfirmerOpinion(String orderConfirmerOpinion) {
		this.orderConfirmerOpinion = orderConfirmerOpinion;
	}
	/**
	 * 获取：工单确认人意见（结论）
	 */
	public String getOrderConfirmerOpinion() {
		return orderConfirmerOpinion;
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
	 * 设置：处理结果
	 */
	public void setProcessingResult(String processingResult) {
		this.processingResult = processingResult;
	}
	/**
	 * 获取：处理结果
	 */
	public String getProcessingResult() {
		return processingResult;
	}
	/**
	 * 设置：0拟制中，1代受理，2待上报，3待确认，4待结算(待完结)，5已完结
	 */
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	/**
	 * 获取：0拟制中，1代受理，2待上报，3待确认，4待结算(待完结)，5已完结
	 */
	public Integer getOrderStatus() {
		return orderStatus;
	}
	/**
	 * 设置：状态名称
	 */
	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}
	/**
	 * 获取：状态名称
	 */
	public String getOrderStatusName() {
		return orderStatusName;
	}
	
	/**
	 * 设置：0 填报工单，1缺陷工单
	 */
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	/**
	 * 获取：0 填报工单，1缺陷工单
	 */
	public Integer getOrderType() {
		return orderType;
	}
	/**
	 * 设置：工单类型
	 */
	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}
	/**
	 * 获取：获取工单类型
	 */
	public String getOrderTypeName() {
		return orderTypeName;
	}
	/**
	 * 设置：缺陷等级id
	 */
	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}
	/**
	 * 获取：缺陷等级id
	 */
	public Integer getLevelId() {
		return levelId;
	}
	/**
	 * 设置：使用的备件
	 */
	public void setOrderDevice(String orderDevice) {
		this.orderDevice = orderDevice;
	}
	/**
	 * 获取：使用的备件
	 */
	public String getOrderDevice() {
		return orderDevice;
	}
	/**
	 * 设置：归属备件
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
	
	
	
	
}
