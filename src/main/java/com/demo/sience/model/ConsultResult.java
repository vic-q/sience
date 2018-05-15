package com.demo.sience.model;

/**
 * @author wangqing 
 */
public class ConsultResult {

    private final Boolean isEnable;

    private final String errorCode;

    public ConsultResult(final Boolean isEnable, final String errorCode) {
        this.isEnable = isEnable;
        this.errorCode = errorCode;
    }

    public Boolean isEnable() {
        return isEnable;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
