package com.xxytech.tracker.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xxytech.tracker.entity.CallbackActivate;

public interface CallbackActivateRepository extends PagingAndSortingRepository<CallbackActivate, String>{

	Page<CallbackActivate> findByImpIdAndTrackingPartner(String impId, String trackingPartner, Pageable pageable);
	
	Page<CallbackActivate> findByImpId(String impId, Pageable pageable);
	
	Page<CallbackActivate> findByTrackingPartner(String trackingPartner, Pageable pageable);
	
    
}
