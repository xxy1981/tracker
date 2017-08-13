package com.xxytech.tracker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xxytech.tracker.entity.Campaign;

public interface CampaignService {
	Page<Campaign> findByIdAndNameAndPartnerId(String id, String name, String partnerId, Pageable pageable);
	
	Campaign getCampaign(String id);
	
	Boolean add(Campaign campaign);
	
	Boolean update(Campaign campaign);
	
	void delete(String[] ids);
	
	Iterable<Campaign> findAll();
}
