package com.xxytech.tracker.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xxytech.tracker.entity.CallbackActivate;
import com.xxytech.tracker.repository.CallbackActivateRepository;
import com.xxytech.tracker.service.CallbackActiveService;

@Service("callbackActiveService")
public class CallbackActiveServiceImpl implements CallbackActiveService{
	private static final Logger logger = LoggerFactory.getLogger(CallbackActiveServiceImpl.class);

	@Autowired
    private CallbackActivateRepository callbackActiveRepository;
	
	@Override
	public Page<CallbackActivate> findBySidAndPartnerId(String sid, String partnerId,
			Pageable pageable) {
		if(StringUtils.isNotBlank(sid) && StringUtils.isNotBlank(partnerId)){
			return callbackActiveRepository.findBySidAndPartnerId(sid, partnerId, pageable);
		}else{
			if(StringUtils.isNotBlank(sid) && StringUtils.isBlank(partnerId)){
				return callbackActiveRepository.findBySid(sid, pageable);
			}else if(StringUtils.isBlank(sid) && StringUtils.isNotBlank(partnerId)){
				return callbackActiveRepository.findByPartnerId(partnerId, pageable);
			}else{
				return callbackActiveRepository.findAll(pageable);
			}
		}
		
	}

	@Override
	public void save(CallbackActivate callbackActive) {
		callbackActiveRepository.save(callbackActive);
	}

}
