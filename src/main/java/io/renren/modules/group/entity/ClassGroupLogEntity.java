package io.renren.modules.group.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 班组日志表
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-05-05 16:24:44
 */
@TableName("tb_class_group_log")
public class ClassGroupLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer classId;
	/**
	 * 日志编号
	 */
	private String logNumber;
	/**
	 * 部门车间id
	 */
	private Integer deptId;
	
	/**
	 * 所属机构名称
	 */
	@TableField(exist = false)
	private String deptName;
	/**
	 * 班组
	 */
	private String classGroupName;
	/**
	 * 轮次
	 */
	private Integer baseTurnId;
	
	/**
	 * 班次(轮次) 名称
	 */
	@TableField(exist = false)
	private String baseTurnName;
	/**
	 * 日志类型（1:班长日志，2班前日志，3班后日志）
	 */
	private String logType;
	
	/**
	 * 日志类型名称
	 */
	@TableField(exist = false)
	private String logTypeName;
	/**
	 * 日志状态(1待确认，2已确认，3拟制中，4已驳回)
	 */
	private String logStatus;
	/**
	 * 用户处理日志状态(用户确认正常为1，用户驳回为2)
	 */
	private String logUserStatus;
	/**
	 * 日志状态名称
	 */
	@TableField(exist = false)
	private String logStatusName;
	/**
	 * 记录人
	 */
	private String noteTaker;
	/**
	 * 交接人
	 */
	private String handoverPerson;
	/**
	 * 交接人 id
	 */
	private Integer handoverPersonId;
	/**
	 * 接班人
	 */
	private String successor;
	/**
	 * 接班人 id
	 */
	private Integer successorId;
	/**
	 * 接班人前台名称
	 */
	@TableField(exist = false)
	private String successorName;
	/**
	 * 交接完成时间
	 */
	private Date createTime;
	/**
	 * 班长
	 */
	private String monitor;
	/**
	 * 应出勤人数
	 */
	private Integer shouldAttendance;
	/**
	 * 实出勤人数
	 */
	private Integer attendance;
	/**
	 * 实到人员
	 */
	private String actualArrival;
	/**
	 * 未到人员
	 */
	private String notArrived;
	/**
	 * 顶班人员
	 */
	private String topArrived;
	/**
	 * 缺勤原因
	 */
	private String reasonsAbsence;
	/**
	 * 接班记事
	 */
	private String successionRecord;
	/**
	 * 当班记事
	 */
	private String onDuty;
	/**
	 * 上级通知
	 */
	private String superiorNotice;
	/**
	 * 交代事项
	 */
	private String accountConfession;
	/**
	 * 人员精神状态（1正常，2异常）
	 */
	private String personnelMentalState;
	/**
	 * 人员精神异常描述
	 */
	private String mentalException;
	/**
	 * 劳动防护用品
	 */
	private String laborProtectiveArticles;
	/**
	 * 劳动防护用品异常描述
	 */
	private String protectiveException;
	/**
	 * 工器具（1正常，2异常）
	 */
	private String tools;
	/**
	 * 工器具异常描述
	 */
	private String toolsException;
	/**
	 * 其他异常
	 */
	private String otherException;
	/**
	 * 工作安排
	 */
	private String workTask;
	/**
	 * 危险点
	 */
	private String dangerousPoint;
	/**
	 * 防范措施
	 */
	private String preventiveMeasures;
	/**
	 * 交底人
	 */
	private String manAgreement;
	/**
	 * 班组成员
	 */
	private String teamMembers;
	/**
	 * 班组成员 id
	 */
	private String teamMembersIds;
	/**
	 * 工作总结
	 */
	private String workSummary;
	/**
	 * 负责人
	 */
	private String personCharge;
	/**
	 * 驳回原因
	 */
	private String rejectReason;

	/**
	 * 是否跟自己有关
	 */
	@TableField(exist = false)
	private Integer newsCounts;
	/**
	 * 有驳回是否跟自己有关
	 */
	@TableField(exist = false)
	private Integer rejectNewsCounts;
	
	/**
	 * 驳回人
	 */
	private String rejectPeople;

	/**
	 * 设置：
	 */
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	/**
	 * 获取：
	 */
	public Integer getClassId() {
		return classId;
	}
	/**
	 * 设置：日志编号
	 */
	public void setLogNumber(String logNumber) {
		this.logNumber = logNumber;
	}
	/**
	 * 获取：日志编号
	 */
	public String getLogNumber() {
		return logNumber;
	}
	/**
	 * 设置：部门车间id
	 */
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	/**
	 * 获取：部门车间id
	 */
	public Integer getDeptId() {
		return deptId;
	}
	/**
	 * 设置：班组
	 */
	public void setClassGroupName(String classGroupName) {
		this.classGroupName = classGroupName;
	}
	/**
	 * 获取：班组
	 */
	public String getClassGroupName() {
		return classGroupName;
	}
	/**
	 * 设置：轮次
	 */
	public void setBaseTurnId(Integer baseTurnId) {
		this.baseTurnId = baseTurnId;
	}
	/**
	 * 获取：轮次
	 */
	public Integer getBaseTurnId() {
		return baseTurnId;
	}
	/**
	 * 设置：日志类型（1:班长日志，2班前日志，3班后日志）
	 */
	public void setLogType(String logType) {
		this.logType = logType;
	}
	/**
	 * 获取：日志类型（1:班长日志，2班前日志，3班后日志）
	 */
	public String getLogType() {
		return logType;
	}
	/**
	 * 设置：日志状态(1待确认，2已确认，3拟制中，4已完成)
	 */
	public void setLogStatus(String logStatus) {
		this.logStatus = logStatus;
	}
	/**
	 * 获取：日志状态(1待确认，2已确认，3拟制中，4已完成)
	 */
	public String getLogStatus() {
		return logStatus;
	}
	/**
	 * 设置：用户处理日志状态(用户确认正常为1，用户驳回为2)
	 */
	public void setLogUserStatus(String logUserStatus) {
		this.logUserStatus = logUserStatus;
	}
	/**
	 * 获取：用户处理日志状态(用户确认正常为1，用户驳回为2)
	 */
	public String getLogUserStatus() {
		return logUserStatus;
	}
	/**
	 * 设置：记录人
	 */
	public void setNoteTaker(String noteTaker) {
		this.noteTaker = noteTaker;
	}
	/**
	 * 获取：记录人
	 */
	public String getNoteTaker() {
		return noteTaker;
	}
	/**
	 * 设置：交接人
	 */
	public void setHandoverPerson(String handoverPerson) {
		this.handoverPerson = handoverPerson;
	}
	/**
	 * 获取：交接人
	 */
	public String getHandoverPerson() {
		return handoverPerson;
	}
	/**
	 * 设置：交接人 id
	 */
	public void setHandoverPersonId(Integer handoverPersonId) {
		this.handoverPersonId = handoverPersonId;
	}
	/**
	 * 获取：交接人 id
	 */
	public Integer getHandoverPersonId() {
		return handoverPersonId;
	}
	
	/**
	 * 设置：接班人
	 */
	public void setSuccessor(String successor) {
		this.successor = successor;
	}
	/**
	 * 获取：接班人
	 */
	public String getSuccessor() {
		return successor;
	}
	/**
	 * 获取：接班人 id
	 */
	public Integer getSuccessorId() {
		return successorId;
	}
	/**
	 * 设置：接班人 id
	 */
	public void setSuccessorId(Integer successorId) {
		this.successorId = successorId;
	}
	/**
	 * 获取：接班人前台显示名称
	 */
	public String getSuccessorName() {
		return successorName;
	}
	/**
	 * 设置：接班人 前台显示名称
	 */
	public void setSuccessorName(String successorName) {
		this.successorName = successorName;
	}
	/**
	 * 设置：交接完成时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：交接完成时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：班长
	 */
	public void setMonitor(String monitor) {
		this.monitor = monitor;
	}
	/**
	 * 获取：班长
	 */
	public String getMonitor() {
		return monitor;
	}
	/**
	 * 设置：应出勤人数
	 */
	public void setShouldAttendance(Integer shouldAttendance) {
		this.shouldAttendance = shouldAttendance;
	}
	/**
	 * 获取：应出勤人数
	 */
	public Integer getShouldAttendance() {
		return shouldAttendance;
	}
	/**
	 * 设置：实出勤人数
	 */
	public void setAttendance(Integer attendance) {
		this.attendance = attendance;
	}
	/**
	 * 获取：实出勤人数
	 */
	public Integer getAttendance() {
		return attendance;
	}
	/**
	 * 设置：实到人员
	 */
	public void setActualArrival(String actualArrival) {
		this.actualArrival = actualArrival;
	}
	/**
	 * 获取：实到人员
	 */
	public String getActualArrival() {
		return actualArrival;
	}
	/**
	 * 设置：未到人员
	 */
	public void setNotArrived(String notArrived) {
		this.notArrived = notArrived;
	}
	/**
	 * 获取：未到人员
	 */
	public String getNotArrived() {
		return notArrived;
	}
	/**
	 * 设置：顶班人员
	 */
	public void setTopArrived(String topArrived) {
		this.topArrived = topArrived;
	}
	/**
	 * 获取：顶班人员
	 */
	public String getTopArrived() {
		return topArrived;
	}
	/**
	 * 设置：缺勤原因
	 */
	public void setReasonsAbsence(String reasonsAbsence) {
		this.reasonsAbsence = reasonsAbsence;
	}
	/**
	 * 获取：缺勤原因
	 */
	public String getReasonsAbsence() {
		return reasonsAbsence;
	}
	/**
	 * 设置：接班记事
	 */
	public void setSuccessionRecord(String successionRecord) {
		this.successionRecord = successionRecord;
	}
	/**
	 * 获取：接班记事
	 */
	public String getSuccessionRecord() {
		return successionRecord;
	}
	/**
	 * 设置：当班记事
	 */
	public void setOnDuty(String onDuty) {
		this.onDuty = onDuty;
	}
	/**
	 * 获取：当班记事
	 */
	public String getOnDuty() {
		return onDuty;
	}
	/**
	 * 设置：上级通知
	 */
	public void setSuperiorNotice(String superiorNotice) {
		this.superiorNotice = superiorNotice;
	}
	/**
	 * 获取：上级通知
	 */
	public String getSuperiorNotice() {
		return superiorNotice;
	}
	/**
	 * 设置：交代事项
	 */
	public void setAccountConfession(String accountConfession) {
		this.accountConfession = accountConfession;
	}
	/**
	 * 获取：交代事项
	 */
	public String getAccountConfession() {
		return accountConfession;
	}
	/**
	 * 设置：人员精神状态（1正常，2异常）
	 */
	public void setPersonnelMentalState(String personnelMentalState) {
		this.personnelMentalState = personnelMentalState;
	}
	/**
	 * 获取：人员精神状态（1正常，2异常）
	 */
	public String getPersonnelMentalState() {
		return personnelMentalState;
	}
	/**
	 * 设置：人员精神异常描述
	 */
	public void setMentalException(String mentalException) {
		this.mentalException = mentalException;
	}
	/**
	 * 获取：人员精神异常描述
	 */
	public String getMentalException() {
		return mentalException;
	}
	/**
	 * 设置：劳动防护用品
	 */
	public void setLaborProtectiveArticles(String laborProtectiveArticles) {
		this.laborProtectiveArticles = laborProtectiveArticles;
	}
	/**
	 * 获取：劳动防护用品
	 */
	public String getLaborProtectiveArticles() {
		return laborProtectiveArticles;
	}
	/**
	 * 设置：劳动防护用品异常描述
	 */
	public void setProtectiveException(String protectiveException) {
		this.protectiveException = protectiveException;
	}
	/**
	 * 获取：劳动防护用品异常描述
	 */
	public String getProtectiveException() {
		return protectiveException;
	}
	/**
	 * 设置：工器具（1正常，2异常）
	 */
	public void setTools(String tools) {
		this.tools = tools;
	}
	/**
	 * 获取：工器具（1正常，2异常）
	 */
	public String getTools() {
		return tools;
	}
	/**
	 * 设置：工器具异常描述
	 */
	public void setToolsException(String toolsException) {
		this.toolsException = toolsException;
	}
	/**
	 * 获取：工器具异常描述
	 */
	public String getToolsException() {
		return toolsException;
	}
	/**
	 * 设置：其他异常
	 */
	public void setOtherException(String otherException) {
		this.otherException = otherException;
	}
	/**
	 * 获取：其他异常
	 */
	public String getOtherException() {
		return otherException;
	}
	/**
	 * 设置：工作安排
	 */
	public void setWorkTask(String workTask) {
		this.workTask = workTask;
	}
	/**
	 * 获取：工作安排
	 */
	public String getWorkTask() {
		return workTask;
	}
	/**
	 * 设置：危险点
	 */
	public void setDangerousPoint(String dangerousPoint) {
		this.dangerousPoint = dangerousPoint;
	}
	/**
	 * 获取：危险点
	 */
	public String getDangerousPoint() {
		return dangerousPoint;
	}
	/**
	 * 设置：防范措施
	 */
	public void setPreventiveMeasures(String preventiveMeasures) {
		this.preventiveMeasures = preventiveMeasures;
	}
	/**
	 * 获取：防范措施
	 */
	public String getPreventiveMeasures() {
		return preventiveMeasures;
	}
	/**
	 * 设置：交底人
	 */
	public void setManAgreement(String manAgreement) {
		this.manAgreement = manAgreement;
	}
	/**
	 * 获取：交底人
	 */
	public String getManAgreement() {
		return manAgreement;
	}
	/**
	 * 设置：班组成员
	 */
	public void setTeamMembers(String teamMembers) {
		this.teamMembers = teamMembers;
	}
	/**
	 * 获取：班组成员
	 */
	public String getTeamMembers() {
		return teamMembers;
	}
	/**
	 * 设置：班组成员id
	 */
	public void setTeamMembersIds(String teamMembersIds) {
		this.teamMembersIds = teamMembersIds;
	}
	/**
	 * 获取：班组成员id
	 */
	public String getTeamMembersIds() {
		return teamMembersIds;
	}
	/**
	 * 设置：工作总结
	 */
	public void setWorkSummary(String workSummary) {
		this.workSummary = workSummary;
	}
	/**
	 * 获取：工作总结
	 */
	public String getWorkSummary() {
		return workSummary;
	}
	/**
	 * 设置：负责人
	 */
	public void setPersonCharge(String personCharge) {
		this.personCharge = personCharge;
	}
	/**
	 * 获取：负责人
	 */
	public String getPersonCharge() {
		return personCharge;
	}
	/**
	 * 获取：机构名称
	 */
	public String getDeptName() {
		return deptName;
	}
	/**
	 * 设置：机构名称
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	/**
	 * 获取：班次名称
	 */
	public String getBaseTurnName() {
		return baseTurnName;
	}
	/**
	 * 设置：班次名称
	 */
	public void setBaseTurnName(String baseTurnName) {
		this.baseTurnName = baseTurnName;
	}
	/**
	 * 获取：日志类型名称
	 */
	public String getLogTypeName() {
		return logTypeName;
	}
	/**
	 * 设置：日志类型名称
	 */
	public void setLogTypeName(String logTypeName) {
		this.logTypeName = logTypeName;
	}
	/**
	 * 获取：日志状态名称
	 */
	public String getLogStatusName() {
		return logStatusName;
	}
	/**
	 * 设置：日志状态名称
	 */
	public void setLogStatusName(String logStatusName) {
		this.logStatusName = logStatusName;
	}
	/**
	 * 获取：驳回原因
	 */
	public String getRejectReason() {
		return rejectReason;
	}
	/**
	 * 设置：驳回原因
	 */
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	/**
	 * 获取：是否跟自己有关
	 */
	public Integer getNewsCounts(){ return  newsCounts;}
	/**
	 * 设置：是否跟自己有关
	 */
	public void setNewsCounts(Integer newsCounts){ this.newsCounts = newsCounts;}
	/**
	 * 获取：有驳回是否跟自己有关
	 */
	public Integer getRejectNewsCounts() {
		return rejectNewsCounts;
	}
	/**
	 * 设置：有驳回是否跟自己有关
	 */
	public void setRejectNewsCounts(Integer rejectNewsCounts) {
		this.rejectNewsCounts = rejectNewsCounts;
	}
	/**
	 * 设置：驳回人
	 */
	public void setRejectPeople(String rejectPeople) {
		this.rejectPeople = rejectPeople;
	}
	/**
	 * 获取：驳回人
	 */
	public String getRejectPeople() {
		return rejectPeople;
	}
	
}
