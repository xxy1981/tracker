package com.xxytech.tracker.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xxytech.tracker.entity.Tracker;

public interface TrackerRepository extends PagingAndSortingRepository<Tracker, String>, JpaSpecificationExecutor<Tracker>{

	Page<Tracker> findBySidAndChannelAndPartnerId(String sid, String channel, String partnerId, Pageable pageable);
	
    
}
