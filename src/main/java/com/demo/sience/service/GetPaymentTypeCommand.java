package com.demo.sience.service;

import com.demo.sience.model.ConsultResult;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

/**
 * @author wangqing
 */
public class GetPaymentTypeCommand extends HystrixCommand<ConsultResult> {

    private static final String SERVICE_GROUP_KEY = "GetPaymentTypeGroup";

    private final String paymentType;

    private static final HystrixConfig HYSTRIX_CONFIG;

    private FallbackProvider<ConsultResult> fallbackProvider;

    static {
        /**
         * 从配置中心定时拉取熔断器配置，此处只初始化一次
         */
        HYSTRIX_CONFIG = new HystrixConfig();
    }

    public GetPaymentTypeCommand(final String paymentType) {
        this(paymentType, null);
    }

    public GetPaymentTypeCommand(final String paymentType, final FallbackProvider fallbackProvider) {
        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey(SERVICE_GROUP_KEY))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withCircuitBreakerEnabled(HYSTRIX_CONFIG.isEnabled())
                        .withExecutionIsolationSemaphoreMaxConcurrentRequests(HYSTRIX_CONFIG.getMaxConcurrentRequests())
                        .withCircuitBreakerErrorThresholdPercentage(HYSTRIX_CONFIG.getErrorThresholdPercentage())
                        .withExecutionTimeoutInMilliseconds(HYSTRIX_CONFIG.getTimeoutInMilliseconds())));
        this.paymentType = paymentType;
        this.fallbackProvider = fallbackProvider;
    }

    protected ConsultResult run() throws Exception {
        return new PaymentRemoteService().isEnable(paymentType);
    }

    @Override
    protected ConsultResult getFallback() {
        if (fallbackProvider != null) {
            return fallbackProvider.execute();
        }
        return null;
    }
}
