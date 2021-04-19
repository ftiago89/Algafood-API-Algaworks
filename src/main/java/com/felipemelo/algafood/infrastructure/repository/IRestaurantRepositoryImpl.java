package com.felipemelo.algafood.infrastructure.repository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import com.felipemelo.algafood.domain.entity.Restaurant;
import com.felipemelo.algafood.domain.repository.IRestaurantRepository;
import com.felipemelo.algafood.domain.repository.IRestaurantRepositoryQueries;
import com.felipemelo.algafood.infrastructure.repository.specs.RestaurantSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class IRestaurantRepositoryImpl implements IRestaurantRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Autowired @Lazy
    private IRestaurantRepository restauranteRepository;

    @Override
    public List<Restaurant> find(String name,
                                 BigDecimal initialTax, BigDecimal finalTax) {
        var builder = manager.getCriteriaBuilder();

        var criteria = builder.createQuery(Restaurant.class);
        var root = criteria.from(Restaurant.class);

        var predicates = new ArrayList<Predicate>();

        if (StringUtils.hasText(name)) {
            predicates.add(builder.like(root.get("name"), "%" + name + "%"));
        }

        if (initialTax != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("deliveryTax"), initialTax));
        }

        if (finalTax != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("deliveryTax"), finalTax));
        }

        criteria.where(predicates.toArray(new Predicate[0]));

        var query = manager.createQuery(criteria);
        return query.getResultList();
    }

    @Override
    public List<Restaurant> findWithFreeDelivery(String name) {
        return restauranteRepository.findAll(RestaurantSpecs.withFreeDelivery()
                .and(RestaurantSpecs.withAlikeName(name)));
    }

}
