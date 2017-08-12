package com.xxytech.tracker.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "partner")
public class Partner implements Serializable {

    private static final long serialVersionUID = -8862513111593793616L;

	@Id
    @Column(nullable = false, name="id")
    private String id;

    @Column(name="name")
    private String name;
    
    @Column(name="property_id")
    private String propertyId;
    
    @Column(name="chn")
    private String chn;
    
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

	public String getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	public String getChn() {
		return chn;
	}

	public void setChn(String chn) {
		this.chn = chn;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}