package com.xxytech.tracker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xxytech.tracker.entity.Partner;

public interface PartnerService {
	
	Page<Partner> findByIdAndName(String id, String name, Pageable pageable);
	
	Partner getPartner(String id);
	
	Boolean add(Partner partner);
	
	Boolean update(Partner partner);
	
	void delete(String[] ids);
	
	Iterable<Partner> findAll();
}
