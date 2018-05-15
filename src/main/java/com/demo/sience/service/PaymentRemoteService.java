package com.demo.sience.service;

import com.demo.sience.model.ConsultResult;

/**
 * @author wangqing
 */
public class PaymentRemoteService {

    public ConsultResult isEnable(String paymentType) {
        return new ConsultResult(true, "200");
    }

}
