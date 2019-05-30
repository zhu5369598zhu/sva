package io.renren.modules.inspection.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-02-01 17:02:21
 */
@TableName("tb_line_zone")
public class LineZoneEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer id;
	/**
	 * 线路id
	 */
	private Integer lineId;
	/**
	 * 巡区id
	 */
	private Integer zoneId;
	/**
	 * 巡区名称
	 */
	@TableField(exist = false)
	private String zoneName;
	/**
	 * 巡区编码
	 */
	@TableField(exist = false)
	private  String zoneCode;
	/**
	 * 排序
	 */
	private Integer orderNum;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 设置：id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：线路id
	 */
	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}
	/**
	 * 获取：线路id
	 */
	public Integer getLineId() {
		return lineId;
	}
	/**
	 * 设置：巡区id
	 */
	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}
	/**
	 * 获取：巡区id
	 */
	public Integer getZoneId() {
		return zoneId;
	}
	/**
	 * 设置：巡区名称
	 */
	public void setZoneName(String zoneName) { this.zoneName = zoneName; }
	/**
	 * 获取：巡区名称
	 */
	public String getZoneName() {
		return zoneName;
	}
	/**
	 * 设置：巡区编码
	 */
	public void setZoneCode(String zoneCode) { this.zoneCode = zoneCode; }
	/**
	 * 获取：巡区编码
	 */
	public String getZoneCode() {
		return zoneCode;
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
