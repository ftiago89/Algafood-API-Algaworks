package com.felipemelo.algafood.domain.repository;

import com.felipemelo.algafood.domain.entity.Restaurant;

import java.math.BigDecimal;
import java.util.List;

public interface IRestaurantRepositoryQueries {

    List<Restaurant> find(String name, BigDecimal initialDeliveryTax, BigDecimal finalDeliveryTax);

    List<Restaurant> findWithFreeDelivery(String name);
}
