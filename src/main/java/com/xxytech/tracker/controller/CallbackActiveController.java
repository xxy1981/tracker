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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xxytech.tracker.entity.CallbackActivate;
import com.xxytech.tracker.entity.Campaign;
import com.xxytech.tracker.entity.Tracker;
import com.xxytech.tracker.service.CallbackActiveService;
import com.xxytech.tracker.service.CampaignService;
import com.xxytech.tracker.service.PartnerService;
import com.xxytech.tracker.service.TrackerService;
import com.xxytech.tracker.utils.GeneralResponse;
import com.xxytech.tracker.utils.PageHtmlDisplay;

@Controller
@RequestMapping("/")
public class CallbackActiveController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(CallbackActiveController.class);
	
    @Autowired
    private PartnerService partnerService;
    @Autowired
    private CallbackActiveService callbackActiveService;
    @Autowired
    private TrackerService trackerService;
    @Autowired
    private CampaignService campaignService;

    @RequestMapping(value = "/callbackActive", method = RequestMethod.GET)
    public ModelAndView query(@RequestParam(value = "sid", defaultValue="", required = false) String sid,
                              @RequestParam(value = "partnerId", defaultValue="", required = false) String partnerId,
                              @RequestParam(value = "pageNo", defaultValue="0", required = false) String pageNoStr, 
                              ModelMap model) {

        supportEnum(model);
        supportJavaMethod(model);

        Integer pageNo = getPageNo(pageNoStr);
        // 查询条件回填
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("sid", sid == null ? "" : sid);
        model.addAttribute("partnerId", partnerId == null ? "" : partnerId);
        model.addAttribute("parters", partnerService.findAll());

        Sort sort = new Sort(Direction.DESC,"createTime");
        PageRequest pr = new PageRequest(getPageNo(pageNoStr), getPageSize("10"), sort);
        Page<CallbackActivate> page = callbackActiveService.findBySidAndPartnerId(sid, partnerId, pr);
        model.addAttribute("page", page);
        model.addAttribute("list", page.getContent());
        model.addAttribute("pageHtmlDisplay", PageHtmlDisplay.display(page));
        
        return new ModelAndView("console/callbackActive_list");
    }
    
    @RequestMapping(value = "/active")
    @ResponseBody
    public GeneralResponse serve(@RequestParam(value = "sid", defaultValue="", required = true) String sid,
                       	@RequestParam(value = "idfa", defaultValue="", required = false) String idfa,
                       	@RequestParam(value = "o1", defaultValue="", required = false) String o1,
                        @RequestParam(value = "ip", defaultValue="", required = false) String ip,
                        @RequestParam(value = "ua", defaultValue="", required = false) String ua,
                        @RequestParam(value = "activeTime", defaultValue="0", required = false) Long activeTime,
                       	@RequestParam(value = "view_attributed", defaultValue="", required = false) String viewAttributed,
                       	@RequestParam(value = "partnerId", defaultValue="", required = false) String partnerId,
                       	@RequestParam(value = "appId", defaultValue="", required = false) String appId,
                       	HttpServletRequest httpRequest,
                       	HttpServletResponse httpResponse
                      	) {
    	logRequestParameter("###################### Active Callback data", httpRequest);
    	
    	if(StringUtils.isBlank(sid)){
    		return new GeneralResponse("ko", "BAD_REQUEST:paramter 'sid' missing", HttpStatus.BAD_REQUEST.value());
    	}
    	Tracker tracker = trackerService.getTracker(sid);
    	if(tracker == null){
    	    return new GeneralResponse("ko", "related record not found", HttpStatus.NOT_FOUND.value());
    	}
    	Campaign campaign = campaignService.getCampaign(tracker.getCampaignId());
    	if(campaign == null){
    	    return new GeneralResponse("ko", "related record not found", HttpStatus.NOT_FOUND.value());
    	}
    	
    	saveCallbackActive(sid, idfa, o1, ip, ua, activeTime, viewAttributed, partnerId, appId, tracker, campaign);
    	
    	return new GeneralResponse("ok", null, HttpStatus.OK.value());
    }

    private void saveCallbackActive(String sid, String idfa, String o1, String ip, String ua, Long activeTime,
                                    String viewAttributed, String partnerId, String appId, Tracker tracker, Campaign campaign) {
        try {
    		CallbackActivate callbackActive = new CallbackActivate();
    		callbackActive.setSid(sid);
    		
    		if(StringUtils.isNotBlank(idfa)){
    			callbackActive.setIdfa(idfa);
    		}else{
    			callbackActive.setIdfa(tracker.getIdfa());
    		}
    			
    		if(StringUtils.isNotBlank(o1)){
    			callbackActive.setO1(o1);
    		}else{
    			callbackActive.setO1(tracker.getO1());
    		}
    		
    		if(StringUtils.isNotBlank(ip)){
    			callbackActive.setIp(ip);
    		}else{
    			callbackActive.setIp(tracker.getIp());
    		}
    		
    		if(StringUtils.isNotBlank(ua)){
                callbackActive.setUa(ua);
            }else{
                callbackActive.setUa(tracker.getUa());
            }
            
            if(activeTime != null && activeTime != 0){
                callbackActive.setCreateTime(new Date(activeTime));
            }else{
                callbackActive.setCreateTime(new Date());
            }
            
            callbackActive.setViewAttributed(viewAttributed);
            
            if(StringUtils.isNotBlank(partnerId)){
                callbackActive.setPartnerId(partnerId);
            }else{
                callbackActive.setPartnerId(tracker.getPartnerId());
            }
    		
    		if(StringUtils.isNotBlank(appId)){
                callbackActive.setAppId(appId);
            }else{
                callbackActive.setAppId(campaign.getAppId());
            }
			
			callbackActiveService.save(callbackActive);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
    }
}
