package com.felipemelo.algafood.api.controller;

import com.felipemelo.algafood.api.assembler.RestaurantModelAssembler;
import com.felipemelo.algafood.api.model.KitchenModel;
import com.felipemelo.algafood.api.model.RestaurantModel;
import com.felipemelo.algafood.api.model.input.RestaurantInput;
import com.felipemelo.algafood.domain.entity.Kitchen;
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

    @Autowired
    private RestaurantModelAssembler restaurantModelAssembler;

    @GetMapping
    public List<RestaurantModel> list(){
        return restaurantModelAssembler.toCollectionModel(restaurantRegisterService.list());
    }

    @GetMapping("/{id}")
    public RestaurantModel find(@PathVariable Long id){
        return restaurantModelAssembler.toModel(restaurantRegisterService.findOrFail(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantModel save(@RequestBody @Valid RestaurantInput restaurantInput){
        try{
            var restaurant = toDomainModel(restaurantInput);
            return restaurantModelAssembler.toModel(restaurantRegisterService.save(restaurant));
        } catch(KitchenNotFoundException e){
            throw new BusinessException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public RestaurantModel update(@PathVariable Long id, @RequestBody @Valid RestaurantInput restaurantInput){
        try{
            var restaurant = toDomainModel(restaurantInput);
            Restaurant foundRestaurant = restaurantRegisterService.findOrFail(id);
            BeanUtils.copyProperties(restaurant, foundRestaurant, "id", "paymentMethods", "creationDate",
                    "address", "products");

            return restaurantModelAssembler.toModel(restaurantRegisterService.save(foundRestaurant));
        } catch(KitchenNotFoundException e){
            throw new BusinessException(e.getMessage());
        }
    }

    private Restaurant toDomainModel(RestaurantInput restaurantInput) {
        var restaurant = new Restaurant();
        restaurant.setName(restaurantInput.getName());
        restaurant.setDeliveryTax(restaurantInput.getDeliveryTax());

        var kitchen = new Kitchen();
        kitchen.setId(restaurantInput.getKitchen().getId());

        restaurant.setKitchen(kitchen);

        return restaurant;
    }
}
