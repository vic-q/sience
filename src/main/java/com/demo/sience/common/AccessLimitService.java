package com.demo.sience.common;

import com.google.common.util.concurrent.RateLimiter;

/**
 * 限流器
 * @author wangqing
 */
public class AccessLimitService {

    private static final double PERMIT_PER_SECOND = 5.0D;

    private static final AccessLimitService INSTANCE = new AccessLimitService();

    private AccessLimitService() {

    }

    public static AccessLimitService getInstance() {
        return INSTANCE;
    }

    /**
     * 每秒只发出5个令牌
     */
    private RateLimiter rateLimiter = RateLimiter.create(PERMIT_PER_SECOND);

    /**
     * 尝试获取令牌
     * @return
     */
    public boolean tryAcquire() {
        return rateLimiter.tryAcquire();
    }

}
