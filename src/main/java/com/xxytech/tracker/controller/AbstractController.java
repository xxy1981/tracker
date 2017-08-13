package com.xxytech.tracker.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;

import com.xxytech.tracker.utils.RelativeDateFormat;

import freemarker.ext.beans.BeansWrapper;

public class AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(AbstractController.class);
	
	private static final Map<String, Object> CLASS_MAP = new HashMap<String, Object>();

	public static final int DEFAULT_PAGE_NO = 0;
	public static final int DEFAULT_PAGE_SIZE = 10;

	static {
		CLASS_MAP.put("relativeDateFormat", new RelativeDateFormat());
	}

	protected String getContextPath(HttpServletRequest request) {
		return request.getContextPath();
	}

	protected String getBasePath(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
	}

	/**
	 * 获得PageNo Spring Data Page组件的翻页功能页码是以0开始的
	 * 
	 * @param pageNoStr
	 * @return
	 */
	protected Integer getPageNo(String pageNoStr) {
		if (pageNoStr == null || pageNoStr.length() <= 0) {
			return DEFAULT_PAGE_NO;
		}
		int pageNo = DEFAULT_PAGE_NO;
		try {
			pageNo = Integer.parseInt(pageNoStr) > 0 ? Integer.parseInt(pageNoStr) - 1 : DEFAULT_PAGE_NO;
		} catch (NumberFormatException e) {
			pageNo = DEFAULT_PAGE_NO;
		}
		return pageNo;
	}

	/**
	 * 获得PageSize
	 * 
	 * @param pageSizeStr
	 * @return
	 */
	protected Integer getPageSize(String pageSizeStr) {
		if (pageSizeStr == null || pageSizeStr.length() <= 0) {
			return DEFAULT_PAGE_SIZE;
		}
		int pageSize = DEFAULT_PAGE_SIZE;
		try {
			pageSize = Integer.parseInt(pageSizeStr);
		} catch (NumberFormatException e) {
			pageSize = DEFAULT_PAGE_SIZE;
		}
		return pageSize;
	}

	/**
	 * 在Freemarker模板中显示枚举
	 * 
	 * @param model
	 */
	@SuppressWarnings("deprecation")
	protected void supportEnum(ModelMap model) {
		model.addAttribute("enums", BeansWrapper.getDefaultInstance().getEnumModels());
	}

	/**
	 * 在Freemarker模板中支持java静态方法
	 * 
	 * @param model
	 */
	protected void supportJavaMethod(ModelMap model) {
		model.addAttribute("classMap", CLASS_MAP);
	}

	/**
	 * 获取客户端IP
	 * 
	 * @param request
	 * @return
	 */
	protected String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("x-forwarded-for");// X-FORWARDED-FOR
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			if (ip.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ip = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ip != null && ip.length() > 15) {
			if (ip.indexOf(",") > 0) {
				ip = ip.substring(0, ip.indexOf(","));
			}
		}
		return ip;
	}

	/**
	 * 获取客户端UserAgent
	 * 
	 * @param request
	 * @return
	 */
	protected String getUserAgent(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");// User-Agent
		return userAgent;
	}
	
	protected void logRequestParameter(String title, HttpServletRequest request) {
        Map<String, String[]> requestParams = request.getParameterMap();
        StringBuilder sb = new StringBuilder(title);
        sb.append("\r\n");
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            sb.append(name).append("=").append(valueStr).append("\r\n");
        }
        logger.warn(sb.toString());
    }
}
