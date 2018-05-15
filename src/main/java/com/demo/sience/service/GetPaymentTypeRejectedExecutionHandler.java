package com.demo.sience.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author wangqing 
 */
public class GetPaymentTypeRejectedExecutionHandler implements RejectedExecutionHandler {

    private static Logger log = LoggerFactory.getLogger(GetPaymentTypeRejectedExecutionHandler.class);

    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

        if (!executor.getQueue().offer(r)) {
            // 根据场景容错
            log.error("队列已满，任务被拒绝");
        }
    }
}
