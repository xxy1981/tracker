package com.xxytech.tracker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xxytech.tracker.entity.Tracker;

public interface TrackerService {
	Page<Tracker> findBySidAndChannelAndPartnerId(String sid, String channel, String partnerId, Pageable pageable);
	
	void save(Tracker tracker);
	
	Tracker getTracker(String sid);
}
