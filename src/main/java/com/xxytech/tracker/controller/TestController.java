package com.xxytech.tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xxytech.tracker.service.CampaignService;
import com.xxytech.tracker.service.PartnerService;

@Controller
@RequestMapping("/")
public class TestController extends AbstractController {

    @Autowired
    private PartnerService partnerService;
    @Autowired
    private CampaignService campaignService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ModelAndView query(ModelMap model) {

        supportEnum(model);
        supportJavaMethod(model);
        model.addAttribute("parters", partnerService.findAll());
        model.addAttribute("campaigns", campaignService.findAll());
        
        return new ModelAndView("console/test");
    }
}
