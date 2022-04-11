package com.zfy.springcloud.service;

import com.zfy.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @InterfaceName: PaymentService
 * @description:
 **/
public interface PaymentService {
    int create(Payment payment);

    Payment getPaymentById(@Param("id") Long id);

    List<Payment> getAllPayment();
}
