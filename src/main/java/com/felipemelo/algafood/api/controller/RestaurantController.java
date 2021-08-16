package com.felipemelo.algafood.api.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.felipemelo.algafood.domain.entity.Restaurant;
import com.felipemelo.algafood.domain.exception.BusinessException;
import com.felipemelo.algafood.domain.exception.KitchenNotFoundException;
import com.felipemelo.algafood.domain.service.RestaurantRegisterService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    public Restaurant save(@RequestBody @Valid Restaurant restaurant){
        try{
            return restaurantRegisterService.save(restaurant);
        } catch(KitchenNotFoundException e){
            throw new BusinessException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Restaurant update(@PathVariable Long id, @RequestBody @Valid Restaurant restaurant){
        try{
            Restaurant foundRestaurant = restaurantRegisterService.findOrFail(id);
            BeanUtils.copyProperties(restaurant, foundRestaurant, "id", "paymentMethods", "creationDate",
                    "address", "products");

            return restaurantRegisterService.save(foundRestaurant);
        } catch(KitchenNotFoundException e){
            throw new BusinessException(e.getMessage());
        }
    }

    @PatchMapping("/{restaurantId}")
    public Restaurant atualizarParcial(@PathVariable Long restaurantId,
                                       @RequestBody Map<String, Object> fields, HttpServletRequest request) {
        Restaurant actualRestaurant = restaurantRegisterService.findOrFail(restaurantId);

        merge(fields, actualRestaurant, request);

        return update(restaurantId, actualRestaurant);
    }

    private void merge(Map<String, Object> originData, Restaurant destRestaurant, HttpServletRequest request) {
        var servletServerHttpRequest = new ServletServerHttpRequest(request);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            Restaurant origRestaurant = objectMapper.convertValue(originData, Restaurant.class);

            originData.forEach((propertyName, propertyValue) -> {
                Field field = ReflectionUtils.findField(Restaurant.class, propertyName);
                field.setAccessible(true);

                Object newValue = ReflectionUtils.getField(field, origRestaurant);

                ReflectionUtils.setField(field, destRestaurant, newValue);
            });
        } catch (IllegalArgumentException e) {
            var rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, servletServerHttpRequest);
        }

    }
}
