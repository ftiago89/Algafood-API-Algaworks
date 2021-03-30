package com.felipemelo.algafood.domain.service;

import com.felipemelo.algafood.domain.entity.Kitchen;
import com.felipemelo.algafood.domain.entity.Restaurant;
import com.felipemelo.algafood.domain.exception.EntityInUseException;
import com.felipemelo.algafood.domain.exception.EntityNotFoundException;
import com.felipemelo.algafood.domain.repository.IKitchenRepository;
import com.felipemelo.algafood.domain.repository.IRestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantRegisterService {

    @Autowired
    private IRestaurantRepository restaurantRepository;

    @Autowired
    private IKitchenRepository kitchenRepository;

    public List<Restaurant> list(){
        return restaurantRepository.list();
    }

    public Restaurant find(Long id){
        return restaurantRepository.find(id);
    }

    public Restaurant save(Restaurant restaurant){
        Long kitchenId = restaurant.getKitchen().getId();
        Kitchen kitchen = kitchenRepository.find(kitchenId);

        if (kitchen == null){
            throw new EntityNotFoundException(
                    String.format("There is no kitchen with code: %d.", kitchenId));
        }

        restaurant.setKitchen(kitchen);
        return restaurantRepository.save(restaurant);
    }

    public void delete(Long id){
        try{
            restaurantRepository.delete(id);
        } catch (EmptyResultDataAccessException e){
            throw new EntityNotFoundException(String.format("There is no restaurant with code: %d", id));
        } catch (DataIntegrityViolationException e){
            throw new EntityInUseException(
                    String.format("Restaurant with code %d cannot be removed. It's in use by other entities", id));
        }
    }
}
