package com.demo.sience.model;

import com.google.common.base.MoreObjects;

import com.demo.sience.enums.ErrorInfoTypeEnum;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangqing
 */
public class PaymentTypeResultDTO {

    private String type;

    private Boolean enable;

    private String errorCode;

    public PaymentTypeResultDTO(final String type, final Boolean enable, final String errorCode) {
        this.type = type;
        this.enable = enable;
        this.errorCode = errorCode;
    }

    public static List<PaymentTypeResultDTO> getDefaultValue(List<String> paymentTypeList, ErrorInfoTypeEnum errorInfoTypeEnum) {
        return paymentTypeList.stream()
                .map(paymentType -> new PaymentTypeResultDTO(paymentType, false, errorInfoTypeEnum.getErrorCode()))
                .collect(Collectors.toList());
    }

    public String getType() {
        return type;
    }

    public Boolean isEnable() {
        return enable;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("type", this.type)
                .add("enable", this.enable)
                .add("errorCode", this.errorCode)
                .toString();
    }
}
