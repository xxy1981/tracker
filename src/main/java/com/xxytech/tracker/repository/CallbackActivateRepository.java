package com.xxytech.tracker.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xxytech.tracker.entity.CallbackActivate;

public interface CallbackActivateRepository extends PagingAndSortingRepository<CallbackActivate, String>{

	Page<CallbackActivate> findBySidAndPartnerId(String sid, String partnerId, Pageable pageable);
	
	Page<CallbackActivate> findBySid(String sid, Pageable pageable);
	
	Page<CallbackActivate> findByPartnerId(String partnerId, Pageable pageable);
	
    
}
