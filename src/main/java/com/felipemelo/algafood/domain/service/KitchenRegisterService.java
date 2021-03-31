package com.felipemelo.algafood.domain.service;

import com.felipemelo.algafood.domain.entity.Kitchen;
import com.felipemelo.algafood.domain.exception.EntityInUseException;
import com.felipemelo.algafood.domain.exception.EntityNotFoundException;
import com.felipemelo.algafood.domain.repository.IKitchenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KitchenRegisterService {

    @Autowired
    private IKitchenRepository kitchenRepository;

    public List<Kitchen> list(){
        return kitchenRepository.findAll();
    }

    public Kitchen find(Long id){
        return kitchenRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException((String.format("Kitchen with code %d not found.", id))));
    }

    public Kitchen save(Kitchen kitchen){
        return kitchenRepository.save(kitchen);
    }

    public void remove(Long id){
        try{
            kitchenRepository.delete(find(id));
        } catch (EmptyResultDataAccessException e){
            throw new EntityNotFoundException(String.format("There is no kitchen with code: %d", id));
        } catch (DataIntegrityViolationException e){
            throw new EntityInUseException(
                    String.format("Kitchen with code %d cannot be removed. It's in use by other entities", id));
        }
    }
}
