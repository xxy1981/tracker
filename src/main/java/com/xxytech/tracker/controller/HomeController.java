package com.xxytech.tracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class HomeController extends AbstractController {

    @RequestMapping(value = { "", "index" }, method = RequestMethod.GET)
    public String index(ModelMap model) {
        return "redirect:/partner";
    }

    @RequestMapping(value = "/go", method = RequestMethod.GET)
    @ResponseBody
    public String go() {
        return "ok";
    }
}
