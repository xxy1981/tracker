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
    @Column(nullable = false, name="imp_id")
    private String impId;

    @Column(name="idfa")
    private String idfa;
    
    @Column(name="o1")
    private String o1;
    
    @Column(name="view_attributed")
    private String viewAttributed;
    
    @Column(name="property_id")
    private String propertyId;
    
    @Column(name="event_name")
    private String eventName;
    
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

	public String getViewAttributed() {
		return viewAttributed;
	}

	public void setViewAttributed(String viewAttributed) {
		this.viewAttributed = viewAttributed;
	}

	public String getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
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