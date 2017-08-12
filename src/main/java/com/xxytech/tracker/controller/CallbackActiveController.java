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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xxytech.tracker.entity.CallbackActivate;
import com.xxytech.tracker.service.CallbackActiveService;
import com.xxytech.tracker.service.HttpService;
import com.xxytech.tracker.service.PartnerService;
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

    @RequestMapping(value = "/callbackActive", method = RequestMethod.GET)
    public ModelAndView query(@RequestParam(value = "impId", defaultValue="", required = false) String impId,
                              @RequestParam(value = "trackingPartner", defaultValue="", required = false) String trackingPartner,
                              @RequestParam(value = "pageNo", defaultValue="0", required = false) String pageNoStr, 
                              ModelMap model) {

        supportEnum(model);
        supportJavaMethod(model);

        Integer pageNo = getPageNo(pageNoStr);
        // 查询条件回填
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("impId", impId == null ? "" : impId);
        model.addAttribute("trackingPartner", trackingPartner == null ? "" : trackingPartner);
        model.addAttribute("parters", partnerService.findAll());

        PageRequest pr = new PageRequest(getPageNo(pageNoStr), getPageSize("10"));
        Page<CallbackActivate> page = callbackActiveService.findByImpIdAndTrackingPartner(impId, trackingPartner, pr);
        model.addAttribute("page", page);
        model.addAttribute("list", page.getContent());
        model.addAttribute("pageHtmlDisplay", PageHtmlDisplay.display(page));
        
        return new ModelAndView("console/callbackActive_list");
    }
    
    @RequestMapping(value = "/active")
    @ResponseBody
    public String serve(@RequestParam(value = "impId", defaultValue="", required = false) String impId,
                       	@RequestParam(value = "idfa", defaultValue="", required = false) String idfa,
                       	@RequestParam(value = "o1", defaultValue="", required = false) String o1,
                       	@RequestParam(value = "view_attributed", defaultValue="", required = false) String viewAttributed,
                       	@RequestParam(value = "trackingPartner", defaultValue="", required = false) String trackingPartner,
                       	@RequestParam(value = "propertyId", defaultValue="", required = false) String propertyId,
                       	HttpServletRequest httpRequest,
                       	HttpServletResponse httpResponse
                      	) {
    	if(StringUtils.isBlank(impId)){
    		httpService.activeFeeback("BAD_REQUEST", "paramter 'impId' missing", 1000);
    	}
    	if(StringUtils.isBlank(idfa) && StringUtils.isBlank(o1)){
    		httpService.activeFeeback("BAD_REQUEST", "paramter 'idfa' and 'o1' both missing", 1000);
    	}
    	
    	try {
    		CallbackActivate callbackActive = new CallbackActivate();
    		callbackActive.setImpId(impId);
    		callbackActive.setIdfa(idfa);
    		callbackActive.setO1(o1);
    		callbackActive.setTrackingPartner(trackingPartner);
    		callbackActive.setPropertyId(propertyId);
    		callbackActive.setViewAttributed(viewAttributed);
    		callbackActive.setCreateTime(new Date());
			
			callbackActiveService.save(callbackActive);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
    	
    	httpService.activeFeeback("OK", "success", 200);
        
        return "ok";
    }
}
