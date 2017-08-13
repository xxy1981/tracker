package com.xxytech.tracker.service;

import com.xxytech.tracker.entity.Campaign;

public interface HttpService {
	void httpPostCall(Campaign campaign, String sid, String idfa, String o1, String clinetIp, String ua);
	
	void httpGetCall(Campaign campaign, String sid, String idfa, String o1, String clinetIp, String ua);
	
	void activeFeeback(Campaign campaign, String status, String message, Integer code);
}
