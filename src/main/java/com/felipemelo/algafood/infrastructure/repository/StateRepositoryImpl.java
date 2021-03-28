package com.felipemelo.algafood.infrastructure.repository;

import com.felipemelo.algafood.domain.entity.State;
import com.felipemelo.algafood.domain.repository.IStateRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class StateRepositoryImpl implements IStateRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<State> list() {
        return entityManager.createQuery("from State", State.class).getResultList();
    }

    @Override
    public State find(Long id) {
        return entityManager.find(State.class, id);
    }

    @Override
    @Transactional
    public State save(State state) {
        return entityManager.merge(state);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        State state = find(id);

        if (state == null){
            throw new EmptyResultDataAccessException(1);
        }

        entityManager.remove(state);
    }
}
