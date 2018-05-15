package com.demo.sience.service;

import com.demo.sience.common.AccessLimitService;
import com.demo.sience.common.NamedThreadFactory;
import com.demo.sience.enums.ErrorInfoTypeEnum;
import com.demo.sience.model.ConsultResult;
import com.demo.sience.model.PaymentTypeResultDO;
import com.demo.sience.model.PaymentTypeResultDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * @author wangqing
 */
public class GetPaymentTypeService {

    /**
     * 扩展（可以生成一个全局的id，通过threadLocal传递做日志调用连）
     */
    private static final Logger log = LoggerFactory.getLogger(GetPaymentTypeService.class);

    /**
     * 线程名称
     */
    private static final String THREAD_NAME = "GetPaymentType";

    /**
     * 核心线程数
     */
    private static final int CORE_SIZE = Runtime.getRuntime().availableProcessors();

    /**
     * 队列大小（防止oom）
     */
    private static final int QUEUE_SIZE = 1000;

    /**
     * 超时时间
     */
    private static final long KEEP_ALIVE_TIME = 60L;


    private static final int TIMEOUT = 2;

    /**
     * 根据场景判断是否启动核心线程数的线程和超时时间
     * 线程执行异常已经交给Thread.UncaughtExceptionHandler处理，也可以在服务层根据场景来处理
     */
    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(
            CORE_SIZE,
            CORE_SIZE,
            KEEP_ALIVE_TIME,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(QUEUE_SIZE),
            NamedThreadFactory.create(THREAD_NAME),
            new GetPaymentTypeRejectedExecutionHandler());

    static {
        /**
         * 注册钩子，在jvm退出时，关闭线程池
         */
        Runtime.getRuntime().addShutdownHook(new Thread(() -> EXECUTOR.shutdown()));
    }

    /**
     * 获取可用的支付类型列表
     * @param paymentTypeList
     * @return
     */
    public List<PaymentTypeResultDTO> getPaymentTypeResultList(final List<String> paymentTypeList) {
        if (paymentTypeList == null || paymentTypeList.isEmpty()) {
            if (log.isInfoEnabled()) {
                log.info("请求参数为空");
            }
            return Collections.emptyList();
        }

        if (log.isInfoEnabled()) {
            log.info("请求参数:{}", Arrays.toString(paymentTypeList.toArray()));
        }

        if (!access()) {
            // 超过限流上限值，根据业务场景处理
            return PaymentTypeResultDTO.getDefaultValue(paymentTypeList, ErrorInfoTypeEnum.LIMIT);
        }
        return doGetPaymentTypeResultList(paymentTypeList);
    }

    private List<PaymentTypeResultDTO> doGetPaymentTypeResultList(final List<String> paymentTypeList) {

        final List<PaymentTypeResultDO> paymentTypeResultDOList = paymentTypeList.stream()
                .map(paymentType -> new PaymentTypeResultDO(paymentType, EXECUTOR.submit(new GetPaymentTypeTask(paymentType))))
                .collect(Collectors.toList());

        return paymentTypeResultDOList.stream().map(paymentTypeResultDO -> {
            Future<ConsultResult> future = paymentTypeResultDO.getFuture();
            try {
                // get方法可以根据场景指定超时时间
                if (future != null && future.get(TIMEOUT, TimeUnit.SECONDS) != null) {
                    return new PaymentTypeResultDTO(paymentTypeResultDO.getPaymentType(), future.get().isEnable(), future.get().getErrorCode());
                } else {
                    log.warn("请求失败,paymentType:{}", paymentTypeResultDO.getPaymentType());
                }
            } catch (InterruptedException e) {
                log.warn("中断异常,paymentType:{}", paymentTypeResultDO.getPaymentType(), e);
            } catch (ExecutionException e) {
                log.warn("执行异常,paymentType:{}", paymentTypeResultDO.getPaymentType(), e);
            } catch (TimeoutException e) {
                log.warn("超时异常,paymentType:{}", paymentTypeResultDO.getPaymentType(), e);
            }
            // 统一处理失败情况，状态码可以根据场景映射
            return new PaymentTypeResultDTO(paymentTypeResultDO.getPaymentType(), false, ErrorInfoTypeEnum.FAIL.getErrorCode());
        }).collect(Collectors.toList());
    }

    private boolean access() {
        // 对上游调用限流，确保自身服务高可用
        return AccessLimitService.getInstance().tryAcquire();
    }

    private static class GetPaymentTypeTask implements Callable<ConsultResult> {

        private final String paymentType;

        public GetPaymentTypeTask(final String paymentType) {
            this.paymentType = paymentType;
        }

        @Override
        public ConsultResult call() throws Exception {
            // 断路器调用远程服务，确保自身服务的稳定,可用
            return new GetPaymentTypeCommand(paymentType).execute();
        }
    }

}
