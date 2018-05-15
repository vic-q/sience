package com.demo.sience.service;

/**
 * @author wangqing
 */
public class HystrixConfig {

    private static final Boolean DEFAULT_ENABLED = true;

    private static final Integer DEFAULT_MAX_CONCURRENT_REQUESTS = 50;

    private static final Integer DEFAULT_TIMEOUT_IN_MILLISECONDS = 2000;

    private static final Integer DEFAULT_ERROR_THRESHOLD_PERCENTAGE = 50;

    /**
     * 是否开启熔断器
     */
    private Boolean enabled;

    /**
     * 最大并发量
     */
    private Integer maxConcurrentRequests;

    /**
     * 超时时间
     */
    private Integer timeoutInMilliseconds;

    /**
     * 错误比率阀值
     */
    private Integer errorThresholdPercentage;

    public HystrixConfig() {
        this.enabled = DEFAULT_ENABLED;
        this.maxConcurrentRequests = DEFAULT_MAX_CONCURRENT_REQUESTS;
        this.timeoutInMilliseconds = DEFAULT_TIMEOUT_IN_MILLISECONDS;
        this.errorThresholdPercentage = DEFAULT_ERROR_THRESHOLD_PERCENTAGE;
        //TODO 开启线程刷新配置
    }

    /**
     * 其他参数省略
     * @return
     */

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getMaxConcurrentRequests() {
        return maxConcurrentRequests;
    }

    public void setMaxConcurrentRequests(Integer maxConcurrentRequests) {
        this.maxConcurrentRequests = maxConcurrentRequests;
    }

    public Integer getErrorThresholdPercentage() {
        return errorThresholdPercentage;
    }

    public void setErrorThresholdPercentage(Integer errorThresholdPercentage) {
        this.errorThresholdPercentage = errorThresholdPercentage;
    }

    public Integer getTimeoutInMilliseconds() {
        return timeoutInMilliseconds;
    }

    public void setTimeoutInMilliseconds(Integer timeoutInMilliseconds) {
        this.timeoutInMilliseconds = timeoutInMilliseconds;
    }
}
