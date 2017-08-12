package com.xxytech.tracker.utils;

import org.springframework.data.domain.Page;

public class PageHtmlDisplay {

    public static String display(Page<?> page) {

        // 显示总条数
        StringBuilder sb = new StringBuilder();
        sb.append("<div>共<span>" + page.getTotalElements() + "</span>条</div>");
        int currentNo = page.getNumber() + 1;

        // 如果总条数为0
        if (page.getTotalElements() <= 0) {
            return sb.toString();
        }

        // 计算显示页码
        int start = Math.max(currentNo - 4, 1);
        if (page.getTotalPages() - 6 > 1) {
            start = Math.min(start, page.getTotalPages() - 6);
        }
        int end = Math.min(page.getTotalPages(), start + 6);

        // 上一页
        sb.append("<ul>");
        if(page.isFirst()){
        	 sb.append("<li>上页</li>");
        }else{
        	 sb.append("<li><a data-no='" + (currentNo - 1)  + "'>上页</a></li>");
        }

        // 是否显示首页
        if (start > 1) {
            sb.append("<li><a data-no='1'>1</a></li>");
        }

        // 循环页码
        for (int i = start; i <= end; i++) {

            // 添加首页省略号
            if (start > 2 && i == start) {
                sb.append("<li>...</li>");
            }

            // 循环
            if (i == currentNo) {
                sb.append("<li><span>" + i + "</span></li>");
            } else {
                sb.append("<li><a data-no='" + i + "'>" + i + "</a></li>");
            }

            // 添加末页省略号
            if (end < (page.getTotalPages() - 1) && i == end) {
                sb.append("<li>...</li>");
            }
        }

        // 是否显示末页
        if (page.getTotalPages() > end) {
            sb.append("<li><a data-no='" + page.getTotalPages() + "'>" + page.getTotalPages() + "</a></li>");
        }

        // 下一页
        if(page.isLast()){
        	sb.append("<li>下页</li>");
        }else{
        	sb.append("<li><a data-no='" + (currentNo + 1) + "'>下页</a></li>");
        }
        sb.append("</ul>");

        // 输入框
        if (page.getTotalPages() > 10) {
            sb.append("<div>转到第 <input type='text' value='" + currentNo + "' /> 页 ");
            sb.append("<input type='button' value='确定' /></div>");
        }
        return sb.toString();
    }
}
