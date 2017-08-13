package com.xxytech.tracker.service.impl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
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
import com.xxytech.tracker.entity.Campaign;
import com.xxytech.tracker.http.HttpClientFactory;
import com.xxytech.tracker.service.HttpService;

@Service("httpService")
public class HttpServiceImpl implements HttpService{
	private static final Logger logger = LoggerFactory.getLogger(HttpServiceImpl.class);
	
	@Value("${httpclinet.pool.enable:true}")
    private boolean	httpPoolEnable;
	
	@Override
	public void httpPostCall(Campaign campaign, String sid, String idfa, String o1, String clinetIp, String ua) {
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		if(httpPoolEnable){
			httpclient = HttpClientFactory.getHttpClient(null); 
		}else{
			httpclient = HttpClientFactory.getNewHttpClient(null);
		}

        try {
        	HttpPost httpPost = new HttpPost(campaign.getThirdUrl());//https://lnk0.com/URhcE9?chn=Inmobi&idfa=$IDFA&sid=$SID&ip=$USER_IP
        	
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            
            nvps.add(new BasicNameValuePair("chn", campaign.getChannel()));
            nvps.add(new BasicNameValuePair("sid", sid));
            if(StringUtils.isNotBlank(idfa)){
            	nvps.add(new BasicNameValuePair("idfa", idfa));
            }else{
            	nvps.add(new BasicNameValuePair("androidid_sha1", o1));
            }
            nvps.add(new BasicNameValuePair("action", "none"));	
            nvps.add(new BasicNameValuePair("clicktime", String.valueOf(System.currentTimeMillis())));
            nvps.add(new BasicNameValuePair("ip", clinetIp));
            nvps.add(new BasicNameValuePair("useragent", ua));
            
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            response = httpclient.execute(httpPost);
            String content = IOUtils.toString(response.getEntity().getContent());
            int statusCode = response.getStatusLine().getStatusCode();
            if (200 == statusCode) {
                logger.info("###################### Successful call chn[{}], idfa[{}], sid[{}], ip[{}], url [{}], ua [{}], http status is [{}], return content is\r\n{}", 
                		campaign.getChannel(), idfa, sid, clinetIp, campaign.getThirdUrl(), ua, statusCode, content);
            } else {
                logger.error("###################### Failed call chn[{}], idfa[{}], sid[{}], ip[{}], url [{}], ua [{}], http status is [{}], return content is\r\n{}", 
                		campaign.getChannel(), idfa, sid, clinetIp, campaign.getThirdUrl(), ua, statusCode, content);
            }
        } catch (Exception e) {
            logger.error("###################### Failed call chn[" + campaign.getChannel() + "], idfa[" + idfa + "], sid[" + sid + "], ip[" + clinetIp + "], url[" + campaign.getThirdUrl() + "], ua[" + ua + "]", e);
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
	public void httpGetCall(Campaign campaign, String sid, String idfa, String o1, String clinetIp, String ua) {
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		if(httpPoolEnable){
			httpclient = HttpClientFactory.getHttpClient(null); 
		}else{
			httpclient = HttpClientFactory.getNewHttpClient(null);
		}

        try {
        	StringBuffer ulr = new StringBuffer(campaign.getThirdUrl()).append("?chn=").append(campaign.getChannel());
        	ulr.append("&sid=").append(sid);
        	if(StringUtils.isNotBlank(idfa)){
        		ulr.append("&idfa=").append(idfa);
            }else{
            	ulr.append("&androidid_sha1=").append(o1);
            }
        	ulr.append("&action=").append("none");
        	ulr.append("&clicktime=").append(String.valueOf(System.currentTimeMillis()));
        	ulr.append("&ip=").append(clinetIp);
        	ulr.append("&useragent=").append(ua);
        	
        	HttpGet httpGet = new HttpGet(ulr.toString());//https://lnk0.com/URhcE9?chn=Inmobi&idfa=$IDA&sid=$SID&ip=$USER_IP
        	response = httpclient.execute(httpGet);
            String content = IOUtils.toString(response.getEntity().getContent());
            int statusCode = response.getStatusLine().getStatusCode();
            if (200 == statusCode) {
                logger.info("###################### Successful call chn[{}], idfa[{}], sid[{}], ip[{}], url [{}], ua [{}], http status is [{}], return content is\r\n{}", 
                		campaign.getChannel(), idfa, sid, clinetIp, campaign.getThirdUrl(), ua, statusCode, content);
            } else {
                logger.error("###################### Failed call chn[{}], idfa[{}], sid[{}], ip[{}], url [{}], ua [{}], http status is [{}], return content is\r\n{}", 
                		campaign.getChannel(), idfa, sid, clinetIp, campaign.getThirdUrl(), ua, statusCode, content);
            }
        } catch (Exception e) {
            logger.error("###################### Failed call chn[" + campaign.getChannel() + "], idfa[" + idfa + "], sid[" + sid + "], ip[" + clinetIp + "], url[" + campaign.getThirdUrl() + "], ua[" + ua + "]", e);
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
	public void activeFeeback(Campaign campaign, String status, String message, Integer code) {
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
        	HttpPost httpPost = new HttpPost(campaign.getThirdUrl());//https://lnk0.com/URhcE9?chn=Inmobi&idfa=$IDA&impId=$IMP_ID&ip=$USER_IP
            
            httpPost.addHeader("Content-type","application/json; charset=utf-8");  
            httpPost.setHeader("Accept", "application/json");  
            httpPost.setEntity(new StringEntity(data, Charset.forName("UTF-8")));
            
            response = httpclient.execute(httpPost);
            String content = IOUtils.toString(response.getEntity().getContent());
            int statusCode = response.getStatusLine().getStatusCode();
            if (200 == statusCode) {
                logger.info("###################### Successful active feeback data[{}], url [{}], http status is [{}], return content is\r\n{}", 
                		data, campaign.getThirdUrl(), statusCode, content);
            } else {
                logger.error("###################### Failed active feeback data[{}], url [{}], http status is [{}], return content is\r\n{}", 
                		data, campaign.getThirdUrl(), statusCode, content);
            }
        } catch (Exception e) {
            logger.error("###################### Failed active feeback data[" + data + "], url[" + campaign.getThirdUrl() + "]", e);
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
