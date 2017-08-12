package com.xxytech.tracker.http;

import java.util.HashMap;
import java.util.Map;

public class HttpFormEncoding {

    private Map<String, String> params = new HashMap<String, String>();

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    /**
     * 追加参数
     * 
     * @param key Key
     * @param value Value
     */
    public void addParams(String key, String value) {
        this.params.put(key, value);
    }
}
