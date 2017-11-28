package com.multimerchant_haze.rest.v1.modules.payments.dao.payment_transactions_dao;

import com.multimerchant_haze.rest.v1.modules.payments.model.payment_transactions_model.PaymentApproval;

import java.util.List;

/**
 * Created by zorzis on 9/30/2017.
 */
public interface PaymentApprovalDAO
{
    public List<PaymentApproval> getPaymentsApprovals();

    public PaymentApproval getPaymentApprovalByID(String paymentApprovalID);
}
