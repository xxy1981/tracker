package com.xxytech.tracker.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xxytech.tracker.entity.Campaign;

public interface CampaignRepository extends PagingAndSortingRepository<Campaign, String>, JpaSpecificationExecutor<Campaign>{

	Page<Campaign> findByIdAndNameAndPartnerId(String id, String name, String partnerId, Pageable pageable);
	
}
