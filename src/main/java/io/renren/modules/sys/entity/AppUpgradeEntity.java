package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author qiaosixun
 * @email qiaosixun@handweb.com
 * @date 2019-04-18 12:45:52
 */
@TableName("tb_app_update")
public class AppUpgradeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer id;
	/**
	 * 内网地址
	 */
	private String localhost;
	/**
	 * 是否强制更新
	 */
	private Integer isMust;
	/**
	 * 是否户用外网
	 */
	private Integer isDomain;
	/**
	 * 外网地址
	 */
	private String domain;
	/**
	 * app文件内容
	 */
	private byte[] appFile;
	/**
	 * app文件名
	 */
	private String appFilename;
	/**
	 * app文件大小
	 */
	private long appFilesize;
	/**
	 * app版本号
	 */
	private String appVersion;
	/**
	 * app内部版本号
	 */
	private Long appVersionCode;
	/**
	 * app下载二维码
	 */
	private byte[] appQrcode;
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
	 * 设置：内网地址
	 */
	public void setLocalhost(String localhost) {
		this.localhost = localhost;
	}
	/**
	 * 获取：内网地址
	 */
	public String getLocalhost() {
		return localhost;
	}
	/**
	 * 设置：是否户用外网
	 */
	public void setIsDomain(Integer isDomain) {
		this.isDomain = isDomain;
	}
	/**
	 * 获取：是否户用外网
	 */
	public Integer getIsDomain() {
		return isDomain;
	}
	/**
	 * 设置：是否强制更新
	 */
	public void setIsMust(Integer isMust) {
		this.isMust = isMust;
	}
	/**
	 * 获取：是否强制更新
	 */
	public Integer getIsMust() {
		return isMust;
	}

	/**
	 * 设置：外网地址
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}
	/**
	 * 获取：外网地址
	 */
	public String getDomain() {
		return domain;
	}
	/**
	 * 设置：app文件内容
	 */
	public void setAppFile(byte[] appFile) {
		this.appFile = appFile;
	}
	/**
	 * 获取：app文件内容
	 */
	public byte[] getAppFile() {
		return appFile;
	}
	/**
	 * 设置：app文件名
	 */
	public void setAppFilename(String appFilename) {
		this.appFilename = appFilename;
	}
	/**
	 * 获取：app文件名
	 */
	public String getAppFilename() {
		return appFilename;
	}
	/**
	 * 设置：app文件大小
	 */
	public void setAppFilesize(long appFilesize) {
		this.appFilesize = appFilesize;
	}
	/**
	 * 获取：app文件大小
	 */
	public long getAppFilesize() {
		return appFilesize;
	}
	/**
	 * 设置：app版本号
	 */
	public void setAppVersionCode(Long appVersionCode) {
		this.appVersionCode = appVersionCode;
	}
	/**
	 * 获取：app内部版本号
	 */
	public Long getAppVersionCode() {
		return appVersionCode;
	}
	/**
	 * 设置：app内部版本号
	 */
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	/**
	 * 获取：app版本号
	 */
	public String getAppVersion() {
		return appVersion;
	}
	/**
	 * 设置：app下载二维码
	 */
	public void setAppQrcode(byte[] appQrcode) {
		this.appQrcode = appQrcode;
	}
	/**
	 * 获取：app下载二维码
	 */
	public byte[] getAppQrcode() {
		return appQrcode;
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
