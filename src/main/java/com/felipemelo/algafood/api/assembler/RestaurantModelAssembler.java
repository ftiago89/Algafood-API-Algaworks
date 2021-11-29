package com.felipemelo.algafood.api.assembler;

import com.felipemelo.algafood.api.model.KitchenModel;
import com.felipemelo.algafood.api.model.RestaurantModel;
import com.felipemelo.algafood.domain.entity.Restaurant;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestaurantModelAssembler {

    public RestaurantModel toModel(Restaurant restaurant) {
        var kitchenModel = new KitchenModel();
        kitchenModel.setId(restaurant.getKitchen().getId());
        kitchenModel.setName(restaurant.getKitchen().getName());

        var restaurantModel = new RestaurantModel();
        restaurantModel.setId(restaurant.getId());
        restaurantModel.setName(restaurant.getName());
        restaurantModel.setDeliveryTax(restaurant.getDeliveryTax());
        restaurantModel.setKitchen(kitchenModel);

        return restaurantModel;
    }

    public List<RestaurantModel> toCollectionModel(List<Restaurant> restaurants) {
        return restaurants.stream().map(this::toModel)
                .collect(Collectors.toList());
    }
}
