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
 * @date 2019-03-15 11:43:31
 */
@TableName("tb_inspection_line_publish")
public class InspectionLinePublishEntity implements Serializable {
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
	 * pda id
	 */
	private Integer pdaId;
	/**
	 * pad名称
	 */@TableField(exist = false)
	private String pdaName;
	/**
	 * pda地址
	 */
	@TableField(exist = false)
	private String pdaMac;
	/**
	 * 是否已下载
	 */
	private Integer isDownload;
	/**
	 * 下载时间
	 */
	private Date updateTime;
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
	 * 设置：pda id
	 */
	public void setPdaId(Integer pdaId) {
		this.pdaId = pdaId;
	}
	/**
	 * 获取：pda id
	 */
	public Integer getPdaId() {
		return pdaId;
	}
	/**
	 * 设置：是否已下载
	 */
	public void setIsDownload(Integer isDownload) {
		this.isDownload = isDownload;
	}
	/**
	 * 获取：是否已下载
	 */
	public Integer getIsDownload() {
		return isDownload;
	}
	/**
	 * 设置：下载时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：下载时间
	 */
	public Date getUpdateTime() {
		return updateTime;
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
	 * 设置：pda名称
	 */
	public void setPdaName(String pdaName) {
		this.pdaName = pdaName;
	}
	/**
	 * 获取：pda名称
	 */
	public String getpdaName() {
		return pdaName;
	}
	/**
	 * 设置：pda地址
	 */
	public void setPdaMac(String pdaMac) {
		this.pdaMac = pdaMac;
	}
	/**
	 * 获取：pda地址
	 */
	public String getPdaMac() {
		return pdaMac;
	}

}
