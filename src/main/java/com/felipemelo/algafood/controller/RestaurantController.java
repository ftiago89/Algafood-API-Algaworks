package com.felipemelo.algafood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.felipemelo.algafood.domain.entity.Restaurant;
import com.felipemelo.algafood.domain.exception.BusinessException;
import com.felipemelo.algafood.domain.exception.EntityNotFoundException;
import com.felipemelo.algafood.domain.service.RestaurantRegisterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantRegisterService restaurantRegisterService;

    @GetMapping
    public List<Restaurant> list(){
        return restaurantRegisterService.list();
    }

    @GetMapping("/{id}")
    public Restaurant find(@PathVariable Long id){
        return restaurantRegisterService.findOrFail(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant save(@RequestBody Restaurant restaurant){
        try{
            return restaurantRegisterService.save(restaurant);
        } catch(EntityNotFoundException e){
            throw new BusinessException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Restaurant update(@PathVariable Long id, @RequestBody Restaurant restaurant){
        Restaurant foundRestaurant = restaurantRegisterService.findOrFail(id);
        BeanUtils.copyProperties(restaurant, foundRestaurant, "id", "paymentMethods", "creationDate",
                "address", "products");

        try{
            return restaurantRegisterService.save(foundRestaurant);
        } catch(EntityNotFoundException e){
            throw new BusinessException(e.getMessage());
        }
    }

    @PatchMapping("/{restaurantId}")
    public Restaurant atualizarParcial(@PathVariable Long restaurantId,
                                        @RequestBody Map<String, Object> fields) {
        Restaurant actualRestaurant = restaurantRegisterService.findOrFail(restaurantId);

        merge(fields, actualRestaurant);

        return update(restaurantId, actualRestaurant);
    }

    private void merge(Map<String, Object> originData, Restaurant destRestaurant) {
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurant origRestaurant = objectMapper.convertValue(originData, Restaurant.class);

        originData.forEach((propertyName, propertyValue) -> {
            Field field = ReflectionUtils.findField(Restaurant.class, propertyName);
            field.setAccessible(true);

            Object newValue = ReflectionUtils.getField(field, origRestaurant);

//			System.out.println(nomePropriedade + " = " + valorPropriedade + " = " + novoValor);

            ReflectionUtils.setField(field, destRestaurant, newValue);
        });
    }
}
