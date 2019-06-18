package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息表
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-05-10 08:42:48
 */
@TableName("tb_news")
public class NewsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 消息id
	 */
	@TableId
	private Integer newsId;
	/**
	 * 消息人
	 */
	private Integer userId;
	/**
	 * 消息名称
	 */
	private String newsName;
	/**
	 * 消息类型(0没有消息(消息已解决)，1待确认班长日志，2驳回的 班长日志)
	 */
	private Integer newsType;
	/**
	 * 消息类型名称
	 */
	@TableField(exist = false)
	private String newsTypeName;
	/**
	 * 待处理编号（日志编号，工单编号）
	 */
	private String newsNumber;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 设置：消息id
	 */
	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}
	/**
	 * 获取：消息id
	 */
	public Integer getNewsId() {
		return newsId;
	}
	/**
	 * 设置：消息人
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 获取：消息人
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * 设置：消息名称
	 */
	public void setNewsName(String newsName) {
		this.newsName = newsName;
	}
	/**
	 * 获取：消息名称
	 */
	public String getNewsName() {
		return newsName;
	}
	/**
	 * 设置：消息类型(0没有消息(消息已解决)，1待确认班长日志，2驳回的 班长日志)
	 */
	public void setNewsType(Integer newsType) {
		this.newsType = newsType;
	}
	/**
	 * 获取：消息类型(0没有消息(消息已解决)，1待确认班长日志，2驳回的 班长日志)
	 */
	public Integer getNewsType() {
		return newsType;
	}
	/**
	 * 设置：消息类型名称
	 */
	public void setNewsTypeName(String newsTypeName) {
		this.newsTypeName = newsTypeName;
	}
	/**
	 * 获取：消息类型名称
	 */
	public String getNewsTypeName() {
		return newsTypeName;
	}
	/**
	 * 获取：待处理编号（日志编号，工单编号）
	 */
	public String getNewsNumber() {
		return newsNumber;
	}
	/**
	 * 设置：待处理编号（日志编号，工单编号）
	 */
	public void setNewsNumber(String newsNumber) {
		this.newsNumber = newsNumber;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：修改时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
