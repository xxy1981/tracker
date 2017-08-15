package com.xxytech.tracker.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.xxytech.tracker.utils.GeneralResponse;


@Controller
@RequestMapping(value = { "/" })
public class LoginController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private static final String LOGIN_SEESION_USER_INFO = "l_s_u_i";

    /**
     * 跳转到登录页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request,
                        HttpServletResponse response) {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public GeneralResponse login(@RequestParam("loginName") String loginName,
                                 @RequestParam("password") String password, 
                                 HttpServletRequest request,
                                 HttpServletResponse response) {

        logger.debug("post login request body:" + loginName);
        
        if("tracker".equalsIgnoreCase(loginName) || "tracker".equalsIgnoreCase(password)){
            // 密码验证成功后，把用户(user)信息存入session
            request.getSession().setAttribute(LOGIN_SEESION_USER_INFO, loginName);
            response.addHeader("Set-Cookie", "JSESSIONID=" + request.getSession().getId() + "; Path=/; HttpOnly");
            return new GeneralResponse("success", null, HttpStatus.OK.value());
        }else{
            return new GeneralResponse("fail", "用户名或密码错误", HttpStatus.UNAUTHORIZED.value());
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {

        logger.debug("is logout");

        // 在没有session的情况下应直接属于非登录状态
        HttpSession session = request.getSession(false);
        if (session != null) {
            // 有session时，直接删除登录时保存在session里的用户对象
            session.removeAttribute(LOGIN_SEESION_USER_INFO);
        }
        return new ModelAndView(new RedirectView("login"));
    }
}
