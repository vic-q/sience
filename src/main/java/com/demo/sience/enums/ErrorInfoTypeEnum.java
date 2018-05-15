package com.demo.sience.enums;

/**
 * @author wangqing 
 */
public enum ErrorInfoTypeEnum {

    FAIL("9998", "请求失败"),

    LIMIT("9999", "限流");

    private String errorCode;

    private String description;

    ErrorInfoTypeEnum(String errorCode, String description) {
        this.errorCode = errorCode;
        this.description = description;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getDescription() {
        return description;
    }
}
