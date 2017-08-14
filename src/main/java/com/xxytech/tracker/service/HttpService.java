package com.xxytech.tracker.service;

import com.xxytech.tracker.entity.Campaign;
import com.xxytech.tracker.entity.Tracker;

public interface HttpService {
	void httpPostCall(Campaign campaign, Tracker tracker);
	
	void httpGetCall(Campaign campaign, Tracker tracker);
	
	void activeCallbackFeeback(Campaign campaign, String status, String message, Integer code);
}
