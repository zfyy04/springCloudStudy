package com.zfy.springcloud.service.impl;

import com.zfy.springcloud.dao.PaymentDao;
import com.zfy.springcloud.entities.Payment;
import com.zfy.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: PaymentServiceImpl
 * @description:
 **/
@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private PaymentDao paymentDao;

    //    @Transactional
    @Override
    public int create(Payment payment) {
        int i = paymentDao.create(payment);
        int s = i / 0;
        return i;
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }

    @Override
    public List<Payment> getAllPayment() {
        return paymentDao.getAllPayment();
    }
}
