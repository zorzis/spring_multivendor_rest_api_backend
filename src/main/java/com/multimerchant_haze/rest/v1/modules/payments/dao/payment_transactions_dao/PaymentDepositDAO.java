package com.multimerchant_haze.rest.v1.modules.payments.dao.payment_transactions_dao;

import com.multimerchant_haze.rest.v1.modules.payments.model.payment_transactions_model.PaymentDeposit;

import java.util.List;

/**
 * Created by zorzis on 9/30/2017.
 */
public interface PaymentDepositDAO
{
    public List<PaymentDeposit> getPaymentsDeposits();

    public PaymentDeposit getPaymentDepositByID(String paymentDepositID);
}
