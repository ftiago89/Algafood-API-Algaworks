package com.felipemelo.algafood.api.assembler;

import com.felipemelo.algafood.api.model.input.RestaurantInput;
import com.felipemelo.algafood.domain.entity.Kitchen;
import com.felipemelo.algafood.domain.entity.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class RestaurantInputDisassembler {

    public Restaurant toDomainModel(RestaurantInput restaurantInput) {
        var restaurant = new Restaurant();
        restaurant.setName(restaurantInput.getName());
        restaurant.setDeliveryTax(restaurantInput.getDeliveryTax());

        var kitchen = new Kitchen();
        kitchen.setId(restaurantInput.getKitchen().getId());

        restaurant.setKitchen(kitchen);

        return restaurant;
    }
}
