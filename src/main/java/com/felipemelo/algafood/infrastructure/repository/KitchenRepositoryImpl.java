package com.felipemelo.algafood.infrastructure.repository;

import com.felipemelo.algafood.domain.entity.Kitchen;
import com.felipemelo.algafood.domain.repository.IKitchenRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class KitchenRepositoryImpl implements IKitchenRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Kitchen> list() {
        return entityManager.createQuery("from Kitchen", Kitchen.class).getResultList();
    }

    @Override
    public Kitchen find(Long id) {
        return entityManager.find(Kitchen.class, id);
    }

    @Override
    @Transactional
    public Kitchen save(Kitchen kitchen) {
        return entityManager.merge(kitchen);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Kitchen kitchen = find(id);

        if (kitchen == null){
            throw new EmptyResultDataAccessException(1);
        }

        entityManager.remove(kitchen);
    }
}
