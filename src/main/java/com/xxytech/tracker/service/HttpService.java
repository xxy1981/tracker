package com.xxytech.tracker.service;

public interface HttpService {
	void httpPostCall(String chn, String idfa, String impId, String clinetIp);
	
	void httpGetCall(String chn, String idfa, String impId, String clinetIp);
	
	void activeFeeback(String status, String message, Integer code);
}
