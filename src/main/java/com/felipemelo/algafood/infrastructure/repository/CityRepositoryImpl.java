package com.felipemelo.algafood.infrastructure.repository;

import com.felipemelo.algafood.domain.entity.City;
import com.felipemelo.algafood.domain.repository.ICityRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class CityRepositoryImpl implements ICityRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<City> list() {
        return entityManager.createQuery("from City", City.class).getResultList();
    }

    @Override
    public City find(Long id) {
        return entityManager.find(City.class, id);
    }

    @Override
    @Transactional
    public City save(City city) {
        return entityManager.merge(city);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        City city = find(id);

        if (city == null){
            throw new EmptyResultDataAccessException(1);
        }

        entityManager.remove(city);
    }
}
