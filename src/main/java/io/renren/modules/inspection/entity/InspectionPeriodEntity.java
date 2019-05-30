package io.renren.modules.inspection.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-01-29 17:34:03
 */
@TableName("tb_inspection_period")
public class InspectionPeriodEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * guid
	 */
	private String guid;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 线路id
	 */
	private Long lineId;
	/**
	 * 基准点
	 */
	private String basePoint;
	/**
	 * 跨度起点
	 */
	private Integer startPoint;
	/**
	 * 跨度
	 */
	private Integer span;
	/**
	 * 频度
	 */
	private Integer frequency;
	/**
	 * 轮次集合
	 */
	@TableField(exist = false)
	private List<Long> turnList;
	/**
	 * 轮次名称集合
	 */
	@TableField(exist = false)
	private List<String> turnNameList;
	/**
	 * 轮次名称集合
	 */
	@TableField(exist = false)
	private String turnNames;
	/**
	 * 是否允许漏检
	 */
	private Long isAllowOmission;
	/**
	 * 是否允许超时
	 */
	private Long isAllowTimeout;
	/**
	 * 轮次完成模式（0全体完成，1班组完成，2个人完成）
	 */
	private Long turnFinishMod;
	/**
	 * 轮次完成模式名称
	 */
	@TableField(exist = false)
	private String turnFinishModName;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 设置：id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public Long getId() {
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
	 * 设置：线路id
	 */
	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}
	/**
	 * 获取：线路id
	 */
	public Long getLineId() {
		return lineId;
	}
	/**
	 * 设置：基准点
	 */
	public void setBasePoint(String basePoint) {
		this.basePoint = basePoint;
	}
	/**
	 * 获取：基准点
	 */
	public String getBasePoint() {
		return basePoint;
	}
	/**
	 * 设置：跨度起点
	 */
	public void setStartPoint(Integer startPoint) {
		this.startPoint = startPoint;
	}
	/**
	 * 获取：跨度起点
	 */
	public Integer getStartPoint() {
		return startPoint;
	}
	/**
	 * 设置：跨度
	 */
	public void setSpan(Integer span) {
		this.span = span;
	}
	/**
	 * 获取：跨度
	 */
	public Integer getSpan() {
		return span;
	}
	/**
	 * 设置：频度
	 */
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	/**
	 * 获取：频度
	 */
	public Integer getFrequency() {
		return frequency;
	}
	/**
	 * 设置：轮次集合
	 */
	public void setTurnList(List<Long> turnList) { this.turnList = turnList; }
	/**
	 * 获取：轮次集合
	 */
	public List<Long> getTurnList() {
		return turnList;
	}
	/**
	 * 设置：轮次名称集合
	 */
	public void setTurnNameList(List<String> turnNameList) { this.turnNameList = turnNameList; }
	/**
	 * 获取：轮次名称集合
	 */
	public List<String> getTurnNameList() {
		return turnNameList;
	}
	/**
	 * 设置：轮次名称集合
	 */
	public void setTurnNames(String turnNames) { this.turnNames = turnNames; }
	/**
	 * 获取：轮次名称集合
	 */
	public String getTurnNames() {
		return turnNames;
	}
	/**
	 * 设置：是否允许漏检
	 */
	public void setIsAllowOmission(Long isAllowOmission) {
		this.isAllowOmission = isAllowOmission;
	}
	/**
	 * 获取：是否允许漏检
	 */
	public Long getIsAllowOmission() {
		return isAllowOmission;
	}
	/**
	 * 设置：是否允许超时
	 */
	public void setIsAllowTimeout(Long isAllowTimeout) {
		this.isAllowTimeout = isAllowTimeout;
	}
	/**
	 * 获取：是否允许超时
	 */
	public Long getIsAllowTimeout() {
		return isAllowTimeout;
	}
	/**
	 * 设置：轮次完成模式（0全体完成，1班组完成，2个人完成）
	 */
	public void setTurnFinishMod(Long turnFinishMod) {
		this.turnFinishMod = turnFinishMod;
	}
	/**
	 * 获取：轮次完成模式（0全体完成，1班组完成，2个人完成）
	 */
	public Long getTurnFinishMod() {
		return this.turnFinishMod;
	}
	/**
	 * 设置：轮次完成模式名称
	 */
	public void setTurnFinishModName(String turnFinishModName) {
		this.turnFinishModName = turnFinishModName;
	}
	/**
	 * 获取：轮次完成模式名称
	 */
	public String getTurnFinishModName() {
		return turnFinishModName;
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
}
