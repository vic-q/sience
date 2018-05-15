package com.demo.sience.model;

import java.util.concurrent.Future;

/**
 * @author wangqing 
 */
public class PaymentTypeResultDO {

    private final String paymentType;

    private final Future<ConsultResult> future;

    public PaymentTypeResultDO(final String paymentType, final Future<ConsultResult> future) {
        this.paymentType = paymentType;
        this.future = future;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public Future<ConsultResult> getFuture() {
        return future;
    }
}
