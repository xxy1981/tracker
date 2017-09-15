package com.xxytech.tracker.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "callback_activate")
public class CallbackActivate implements Serializable {

    private static final long serialVersionUID = 8127035730921331234L;

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
    
    @Column(name="view_attributed")
    private String viewAttributed;
    
    @Column(name="app_id")
    private String appId;
    
    @Column(name="ip")
    private String ip;
    
    @Column(name="ua")
    private String ua;
    
    @Column(name="event_name")
    private String eventName;
    
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

	public String getViewAttributed() {
		return viewAttributed;
	}

	public void setViewAttributed(String viewAttributed) {
		this.viewAttributed = viewAttributed;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
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

	
}