package com.xxytech.tracker.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xxytech.tracker.entity.Campaign;
import com.xxytech.tracker.entity.Tracker;
import com.xxytech.tracker.enums.DeviceType;
import com.xxytech.tracker.service.CampaignService;
import com.xxytech.tracker.service.HttpService;
import com.xxytech.tracker.service.PartnerService;
import com.xxytech.tracker.service.TrackerService;
import com.xxytech.tracker.utils.GeneralResponse;
import com.xxytech.tracker.utils.PageHtmlDisplay;

@Controller
@RequestMapping("/")
public class TrackerController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(TrackerController.class);
	
    @Autowired
    private TrackerService trackerService;
    @Autowired
    private PartnerService partnerService;
    @Autowired
    private CampaignService campaignService;
    @Autowired
    private HttpService httpService;

    @RequestMapping(value = "/tracker", method = RequestMethod.GET)
    public ModelAndView query(@RequestParam(value = "sid", defaultValue="", required = false) String sid,
                              @RequestParam(value = "channel", defaultValue="", required = false) String channel,
                              @RequestParam(value = "partnerId", defaultValue="", required = false) String partnerId,
                              @RequestParam(value = "pageNo", defaultValue="0", required = false) String pageNoStr, 
                              ModelMap model) {

        supportEnum(model);
        supportJavaMethod(model);
        
        Integer pageNo = getPageNo(pageNoStr);
        // 查询条件回填
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("sid", sid == null ? "" : sid);
        model.addAttribute("channel", channel == null ? "" : channel);
        model.addAttribute("partnerId", partnerId == null ? "" : partnerId);
        model.addAttribute("parters", partnerService.findAll());

        Sort sort = new Sort(Direction.DESC,"createTime");
        PageRequest pr = new PageRequest(getPageNo(pageNoStr), getPageSize("10"), sort);
        Page<Tracker> page = trackerService.findBySidAndChannelAndPartnerId(sid, channel, partnerId, pr);
        model.addAttribute("page", page);
        model.addAttribute("list", page.getContent());
        model.addAttribute("pageHtmlDisplay", PageHtmlDisplay.display(page));
        
        return new ModelAndView("console/tracker_list");
    }
    
    @RequestMapping(value = "/{campaignId}")
    @ResponseBody
    public GeneralResponse serveAuto(@PathVariable(value = "campaignId", required = true) String campaignId,
           				@RequestParam(value = "action", defaultValue="click", required = false) String action,
           				@RequestParam(value = "sid", defaultValue="", required = false) String sid,
                       	@RequestParam(value = "idfa", defaultValue="", required = false) String idfa,
                       	@RequestParam(value = "imei", defaultValue="", required = false) String imei,
                        @RequestParam(value = "androidIdSha1", defaultValue="", required = false) String androidIdSha1,
                        @RequestParam(value = "androidIdMd5", defaultValue="", required = false) String androidIdMd5,
                       	@RequestParam(value = "subChn", defaultValue="", required = false) String subChn,
                       	//@RequestHeader(value = "X-FORWARDED-FOR", required = false) String ip, //X-Forward-for
                       	//@RequestHeader(value = "User-Agent", required = false) String ua, //x-device-user-agent
                       	@RequestParam(value = "ip", defaultValue="", required = false) String ip,
                        @RequestParam(value = "ua", defaultValue="", required = false) String ua,
                        @RequestParam(value = "clickTime", defaultValue="", required = false) String clickTime,
                       	HttpServletRequest httpRequest,
                       	HttpServletResponse httpResponse
                      	) {
    	Campaign campaign = campaignService.getCampaign(campaignId);
    	if(campaign == null){
    	    return new GeneralResponse("ko", "related campaign record not found", HttpStatus.NOT_FOUND.value());
    	}
    	
    	Tracker tracker = saveTracker(campaign, sid, idfa, androidIdSha1, androidIdMd5, imei, subChn, ip, ua, clickTime, httpRequest);
    	
    	try {
    	    httpService.httpGetCall(campaign, tracker);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new GeneralResponse("ko", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        
    	return new GeneralResponse("ok", null, HttpStatus.OK.value());
    }
    
    @RequestMapping(value = "/serve")
    @ResponseBody
    public GeneralResponse serve(@RequestParam(value = "campaignId", defaultValue="", required = false) String campaignId,
    					@RequestParam(value = "action", defaultValue="click", required = false) String action,
           				@RequestParam(value = "sid", defaultValue="", required = false) String sid,
                       	@RequestParam(value = "idfa", defaultValue="", required = false) String idfa,
                       	@RequestParam(value = "imei", defaultValue="", required = false) String imei,
                        @RequestParam(value = "androidIdSha1", defaultValue="", required = false) String androidIdSha1,
                        @RequestParam(value = "androidIdMd5", defaultValue="", required = false) String androidIdMd5,
                       	@RequestParam(value = "subChn", defaultValue="", required = false) String subChn,
                       	//@RequestHeader(value = "X-FORWARDED-FOR", required = false) String ip, //X-Forward-for
                        //@RequestHeader(value = "User-Agent", required = false) String ua, //x-device-user-agent
                        @RequestParam(value = "ip", defaultValue="", required = false) String ip,
                        @RequestParam(value = "ua", defaultValue="", required = false) String ua,
                        @RequestParam(value = "clickTime", defaultValue="", required = false) String clickTime,
                       	HttpServletRequest httpRequest,
                       	HttpServletResponse httpResponse
                      	) {
    	Campaign campaign = campaignService.getCampaign(campaignId);
    	if(campaign == null){
    	    return new GeneralResponse("ko", "related campaign record not found", HttpStatus.NOT_FOUND.value());
    	}
        
    	Tracker tracker = saveTracker(campaign, sid, idfa, androidIdSha1, androidIdMd5, imei, subChn, ip, ua, clickTime, httpRequest);
    	
    	try {
            httpService.httpGetCall(campaign, tracker);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new GeneralResponse("ko", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        
    	return new GeneralResponse("ok", null, HttpStatus.OK.value());
    }
    
    @RequestMapping(value = "/serveTest")
    public String serveTest(@RequestParam(value = "campaignId", defaultValue="", required = false) String campaignId,
							@RequestParam(value = "action", defaultValue="click", required = false) String action,
							@RequestParam(value = "sid", defaultValue="", required = false) String sid,
							@RequestParam(value = "idfa", defaultValue="", required = false) String idfa,
							@RequestParam(value = "imei", defaultValue="", required = false) String imei,
	                        @RequestParam(value = "androidIdSha1", defaultValue="", required = false) String androidIdSha1,
	                        @RequestParam(value = "androidIdMd5", defaultValue="", required = false) String androidIdMd5,
							@RequestParam(value = "subChn", defaultValue="", required = false) String subChn,
	                        //@RequestHeader(value = "X-FORWARDED-FOR", required = false) String ip, //X-Forward-for
	                        //@RequestHeader(value = "User-Agent", required = false) String ua, //x-device-user-agent
	                        @RequestParam(value = "ip", defaultValue="", required = false) String ip,
	                        @RequestParam(value = "ua", defaultValue="", required = false) String ua,
	                        @RequestParam(value = "clickTime", defaultValue="", required = false) String clickTime,
							HttpServletRequest httpRequest,
							HttpServletResponse httpResponse
          	                ) {
		Campaign campaign = campaignService.getCampaign(campaignId);
		if(campaign == null){
			return "ko";
		}
		
		Tracker tracker = saveTracker(campaign, sid, idfa, androidIdSha1, androidIdMd5, imei, subChn, ip, ua, clickTime, httpRequest);
        
        httpService.httpGetCall(campaign, tracker);
	        
	    return "redirect:/tracker";
	}

    /**
     * 保存点击记录
     * @param campaign
     * @param sid
     * @param idfa
     * @param androidIdSha1
     * @param androidIdMd5
     * @param imei
     * @param subChn
     * @param ip
     * @param ua
     * @param clickTime
     * @param httpRequest
     */
    private Tracker saveTracker(Campaign campaign, String sid, String idfa, String androidIdSha1, String androidIdMd5, String imei, String subChn, String ip, String ua,
                             String clickTime, HttpServletRequest httpRequest) {
        try {
            String clinetIp = getIpAddr(httpRequest);
            String userAgent = getUserAgent(httpRequest);
            
            Tracker tracker = new Tracker();
            tracker.setSid(sid);
            tracker.setIdfa(idfa);
            tracker.setAndroidIdSha1(androidIdSha1);
            tracker.setAndroidIdMd5(androidIdMd5);
            tracker.setImei(imei);
            tracker.setCampaignId(campaign.getId());
            tracker.setChannel(campaign.getChannel());
            tracker.setSubChannel(subChn);
            tracker.setIp(StringUtils.isBlank(ip) ? clinetIp : ip);
            tracker.setUa(StringUtils.isBlank(ua) ? userAgent : ua);
            tracker.setPartnerId(campaign.getPartnerId());
            try {
                tracker.setCreateTime(new Date(Long.valueOf(clickTime)));
            } catch (Exception e) {
                tracker.setCreateTime(new Date());
            }
            if(StringUtils.isNotBlank(idfa)){
                tracker.setDeviceType(DeviceType.IOS.name());
            }else{
                tracker.setDeviceType(DeviceType.ANDROID.name());
            }
            
            trackerService.save(tracker);
            
            return tracker;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}
