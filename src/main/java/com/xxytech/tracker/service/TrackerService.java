package com.xxytech.tracker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xxytech.tracker.entity.Tracker;

public interface TrackerService {
	Page<Tracker> findByImpIdAndSiteIdAndTrackingPartner(String impId, String siteId, String trackingPartner, Pageable pageable);
	
	void save(Tracker tracker);
}
