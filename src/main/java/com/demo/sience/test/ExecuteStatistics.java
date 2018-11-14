package com.demo.sience.test;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author guangda 
 */
public class ExecuteStatistics implements Runnable {

    private static final Map<String, AtomicLong> statistics = Maps.newHashMap();

    private CyclicBarrier cyclicBarrier;

    public ExecuteStatistics(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    static {
        statistics.put("test", new AtomicLong(0));
    }

    @Override
    public void run() {
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

        System.out.println("threadName=" + Thread.currentThread().getName());
        getAddIncrease("test");
    }

    public void getAddIncrease(String key) {
        AtomicLong value = statistics.get(key);
        value.getAndIncrement();
    }


    public void print() {
        for(Map.Entry<String, AtomicLong> entry : this.statistics.entrySet()) {
            System.out.println("key=" + entry.getKey() + ", value=" + entry.getValue());
        }
    }

    private static final ExecutorService pool = Executors.newFixedThreadPool(100);

    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(100);
        ExecuteStatistics task = new ExecuteStatistics(cyclicBarrier);
        for (int i = 0; i < 100; i++) {
            pool.execute(task);
        }

        Thread.sleep(5000);

        task.print();

        pool.shutdown();
    }
}
