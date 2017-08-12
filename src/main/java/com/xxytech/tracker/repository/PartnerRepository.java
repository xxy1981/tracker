package com.xxytech.tracker.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xxytech.tracker.entity.Partner;

public interface PartnerRepository extends PagingAndSortingRepository<Partner, String>{

	Page<Partner> findByIdAndName(String id, String name, Pageable pageable);
	Page<Partner> findById(String id, Pageable pageable);
	Page<Partner> findByName(String name, Pageable pageable);
    
}
