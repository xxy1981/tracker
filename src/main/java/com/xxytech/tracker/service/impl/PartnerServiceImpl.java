package com.xxytech.tracker.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xxytech.tracker.entity.Partner;
import com.xxytech.tracker.repository.PartnerRepository;
import com.xxytech.tracker.service.PartnerService;
import com.xxytech.tracker.utils.CacheUtils;

@Service("partnerService")
public class PartnerServiceImpl implements PartnerService{
	private static final Logger logger = LoggerFactory.getLogger(PartnerServiceImpl.class);

	@Autowired
    private PartnerRepository partnerRepository;
	
	@PostConstruct
	private void init(){
		partnerRepository.findAll().forEach(p -> CacheUtils.putPartner(p));
	}
	
	@Override
	public Page<Partner> findByIdAndName(String id, String name, Pageable pageable) {
		if(StringUtils.isNotBlank(id) && StringUtils.isNotBlank(name)){
			return partnerRepository.findByIdAndName(id, name, pageable);
		}else{
			if(StringUtils.isNotBlank(id) && StringUtils.isBlank(name)){
				return partnerRepository.findById(id, pageable);
			}else if(StringUtils.isBlank(id) && StringUtils.isNotBlank(name)){
				return partnerRepository.findByName(name, pageable);
			}else{
				return partnerRepository.findAll(pageable);
			}
		}
	}

	@Override
	public Partner getPartner(String id) {
		return partnerRepository.findOne(id);
	}

	@Override
	public Boolean add(Partner partner) {
		partnerRepository.save(partner);
		CacheUtils.putPartner(partner);
		return true;
	}

	@Override
	public Boolean update(Partner partner) {
		partnerRepository.save(partner);
		CacheUtils.putPartner(partner);
		return true;
	}

	@Override
	public void delete(String[] ids) {
		if(ids != null){
			for(String id : ids){
				partnerRepository.delete(id);
				CacheUtils.removePartner(id);
			}
		}
	}

	@Override
	public List<Partner> findAll() {
		Iterable<Partner> iterable = partnerRepository.findAll();
		List<Partner> list = new ArrayList<Partner>();  
		iterable.forEach(partner ->{list.add(partner);});
		return list;
	}

}
