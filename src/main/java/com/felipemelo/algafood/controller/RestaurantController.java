package com.felipemelo.algafood.controller;

import com.felipemelo.algafood.domain.entity.Restaurant;
import com.felipemelo.algafood.domain.exception.EntityInUseException;
import com.felipemelo.algafood.domain.exception.EntityNotFoundException;
import com.felipemelo.algafood.domain.service.RestaurantRegisterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Restaurant> find(@PathVariable Long id){
        Restaurant restaurant = restaurantRegisterService.find(id);

        if (restaurant == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(restaurant);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Restaurant restaurant){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurantRegisterService.save(restaurant));
        } catch (EntityNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Restaurant restaurant){
        Restaurant foundRestaurant = restaurantRegisterService.find(id);

        if (restaurant == null){
            return ResponseEntity.notFound().build();
        }

        BeanUtils.copyProperties(restaurant, foundRestaurant, "id", "paymentMethods", "address");
        try {
            return ResponseEntity.ok(restaurantRegisterService.save(restaurant));
        } catch (EntityNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try{
            restaurantRegisterService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (EntityInUseException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
