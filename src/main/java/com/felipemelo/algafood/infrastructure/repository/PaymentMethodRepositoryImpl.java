package com.felipemelo.algafood.infrastructure.repository;

import com.felipemelo.algafood.domain.entity.PaymentMethod;
import com.felipemelo.algafood.domain.repository.IPaymentMethodRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class PaymentMethodRepositoryImpl implements IPaymentMethodRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<PaymentMethod> list() {
        return entityManager.createQuery("from PaymentMethod", PaymentMethod.class).getResultList();
    }

    @Override
    public PaymentMethod find(Long id) {
        return entityManager.find(PaymentMethod.class, id);
    }

    @Override
    @Transactional
    public PaymentMethod save(PaymentMethod paymentMethod) {
        return entityManager.merge(paymentMethod);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        PaymentMethod paymentMethod = find(id);

        if (paymentMethod == null){
            throw new EmptyResultDataAccessException(1);
        }

        entityManager.remove(paymentMethod);
    }
}
