package com.xxytech.tracker.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import com.xxytech.tracker.entity.Tracker;
import com.xxytech.tracker.http.HttpClientFactory;
import com.xxytech.tracker.http.OkHttpFactory;
import com.xxytech.tracker.service.HttpService;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service("httpService")
public class HttpServiceImpl implements HttpService{
	private static final Logger logger = LoggerFactory.getLogger(HttpServiceImpl.class);
	
	private static final String HTTP_CHANNEL_OK_HTTP = "okhttp";
	private static final String HTTP_CHANNEL_HTTP_CLIENT = "httpclient";
	
	@Value("${http.pool.enable:true}")
    private boolean	httpPoolEnable;
	@Value("${http.channel:okhttp}")
    private String httpChannel;
	
	@Override
	public void httpPostCall(Campaign campaign, Tracker tracker) {
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
            nvps.add(new BasicNameValuePair("sid", tracker.getSid()));
            if(StringUtils.isNotBlank(tracker.getIdfa())){
            	nvps.add(new BasicNameValuePair("idfa", tracker.getIdfa()));
            }else{
            	nvps.add(new BasicNameValuePair("androidid_sha1", tracker.getO1()));
            }
            nvps.add(new BasicNameValuePair("action", "none"));	
            nvps.add(new BasicNameValuePair("clicktime", String.valueOf(tracker.getCreateTime().getTime())));
            nvps.add(new BasicNameValuePair("ip", tracker.getIp()));
            nvps.add(new BasicNameValuePair("useragent", tracker.getUa()));
            
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            response = httpclient.execute(httpPost);
            String content = IOUtils.toString(response.getEntity().getContent());
            int statusCode = response.getStatusLine().getStatusCode();
            if (200 == statusCode) {
                logger.info("###################### Successful call chn[{}], idfa[{}], sid[{}], ip[{}], url[{}], ua[{}], http status is [{}], return content is\r\n{}", 
                		campaign.getChannel(), tracker.getIdfa(), tracker.getSid(), tracker.getIp(), campaign.getThirdUrl(), tracker.getUa(), statusCode, content);
            } else {
                logger.error("###################### Failed call chn[{}], idfa[{}], sid[{}], ip[{}], url[{}], ua[{}], http status is [{}], return content is\r\n{}", 
                		campaign.getChannel(), tracker.getIdfa(), tracker.getSid(), tracker.getIp(), campaign.getThirdUrl(), tracker.getUa(), statusCode, content);
            }
        } catch (Exception e) {
            logger.error("###################### Failed call chn[" + campaign.getChannel() + "], idfa[" + tracker.getIdfa() + "], sid[" + tracker.getSid() + "], ip[" + tracker.getIp() + "], url[" + campaign.getThirdUrl() + "], ua[" + tracker.getUa() + "]", e);
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
	public void httpGetCall(Campaign campaign, Tracker tracker) {
		if(HTTP_CHANNEL_OK_HTTP.equalsIgnoreCase(httpChannel)){
		    httpGetCallByOkHttp(campaign, tracker);
		}else{
		    httpGetCallByHttpClient(campaign, tracker);
		}
	}

	@Override
	public void activeCallbackFeeback(Campaign campaign, String status, String message, Integer code) {
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
                logger.info("###################### Successful active feeback data[{}], url[{}], http status is [{}], return content is\r\n{}", 
                		data, campaign.getThirdUrl(), statusCode, content);
            } else {
                logger.error("###################### Failed active feeback data[{}], url[{}], http status is [{}], return content is\r\n{}", 
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

    private void httpGetCallByOkHttp(Campaign campaign, Tracker tracker) {
        String ulr = buildGetUrl(campaign, tracker);
        
        OkHttpClient okHttpClient;
        if(httpPoolEnable){
            okHttpClient = OkHttpFactory.getOkHttpClient();
        }else{
            okHttpClient = OkHttpFactory.getNewOkHttpClient();
        }
        
        Request request = new Request.Builder().url(ulr).addHeader("Connection", "close").build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            String content = response.body().string();
            if(response.isSuccessful()){
                logger.info("###################### Successful call chn[{}], idfa[{}], sid[{}], ip[{}], url[{}], ua[{}], http status is [{}], return content is\r\n{}", 
                        campaign.getChannel(), tracker.getIdfa(), tracker.getSid(), tracker.getIp(), campaign.getThirdUrl(), tracker.getUa(), response.code(), content);
            } else {
                logger.error("###################### Failed call chn[{}], idfa[{}], sid[{}], ip[{}], url[{}], ua[{}], http status is [{}], return content is\r\n{}", 
                        campaign.getChannel(), tracker.getIdfa(), tracker.getSid(), tracker.getIp(), campaign.getThirdUrl(), tracker.getUa(), response.code(), content);
            }
        } catch (IOException e) {
            logger.error("###################### Failed call chn[" + campaign.getChannel() + "], idfa[" + tracker.getIdfa() + "], sid[" + tracker.getSid() + "], ip[" + tracker.getIp() + "], url[" + campaign.getThirdUrl() + "], ua[" + tracker.getUa() + "]", e);
        }
    }
    
    private void httpGetCallByHttpClient(Campaign campaign, Tracker tracker) {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        if(httpPoolEnable){
            httpclient = HttpClientFactory.getHttpClient(null); 
        }else{
            httpclient = HttpClientFactory.getNewHttpClient(null);
        }

        try {
            String ulr = buildGetUrl(campaign, tracker);
            HttpGet httpGet = new HttpGet(ulr);//https://lnk0.com/URhcE9?chn=Inmobi&idfa=$IDA&sid=$SID&ip=$USER_IP
            response = httpclient.execute(httpGet);
            String content = IOUtils.toString(response.getEntity().getContent());
            int statusCode = response.getStatusLine().getStatusCode();
            if (200 == statusCode) {
                logger.info("###################### Successful call chn[{}], idfa[{}], sid[{}], ip[{}], url[{}], ua[{}], http status is [{}], return content is\r\n{}", 
                        campaign.getChannel(), tracker.getIdfa(), tracker.getSid(), tracker.getIp(), campaign.getThirdUrl(), tracker.getUa(), statusCode, content);
            } else {
                logger.error("###################### Failed call chn[{}], idfa[{}], sid[{}], ip[{}], url[{}], ua[{}], http status is [{}], return content is\r\n{}", 
                        campaign.getChannel(), tracker.getIdfa(), tracker.getSid(), tracker.getIp(), campaign.getThirdUrl(), tracker.getUa(), statusCode, content);
            }
        } catch (Exception e) {
            logger.error("###################### Failed call chn[" + campaign.getChannel() + "], idfa[" + tracker.getIdfa() + "], sid[" + tracker.getSid() + "], ip[" + tracker.getIp() + "], url[" + campaign.getThirdUrl() + "], ua[" + tracker.getUa() + "]", e);
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

    /**
     * 构件
     * @param campaign
     * @param tracker
     * @return
     */
    private String buildGetUrl(Campaign campaign, Tracker tracker) {
        StringBuffer ulr = new StringBuffer(campaign.getThirdUrl()).append("?action=none");
        ulr.append("&chn=").append(campaign.getChannel());
        ulr.append("&sid=").append(tracker.getSid());
        if(StringUtils.isNotBlank(tracker.getIdfa())){
            ulr.append("&idfa=").append(tracker.getIdfa());
        }else{
            ulr.append("&androidid_sha1=").append(tracker.getO1());
        }
        ulr.append("&clicktime=").append(String.valueOf(tracker.getCreateTime().getTime()));
        ulr.append("&ip=").append(tracker.getIp());
        try {
            ulr.append("&useragent=").append(URLEncoder.encode(tracker.getUa(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        logger.warn("###################### Send url[{}]", ulr.toString());
        return ulr.toString();
    }

}
