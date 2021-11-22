package com.felipemelo.algafood.api.controller;

import com.felipemelo.algafood.api.model.KitchenModel;
import com.felipemelo.algafood.api.model.RestaurantModel;
import com.felipemelo.algafood.domain.entity.Restaurant;
import com.felipemelo.algafood.domain.exception.BusinessException;
import com.felipemelo.algafood.domain.exception.KitchenNotFoundException;
import com.felipemelo.algafood.domain.service.RestaurantRegisterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantRegisterService restaurantRegisterService;

    @Autowired
    private SmartValidator validator;

    @GetMapping
    public List<RestaurantModel> list(){
        return toCollectionModel(restaurantRegisterService.list());
    }

    @GetMapping("/{id}")
    public RestaurantModel find(@PathVariable Long id){
        return toModel(restaurantRegisterService.findOrFail(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantModel save(@RequestBody @Valid Restaurant restaurant){
        try{
            return toModel(restaurantRegisterService.save(restaurant));
        } catch(KitchenNotFoundException e){
            throw new BusinessException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public RestaurantModel update(@PathVariable Long id, @RequestBody @Valid Restaurant restaurant){
        try{
            Restaurant foundRestaurant = restaurantRegisterService.findOrFail(id);
            BeanUtils.copyProperties(restaurant, foundRestaurant, "id", "paymentMethods", "creationDate",
                    "address", "products");

            return toModel(restaurantRegisterService.save(foundRestaurant));
        } catch(KitchenNotFoundException e){
            throw new BusinessException(e.getMessage());
        }
    }

    private RestaurantModel toModel(Restaurant restaurant) {
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

    private List<RestaurantModel> toCollectionModel(List<Restaurant> restaurants) {
        return restaurants.stream().map(this::toModel)
                .collect(Collectors.toList());
    }
}
