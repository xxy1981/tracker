package com.xxytech.tracker.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tracker")
public class Tracker implements Serializable {

    private static final long serialVersionUID = 8127035730921338189L;

    @Id
    @Column(nullable = false, name="sid")
    private String sid;

    @Column(name="idfa")
    private String idfa;
    
    @Column(name="imei")
    private String imei;
    
    @Column(name="androidid_md5")
    private String androidIdMd5;
    
    @Column(name="androidid_sha1")
    private String androidIdSha1;
    
    @Column(name="campaign_id")
    private String campaignId;
    
    @Column(name="channel")
    private String channel;
    
    @Column(name="sub_channel")
    private String subChannel;
    
    @Column(name="ip")
    private String ip;
    
    @Column(name="ua")
    private String ua;
    
    @Column(name="device_type")
    private String deviceType;
    
    @Column(name="partner_id")
    private String partnerId;
    
    @Column(name="create_time")
    private Date createTime;

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getIdfa() {
		return idfa;
	}

	public void setIdfa(String idfa) {
		this.idfa = idfa;
	}
	
    public String getImei() {
        return imei;
    }
    
    public void setImei(String imei) {
        this.imei = imei;
    }
    
    public String getAndroidIdMd5() {
        return androidIdMd5;
    }
    
    public void setAndroidIdMd5(String androidIdMd5) {
        this.androidIdMd5 = androidIdMd5;
    }
    
    public String getAndroidIdSha1() {
        return androidIdSha1;
    }
    
    public void setAndroidIdSha1(String androidIdSha1) {
        this.androidIdSha1 = androidIdSha1;
    }

    public String getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getSubChannel() {
		return subChannel;
	}

	public void setSubChannel(String subChannel) {
		this.subChannel = subChannel;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUa() {
		return ua;
	}

	public void setUa(String ua) {
		this.ua = ua;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

    
}