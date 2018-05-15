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

    public PaymentTypeResultDTO(final String type) {
        this.type = type;
    }

    public static List<PaymentTypeResultDTO> getDefaultValue(List<String> paymentTypeList) {
        return paymentTypeList.stream()
                .map(paymentType -> new PaymentTypeResultDTO(paymentType))
                .collect(Collectors.toList());
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("type", this.type)
                .toString();
    }
}
