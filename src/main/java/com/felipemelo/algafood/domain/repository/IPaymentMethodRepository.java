package com.felipemelo.algafood.domain.repository;

import com.felipemelo.algafood.domain.entity.PaymentMethod;

import java.util.List;

public interface IPaymentMethodRepository {

    public List<PaymentMethod> list();

    public PaymentMethod find(Long id);

    public PaymentMethod save(PaymentMethod paymentMethod);

    public void delete(Long id);
}
