package com.xxytech.tracker.http;

import java.util.HashMap;
import java.util.Map;

/**
 * HTTP headers 工具
 */
public class HttpHeaders {

    public static final String  AUTHORIZATION       = "Authorization";
    public static final String  CACHE_CONTROL       = "Cache-Control";
    public static final String  CONTENT_DISPOSITION = "Content-Disposition";
    public static final String  CONTENT_ENCODING    = "Content-Encoding";
    public static final String  CONTENT_LENGTH      = "Content-Length";
    public static final String  CONTENT_MD5         = "Content-MD5";
    public static final String  CONTENT_TYPE        = "Content-Type";
    public static final String  TRANSFER_ENCODING   = "Transfer-Encoding";
    public static final String  DATE                = "Date";
    public static final String  ETAG                = "ETag";
    public static final String  EXPIRES             = "Expires";
    public static final String  HOST                = "Host";
    public static final String  LAST_MODIFIED       = "Last-Modified";
    public static final String  RANGE               = "Range";
    public static final String  LOCATION            = "Location";
    public static final String  CONNECTION          = "Connection";

    private Map<String, String> headers             = new HashMap<String, String>();

    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * 设置header
     * 
     * @param headers HTTP Request Heasers
     */
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    /**
     * header 追加
     * 
     * @param key Header key
     * @param value Header value
     */
    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

}
