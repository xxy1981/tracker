package com.xxytech.tracker.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xxytech.tracker.entity.Partner;
import com.xxytech.tracker.service.PartnerService;
import com.xxytech.tracker.utils.PageHtmlDisplay;

@Controller
@RequestMapping("/")
public class PartnerController extends AbstractController {

    @Autowired
    private PartnerService partnerService;

    @RequestMapping(value = "/partner", method = RequestMethod.GET)
    public ModelAndView query(@RequestParam(value = "id", defaultValue="", required = false) String id,
                              @RequestParam(value = "name", defaultValue="", required = false) String name,
                              @RequestParam(value = "pageNo", defaultValue="0", required = false) String pageNoStr, 
                              ModelMap model) {

        supportEnum(model);
        supportJavaMethod(model);

        Integer pageNo = getPageNo(pageNoStr);
        // 查询条件回填
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("id", id == null ? "" : id);
        model.addAttribute("name", name == null ? "" : name);

        PageRequest pr = new PageRequest(getPageNo(pageNoStr), getPageSize("10"));
        Page<Partner> page = partnerService.findByIdAndName(id, name, pr);
        model.addAttribute("page", page);
        model.addAttribute("list", page.getContent());
        model.addAttribute("pageHtmlDisplay", PageHtmlDisplay.display(page));
        
        return new ModelAndView("console/partner_list");
    }

    @RequestMapping(value = "/partner/{id}", method = RequestMethod.GET)
    public ModelAndView query(@PathVariable(value = "id") String id, ModelMap model) {

        supportEnum(model);
        supportJavaMethod(model);

        Partner partner = partnerService.getPartner(id);
        model.addAttribute("partnerDetail", partner);
        return new ModelAndView("console/partner_update");
    }

    @RequestMapping(value = "/partner/insert", method = RequestMethod.GET)
    public ModelAndView insertBook(ModelMap model) {

        supportEnum(model);
        supportJavaMethod(model);

        return new ModelAndView("console/partner_add");
    }

    @RequestMapping(value = "/partner/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(@RequestParam(value = "ids", required = false) String ids) {
    	String[] strIds = ids.split(",");
        partnerService.delete(strIds);
        return "ok";
    }

    @RequestMapping(value = "/partner/update", method = RequestMethod.POST)
    public ModelAndView update(Partner partner, ModelMap model) {
    	partner.setCreateTime(new Date());
        Boolean flag = partnerService.update(partner);
        model.addAttribute("msg", flag ? "修改成功" : "修改失败");
        return new ModelAndView("console/update_result");
    }

    @RequestMapping(value = "/partner/update", method = RequestMethod.GET)
    public String updateToShow() {
        return "redirect:/partner";
    }

    @RequestMapping(value = "/partner/add", method = RequestMethod.POST)
    public ModelAndView add(Partner partner, ModelMap model) {
    	partner.setCreateTime(new Date());
    	Boolean flag = partnerService.add(partner);
        model.addAttribute("msg", flag ? "添加成功" : "添加失败");
        return new ModelAndView("console/add_result");
    }

    @RequestMapping(value = "/partner/add", method = RequestMethod.GET)
    public String addToShow() {
        return "redirect:/partner";
    }
    
    @RequestMapping(value = "/json/partner/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Partner query(@PathVariable(value = "id") String id) {

    	Partner partner = partnerService.getPartner(id);
        return partner;
    }
}
