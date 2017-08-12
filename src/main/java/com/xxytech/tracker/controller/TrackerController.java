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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xxytech.tracker.entity.Tracker;
import com.xxytech.tracker.enums.DeviceType;
import com.xxytech.tracker.service.HttpService;
import com.xxytech.tracker.service.PartnerService;
import com.xxytech.tracker.service.TrackerService;
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
    private HttpService httpService;

    @RequestMapping(value = "/tracker", method = RequestMethod.GET)
    public ModelAndView query(@RequestParam(value = "impId", defaultValue="", required = false) String impId,
                              @RequestParam(value = "siteId", defaultValue="", required = false) String siteId,
                              @RequestParam(value = "trackingPartner", defaultValue="", required = false) String trackingPartner,
                              @RequestParam(value = "pageNo", defaultValue="0", required = false) String pageNoStr, 
                              ModelMap model) {

        supportEnum(model);
        supportJavaMethod(model);

        Integer pageNo = getPageNo(pageNoStr);
        // 查询条件回填
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("impId", impId == null ? "" : impId);
        model.addAttribute("siteId", siteId == null ? "" : siteId);
        model.addAttribute("trackingPartner", trackingPartner == null ? "" : trackingPartner);
        model.addAttribute("parters", partnerService.findAll());

        PageRequest pr = new PageRequest(getPageNo(pageNoStr), getPageSize("10"));
        Page<Tracker> page = trackerService.findByImpIdAndSiteIdAndTrackingPartner(impId, siteId, trackingPartner, pr);
        model.addAttribute("page", page);
        model.addAttribute("list", page.getContent());
        model.addAttribute("pageHtmlDisplay", PageHtmlDisplay.display(page));
        
        return new ModelAndView("console/tracker_list");
    }
    
    @RequestMapping(value = "/serve")
    @ResponseBody
    public String serve(@RequestParam(value = "action", defaultValue="click", required = false) String action,
           				@RequestParam(value = "impId", defaultValue="", required = false) String impId,
                       	@RequestParam(value = "idfa", defaultValue="", required = false) String idfa,
                       	@RequestParam(value = "o1", defaultValue="", required = false) String o1,
                       	@RequestParam(value = "siteId", defaultValue="", required = false) String siteId,
                       	@RequestParam(value = "chn", defaultValue="", required = false) String chn,
                       	@RequestParam(value = "trackingPartner", defaultValue="", required = false) String trackingPartner,
                       	@RequestHeader(value = "X-FORWARDED-FOR", required = false) String ip, //X-Forward-for
                       	@RequestHeader(value = "User-Agent", required = false) String ua, //x-device-user-agent
                       	HttpServletRequest httpRequest,
                       	HttpServletResponse httpResponse
                      	) {

    	String clinetIp = getIpAddr(httpRequest);
    	String userAgent = getUserAgent(httpRequest);
        
    	httpService.httpGetCall(chn, idfa, impId, clinetIp);
    	
    	try {
			Tracker tracker = new Tracker();
			tracker.setImpId(impId);
			tracker.setIdfa(idfa);
			tracker.setO1(o1);
			tracker.setSiteId(siteId);
			tracker.setIp(clinetIp);
			tracker.setTrackingPartner(trackingPartner);
			tracker.setUa(ua);
			tracker.setCreateTime(new Date());
			if(StringUtils.isNotBlank(idfa)){
				tracker.setDeviceType(DeviceType.IOS.name());
			}else{
				tracker.setDeviceType(DeviceType.ANDROID.name());
			}
			
			trackerService.save(tracker);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
        
        return "ok";
    }
    
    @RequestMapping(value = "/serveTest")
    public String serveTest(@RequestParam(value = "action", defaultValue="click", required = false) String action,
           				@RequestParam(value = "impId", defaultValue="", required = false) String impId,
                       	@RequestParam(value = "idfa", defaultValue="", required = false) String idfa,
                       	@RequestParam(value = "o1", defaultValue="", required = false) String o1,
                       	@RequestParam(value = "siteId", defaultValue="", required = false) String siteId,
                       	@RequestParam(value = "chn", defaultValue="", required = false) String chn,
                       	@RequestParam(value = "trackingPartner", defaultValue="", required = false) String trackingPartner,
                       	@RequestHeader(value = "X-FORWARDED-FOR", required = false) String ip, //X-Forward-for
                       	@RequestHeader(value = "User-Agent", required = false) String ua, //x-device-user-agent
                       	HttpServletRequest httpRequest,
                       	HttpServletResponse httpResponse
                      	) {

    	String clinetIp = getIpAddr(httpRequest);
    	String userAgent = getUserAgent(httpRequest);
        
    	httpService.httpGetCall(chn, idfa, impId, clinetIp);
    	
    	try {
			Tracker tracker = new Tracker();
			tracker.setImpId(impId);
			tracker.setIdfa(idfa);
			tracker.setO1(o1);
			tracker.setSiteId(siteId);
			tracker.setIp(clinetIp);
			tracker.setTrackingPartner(trackingPartner);
			tracker.setUa(ua);
			tracker.setCreateTime(new Date());
			if(StringUtils.isNotBlank(idfa)){
				tracker.setDeviceType(DeviceType.IOS.name());
			}else{
				tracker.setDeviceType(DeviceType.ANDROID.name());
			}
			
			trackerService.save(tracker);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
        
        return "redirect:/tracker";
    }
}
