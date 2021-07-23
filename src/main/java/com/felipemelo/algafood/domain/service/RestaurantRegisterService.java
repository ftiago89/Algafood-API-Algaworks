package com.felipemelo.algafood.domain.service;

import com.felipemelo.algafood.domain.entity.Kitchen;
import com.felipemelo.algafood.domain.entity.Restaurant;
import com.felipemelo.algafood.domain.exception.EntityInUseException;
import com.felipemelo.algafood.domain.exception.EntityNotFoundException;
import com.felipemelo.algafood.domain.repository.IRestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantRegisterService {

    public static final String RESTAURANT_NOT_FOUND = "Restaurant with code %d not found.";
    public static final String RESTAURANT_IN_USE = "Restaurant with code %d cannot be removed. It's in use by other entities";

    @Autowired
    private IRestaurantRepository restaurantRepository;

    @Autowired
    private KitchenRegisterService kitchenRegisterService;

    public List<Restaurant> list(){
        return restaurantRepository.findAll();
    }

    public Restaurant findOrFail(Long id){
        return restaurantRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException((String.format(RESTAURANT_NOT_FOUND, id))));
    }

    public Restaurant save(Restaurant restaurant){
        Long kitchenId = restaurant.getKitchen().getId();
        Kitchen kitchen = kitchenRegisterService.findOrFail(kitchenId);
        restaurant.setKitchen(kitchen);

        return restaurantRepository.save(restaurant);
    }
}
