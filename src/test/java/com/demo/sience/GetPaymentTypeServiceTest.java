package com.demo.sience;

import com.google.common.collect.Lists;

import com.demo.sience.model.PaymentTypeResultDTO;
import com.demo.sience.service.GetPaymentTypeService;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author wangqing
 *
 */

public class GetPaymentTypeServiceTest {

    private static final String BALANCE = "balance";

    private static final String RED_PACKET = "redPacket";

    private static final String TICKET = "ticket";

    private static final String VOUCHER = "voucher";

    // TODO 限流和熔断单元测试省略
    @Test
    public void getPaymentTypeResultListTest() {
        GetPaymentTypeService service = new GetPaymentTypeService();
        List<PaymentTypeResultDTO> result = service.getPaymentTypeResultList(Lists.newArrayList(BALANCE, RED_PACKET, TICKET, VOUCHER));
        Assert.assertTrue(result.size() == 4);
    }

}
