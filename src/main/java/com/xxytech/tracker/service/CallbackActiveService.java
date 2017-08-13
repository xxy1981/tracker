package com.xxytech.tracker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xxytech.tracker.entity.CallbackActivate;

public interface CallbackActiveService {
	Page<CallbackActivate> findBySidAndPartnerId(String sid, String partnerId, Pageable pageable);
	void save(CallbackActivate callbackActive);
}
