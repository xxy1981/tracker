package com.xxytech.tracker.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xxytech.tracker.entity.Campaign;
import com.xxytech.tracker.service.CampaignService;
import com.xxytech.tracker.service.PartnerService;
import com.xxytech.tracker.utils.PageHtmlDisplay;

@Controller
@RequestMapping("/")
public class CampaignController extends AbstractController {

    @Autowired
    private CampaignService campaignService;
    @Autowired
    private PartnerService partnerService;

    @RequestMapping(value = "/campaign", method = RequestMethod.GET)
    public ModelAndView query(@RequestParam(value = "id", defaultValue="", required = false) String id,
                              @RequestParam(value = "name", defaultValue="", required = false) String name,
                              @RequestParam(value = "partnerId", defaultValue="", required = false) String partnerId,
                              @RequestParam(value = "pageNo", defaultValue="0", required = false) String pageNoStr, 
                              ModelMap model) {

        supportEnum(model);
        supportJavaMethod(model);

        Integer pageNo = getPageNo(pageNoStr);
        // 查询条件回填
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("id", id == null ? "" : id);
        model.addAttribute("name", name == null ? "" : name);
        model.addAttribute("partnerId", partnerId == null ? "" : partnerId);
        model.addAttribute("parters", partnerService.findAll());

        Sort sort = new Sort(Direction.DESC,"createTime");
        
        PageRequest pr = new PageRequest(getPageNo(pageNoStr), getPageSize("5"), sort);
        Page<Campaign> page = campaignService.findByIdAndNameAndPartnerId(id, name, partnerId, pr);
        model.addAttribute("page", page);
        model.addAttribute("list", page.getContent());
        model.addAttribute("pageHtmlDisplay", PageHtmlDisplay.display(page));
        
        return new ModelAndView("console/campaign_list");
    }

    @RequestMapping(value = "/campaign/{id}", method = RequestMethod.GET)
    public ModelAndView query(@PathVariable(value = "id") String id, ModelMap model) {

        supportEnum(model);
        supportJavaMethod(model);
        model.addAttribute("parters", partnerService.findAll());
        Campaign campaign = campaignService.getCampaign(id);
        model.addAttribute("campaignDetail", campaign);
        return new ModelAndView("console/campaign_update");
    }

    @RequestMapping(value = "/campaign/insert", method = RequestMethod.GET)
    public ModelAndView insertBook(ModelMap model) {

        supportEnum(model);
        supportJavaMethod(model);
        model.addAttribute("parters", partnerService.findAll());
        return new ModelAndView("console/campaign_add");
    }

    @RequestMapping(value = "/campaign/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(@RequestParam(value = "ids", required = false) String ids) {
    	String[] strIds = ids.split(",");
        campaignService.delete(strIds);
        return "ok";
    }

    @RequestMapping(value = "/campaign/update", method = RequestMethod.POST)
    public String update(Campaign campaign, HttpServletRequest httpRequest, ModelMap model) {
    	campaign.setCreateTime(new Date());
    	campaign.setPartnerName(partnerService.getPartner(campaign.getPartnerId()).getName());
    	String url = buildTrackerUrl(campaign, httpRequest);
		campaign.setUrl(url);
    	
        Boolean flag = campaignService.update(campaign);
        model.addAttribute("msg", flag ? "修改成功" : "修改失败");
        //return new ModelAndView("console/update_result");
        return "redirect:/campaign";
    }

    @RequestMapping(value = "/campaign/update", method = RequestMethod.GET)
    public String updateToShow() {
        return "redirect:/campaign";
    }

    @RequestMapping(value = "/campaign/add", method = RequestMethod.POST)
    public String add(Campaign campaign, HttpServletRequest httpRequest, ModelMap model) {
    	campaign.setCreateTime(new Date());
    	campaign.setPartnerName(partnerService.getPartner(campaign.getPartnerId()).getName());
    	String url = buildTrackerUrl(campaign, httpRequest);
    	campaign.setUrl(url);
	
    	Boolean flag = campaignService.add(campaign);
        model.addAttribute("msg", flag ? "添加成功" : "添加失败");
        //return new ModelAndView("console/add_result");
        return "redirect:/campaign";
    }

    @RequestMapping(value = "/campaign/add", method = RequestMethod.GET)
    public String addToShow() {
        return "redirect:/campaign";
    }
    
    @RequestMapping(value = "/json/campaign/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Campaign query(@PathVariable(value = "id") String id) {

    	Campaign campaign = campaignService.getCampaign(id);
        return campaign;
    }

	private String buildTrackerUrl(Campaign campaign, HttpServletRequest httpRequest) {
		String port = httpRequest.getServerPort() == 80 ? "" : ":" + httpRequest.getServerPort() ;
		String contextPath = httpRequest.getContextPath().equals("") ? "/" : "/" + httpRequest.getContextPath() + "/";
		String url = httpRequest.getScheme() + "://" + httpRequest.getServerName() + port + contextPath + campaign.getId()
			+ "?action={action}&sid={sid}&idfa={idfa}&o1={o1}&subChn={subChn}";
		return url;
	}
}
