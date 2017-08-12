package com.xxytech.tracker.enums;

public enum DeviceType {

    IOS("IOS", "苹果"),
    ANDROID("ANDROID", "安卓");

    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    private DeviceType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static DeviceType of(String name) {
        for (DeviceType e : DeviceType.values()) {
            if (e.name.equals(name)) {
                return e;
            }
        }
        return null;
    }
}
