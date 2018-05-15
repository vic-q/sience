package com.demo.sience.service;

/**
 * @author wangqing
 */
public interface FallbackProvider<T> {

    /**
     * 熔断处理逻辑
     * @return
     */
    T execute();
}
