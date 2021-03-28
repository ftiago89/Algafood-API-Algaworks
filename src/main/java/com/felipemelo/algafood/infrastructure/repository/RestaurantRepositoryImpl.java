package com.felipemelo.algafood.infrastructure.repository;

import com.felipemelo.algafood.domain.entity.Restaurant;
import com.felipemelo.algafood.domain.repository.IRestaurantRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class RestaurantRepositoryImpl implements IRestaurantRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Restaurant> list() {
        return entityManager.createQuery("from Restaurant", Restaurant.class).getResultList();
    }

    @Override
    public Restaurant find(Long id) {
        return entityManager.find(Restaurant.class, id);
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return entityManager.merge(restaurant);
    }

    @Override
    public void delete(Long id) {
        Restaurant restaurant = find(id);

        if (restaurant == null){
            throw new EmptyResultDataAccessException(1);
        }

        entityManager.remove(restaurant);
    }
}
