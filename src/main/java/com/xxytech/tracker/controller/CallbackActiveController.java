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
import com.xxytech.tracker.service.HttpService;
import com.xxytech.tracker.service.PartnerService;
import com.xxytech.tracker.service.TrackerService;
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
    private HttpService httpService;
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
    public String serve(@RequestParam(value = "sid", defaultValue="", required = true) String sid,
                       	@RequestParam(value = "idfa", defaultValue="", required = false) String idfa,
                       	@RequestParam(value = "o1", defaultValue="", required = false) String o1,
                       	@RequestParam(value = "view_attributed", defaultValue="", required = false) String viewAttributed,
                       	@RequestParam(value = "partnerId", defaultValue="", required = false) String partnerId,
                       	@RequestParam(value = "appId", defaultValue="", required = false) String appId,
                       	HttpServletRequest httpRequest,
                       	HttpServletResponse httpResponse
                      	) {
    	logRequestParameter("Active Callback data", httpRequest);
    	
    	if(StringUtils.isBlank(sid)){
    		//httpService.activeFeeback(null, "BAD_REQUEST", "paramter 'sid' missing", 1000);
    		return "ko";
    	}
    	Tracker tracker = trackerService.getTracker(sid);
    	if(tracker == null){
    		return "ko";
    	}
    	Campaign campaign = campaignService.getCampaign(tracker.getCampaignId());
    	if(campaign == null){
    		return "ko";
    	}
    	
    	try {
    		CallbackActivate callbackActive = new CallbackActivate();
    		callbackActive.setSid(sid);
    		callbackActive.setIdfa(tracker.getIdfa());
    		callbackActive.setO1(tracker.getO1());
    		callbackActive.setPartnerId(tracker.getPartnerId());
    		callbackActive.setAppId(campaign.getAppId());
    		callbackActive.setViewAttributed(viewAttributed);
    		callbackActive.setCreateTime(new Date());
			
			callbackActiveService.save(callbackActive);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
    	
    	httpService.activeFeeback(campaign, "OK", "success", 200);
        
        return "ok";
    }
}
