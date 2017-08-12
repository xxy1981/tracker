package com.xxytech.tracker.service.impl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xxytech.tracker.http.HttpClientFactory;
import com.xxytech.tracker.service.HttpService;

@Service("httpService")
public class HttpServiceImpl implements HttpService{
	private static final Logger logger = LoggerFactory.getLogger(HttpServiceImpl.class);
	
	@Value("${talking.data.url}")
    private String	talkingDataUrl;
	@Value("${httpclinet.pool.enable:true}")
    private boolean	httpPoolEnable;
	
	@Override
	public void httpPostCall(String chn, String idfa, String impId, String clinetIp) {
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		if(httpPoolEnable){
			httpclient = HttpClientFactory.getHttpClient(null); 
		}else{
			httpclient = HttpClientFactory.getNewHttpClient(null);
		}

        try {
        	HttpPost httpPost = new HttpPost(talkingDataUrl);//https://lnk0.com/URhcE9?chn=Inmobi&idfa=$IDA&impId=$IMP_ID&ip=$USER_IP
        	
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("chn", chn));
            nvps.add(new BasicNameValuePair("idfa", idfa));
            nvps.add(new BasicNameValuePair("impId", impId));
            nvps.add(new BasicNameValuePair("ip", clinetIp));
            
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            response = httpclient.execute(httpPost);
            String content = IOUtils.toString(response.getEntity().getContent());
            int statusCode = response.getStatusLine().getStatusCode();
            if (200 == statusCode) {
                logger.info("###################### Successful call chn[{}], idfa[{}], impId[{}], ip[{}], url [{}], http status is [{}], return content is\r\n{}", 
                		chn, idfa, impId, clinetIp, talkingDataUrl, statusCode, content);
            } else {
                logger.error("###################### Failed call chn[{}], idfa[{}], impId[{}], ip[{}], url [{}], http status is [{}], return content is\r\n{}", 
                		chn, idfa, impId, clinetIp, talkingDataUrl, statusCode, content);
            }
        } catch (Exception e) {
            logger.error("###################### Failed call chn[" + chn + "], idfa[" + idfa + "], impId[" + impId + "], ip[" + clinetIp + "], url[" + talkingDataUrl + "]", e);
        }finally{
        	if(response != null){
        		try {
        			response.close();
				} catch (IOException e) {
					logger.error("release(close) httpclient'response error :", e);
				}
        	}

            //没使用连接池的时候每次关掉client，使用连接池的时候不能关闭
        	if(!httpPoolEnable){
        		if(httpclient != null){
            		try {
                		httpclient.close();
        			} catch (IOException e) {
        				logger.error("release(close) httpclient error :", e);
        			}
        		}
        	}
        }
	}
	
	@Override
	public void httpGetCall(String chn, String idfa, String impId, String clinetIp) {
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		if(httpPoolEnable){
			httpclient = HttpClientFactory.getHttpClient(null); 
		}else{
			httpclient = HttpClientFactory.getNewHttpClient(null);
		}

        try {
        	HttpGet httpGet = new HttpGet(talkingDataUrl+"?chn=" + chn + "&idfa=" + idfa + "&impId=" + impId + "&ip=" + clinetIp);//https://lnk0.com/URhcE9?chn=Inmobi&idfa=$IDA&impId=$IMP_ID&ip=$USER_IP
        	response = httpclient.execute(httpGet);
            String content = IOUtils.toString(response.getEntity().getContent());
            int statusCode = response.getStatusLine().getStatusCode();
            if (200 == statusCode) {
                logger.info("###################### Successful call chn[{}], idfa[{}], impId[{}], ip[{}], url [{}], http status is [{}], return content is\r\n{}", 
                		chn, idfa, impId, clinetIp, talkingDataUrl, statusCode, content);
            } else {
                logger.error("###################### Failed call chn[{}], idfa[{}], impId[{}], ip[{}], url [{}], http status is [{}], return content is\r\n{}", 
                		chn, idfa, impId, clinetIp, talkingDataUrl, statusCode, content);
            }
        } catch (Exception e) {
            logger.error("###################### Failed call chn[" + chn + "], idfa[" + idfa + "], impId[" + impId + "], ip[" + clinetIp + "], url[" + talkingDataUrl + "]", e);
        }finally{
        	if(response != null){
        		try {
        			response.close();
				} catch (IOException e) {
					logger.error("release(close) httpclient'response error :", e);
				}
        	}

            //没使用连接池的时候每次关掉client，使用连接池的时候不能关闭
        	if(!httpPoolEnable){
        		if(httpclient != null){
            		try {
                		httpclient.close();
        			} catch (IOException e) {
        				logger.error("release(close) httpclient error :", e);
        			}
        		}
        	}
        }
	}

	@Override
	public void activeFeeback(String status, String message, Integer code) {
		JSONObject j = new JSONObject();  
        j.put("status", status);  
        j.put("message", message);
        j.put("code", code);
        String data = j.toJSONString();
        
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		if(httpPoolEnable){
			httpclient = HttpClientFactory.getHttpClient(null); 
		}else{
			httpclient = HttpClientFactory.getNewHttpClient(null);
		}

        try {
        	HttpPost httpPost = new HttpPost(talkingDataUrl);//https://lnk0.com/URhcE9?chn=Inmobi&idfa=$IDA&impId=$IMP_ID&ip=$USER_IP
        	
        	
            
            httpPost.addHeader("Content-type","application/json; charset=utf-8");  
            httpPost.setHeader("Accept", "application/json");  
            httpPost.setEntity(new StringEntity(data, Charset.forName("UTF-8")));
            
            response = httpclient.execute(httpPost);
            String content = IOUtils.toString(response.getEntity().getContent());
            int statusCode = response.getStatusLine().getStatusCode();
            if (200 == statusCode) {
                logger.info("###################### Successful active feeback data[{}], url [{}], http status is [{}], return content is\r\n{}", 
                		data, talkingDataUrl, statusCode, content);
            } else {
                logger.error("###################### Failed active feeback data[{}], url [{}], http status is [{}], return content is\r\n{}", 
                		data, talkingDataUrl, statusCode, content);
            }
        } catch (Exception e) {
            logger.error("###################### Failed active feeback data[" + data + "], url[" + talkingDataUrl + "]", e);
        }finally{
        	if(response != null){
        		try {
        			response.close();
				} catch (IOException e) {
					logger.error("release(close) httpclient'response error :", e);
				}
        	}

            //没使用连接池的时候每次关掉client，使用连接池的时候不能关闭
        	if(!httpPoolEnable){
        		if(httpclient != null){
            		try {
                		httpclient.close();
        			} catch (IOException e) {
        				logger.error("release(close) httpclient error :", e);
        			}
        		}
        	}
        }
		
	}

}
