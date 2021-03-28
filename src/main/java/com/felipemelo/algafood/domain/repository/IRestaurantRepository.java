package com.felipemelo.algafood.domain.repository;

import com.felipemelo.algafood.domain.entity.Restaurant;

import java.util.List;

public interface IRestaurantRepository {

    public List<Restaurant> list();

    public Restaurant find(Long id);

    public Restaurant save(Restaurant restaurant);

    public void delete(Long id);
}
