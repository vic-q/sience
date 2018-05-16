package com.demo.sience.common;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程工厂实现
 * @author wangqing
 */
public class NamedThreadFactory implements ThreadFactory {

    private final AtomicInteger threadNumber = new AtomicInteger(1);

    private final String prefix;

    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler = (r, t) -> {
        // TODO 线程内执行异常，外层也可以做处理
    };

    private NamedThreadFactory(final String prefix) {
        this.prefix = prefix;
    }

    public static NamedThreadFactory create(String prefix) {
        return new NamedThreadFactory(prefix);
    }


    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, this.prefix + "-" + threadNumber.getAndIncrement());
        t.setUncaughtExceptionHandler(uncaughtExceptionHandler);
        return t;
    }
}
