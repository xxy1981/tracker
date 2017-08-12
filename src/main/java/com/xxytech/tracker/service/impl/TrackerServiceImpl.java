package com.xxytech.tracker.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.xxytech.tracker.entity.Tracker;
import com.xxytech.tracker.repository.TrackerRepository;
import com.xxytech.tracker.repository.TrackerSpecifications;
import com.xxytech.tracker.service.TrackerService;

@Service("trackerService")
public class TrackerServiceImpl implements TrackerService{
	private static final Logger logger = LoggerFactory.getLogger(TrackerServiceImpl.class);
	
	@Value("${talking.data.url}")
    private String	talkingDataUrl;
	@Value("${httpclinet.pool.enable:true}")
    private boolean	httpPoolEnable;
	
	@Autowired
    private TrackerRepository trackerRepository;
	
	@Override
	public Page<Tracker> findByImpIdAndSiteIdAndTrackingPartner(String impId, String siteId, String trackingPartner,
			Pageable pageable) {
		Specification<Tracker> trackerSpecification = TrackerSpecifications.buildQuery(impId, siteId, trackingPartner);
		return trackerRepository.findAll(trackerSpecification, pageable);
	}

	@Override
	public void save(Tracker tracker) {
		trackerRepository.save(tracker);
	}

}
