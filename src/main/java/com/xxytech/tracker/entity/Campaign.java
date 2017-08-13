package com.xxytech.tracker.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "campaign")
public class Campaign implements Serializable {

    private static final long serialVersionUID = 7897005703647531845L;

	@Id
    @Column(nullable = false, name="id")
    private String id;

    @Column(name="name")
    private String name;
    
    @Column(name="third_code")
    private String thirdCode;
    
    @Column(name="third_name")
    private String thirdName;
    
    @Column(name="third_url")
    private String thirdUrl;
    
    @Column(name="app_id")
    private String appId;
    
    @Column(name="app_name")
    private String appName;
    
    @Column(name="channel")
    private String channel;
    
    @Column(name="partner_id")
    private String partnerId;
    
    @Column(name="partner_name")
    private String partnerName;
    
    @Column(name="url")
    private String url;
    
    @Column(name="create_time")
    private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getThirdCode() {
		return thirdCode;
	}

	public void setThirdCode(String thirdCode) {
		this.thirdCode = thirdCode;
	}

	public String getThirdName() {
		return thirdName;
	}

	public void setThirdName(String thirdName) {
		this.thirdName = thirdName;
	}

	public String getThirdUrl() {
		return thirdUrl;
	}

	public void setThirdUrl(String thirdUrl) {
		this.thirdUrl = thirdUrl;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}