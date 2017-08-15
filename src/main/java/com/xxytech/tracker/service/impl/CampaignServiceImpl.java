package com.xxytech.tracker.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.xxytech.tracker.entity.Campaign;
import com.xxytech.tracker.repository.CampaignRepository;
import com.xxytech.tracker.repository.CampaignSpecifications;
import com.xxytech.tracker.service.CampaignService;

@Service("campaignService")
public class CampaignServiceImpl implements CampaignService{
	private static final Logger logger = LoggerFactory.getLogger(CampaignServiceImpl.class);
	
	@Autowired
    private CampaignRepository campaignRepository;

	@Override
	public Page<Campaign> findByIdAndNameAndPartnerId(String id, String name, String partnerId, Pageable pageable) {
		Specification<Campaign> trackerSpecification = CampaignSpecifications.buildQuery(id, name, partnerId);
		return campaignRepository.findAll(trackerSpecification, pageable);
	}

	@Override
	public Campaign getCampaign(String id) {
		return campaignRepository.findOne(id);
	}

	@Override
	public Boolean add(Campaign campaign) {
		campaignRepository.save(campaign);
		return true;
	}

	@Override
	public Boolean update(Campaign campaign) {
		campaignRepository.save(campaign);
		return true;
	}

	@Override
	public void delete(String[] ids) {
		if(ids != null){
			for(String id : ids){
				campaignRepository.delete(id);
			}
		}
	}

	@Override
	public Iterable<Campaign> findAll() {
		Iterable<Campaign> iterable = campaignRepository.findAll();
		List<Campaign> list = new ArrayList<Campaign>();  
		iterable.forEach(campaign ->{list.add(campaign);});
		return list;
	}

}
