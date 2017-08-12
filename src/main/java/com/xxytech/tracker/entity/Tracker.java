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
    @Column(nullable = false, name="imp_id")
    private String impId;

    @Column(name="idfa")
    private String idfa;
    
    @Column(name="o1")
    private String o1;
    
    @Column(name="site_id")
    private String siteId;
    
    @Column(name="ip")
    private String ip;
    
    @Column(name="ua")
    private String ua;
    
    @Column(name="device_type")
    private String deviceType;
    
    @Column(name="tracking_partner")
    private String trackingPartner;
    
    @Column(name="create_time")
    private Date createTime;

	public String getImpId() {
		return impId;
	}

	public void setImpId(String impId) {
		this.impId = impId;
	}

	public String getIdfa() {
		return idfa;
	}

	public void setIdfa(String idfa) {
		this.idfa = idfa;
	}

	public String getO1() {
		return o1;
	}

	public void setO1(String o1) {
		this.o1 = o1;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
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

	public String getTrackingPartner() {
		return trackingPartner;
	}

	public void setTrackingPartner(String trackingPartner) {
		this.trackingPartner = trackingPartner;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}