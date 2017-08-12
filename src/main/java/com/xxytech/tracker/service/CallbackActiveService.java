package com.xxytech.tracker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xxytech.tracker.entity.CallbackActivate;

public interface CallbackActiveService {
	Page<CallbackActivate> findByImpIdAndTrackingPartner(String impId, String trackingPartner, Pageable pageable);
	void save(CallbackActivate callbackActive);
}
