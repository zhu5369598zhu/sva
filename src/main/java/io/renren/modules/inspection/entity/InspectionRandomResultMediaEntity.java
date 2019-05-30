package io.renren.modules.inspection.entity;

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
@TableName("tb_inspection_random_result_media")
public class InspectionRandomResultMediaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer id;
	/**
	 * guid
	 */
	private String guid;
	/**
	 * 巡检结果id
	 */
	private Integer resultId;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 媒体数据
	 */
	private byte[] content;
	/**
	 * 上传时间
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
	 * 设置：巡检结果id
	 */
	public void setResultId(Integer resultId) {
		this.resultId = resultId;
	}
	/**
	 * 获取：巡检结果id
	 */
	public Integer getResultId() {
		return resultId;
	}
	/**
	 * 设置：类型
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：类型
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：媒体数据
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}
	/**
	 * 获取：媒体数据
	 */
	public byte[] getContent() {
		return content;
	}
	/**
	 * 设置：上传时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：上传时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
}
