package com.felipemelo.algafood.infrastructure.repository;

import com.felipemelo.algafood.domain.entity.State;
import com.felipemelo.algafood.domain.repository.IStateRepository;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
    public void save(State state) {
        entityManager.merge(state);
    }

    @Override
    public void delete(Long id) {
        State state = find(id);

        if (state == null){
            throw new EmptyResultDataAccessException(1);
        }

        entityManager.remove(state);
    }
}
