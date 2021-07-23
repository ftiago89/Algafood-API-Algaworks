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

    public static final String KITCHEN_NOT_FOUND = "Kitchen with code %d not found.";
    public static final String KITCHEN_IN_USE = "Kitchen with code %d cannot be removed. It's in use by other entities";

    @Autowired
    private IKitchenRepository kitchenRepository;

    public List<Kitchen> list(){
        return kitchenRepository.findAll();
    }

    public Kitchen findOrFail(Long id){
        return kitchenRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException((String.format(KITCHEN_NOT_FOUND, id))));
    }

    public Kitchen save(Kitchen kitchen){
        return kitchenRepository.save(kitchen);
    }

    public void delete(Long id){
        try{
            kitchenRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new EntityNotFoundException(String.format(KITCHEN_NOT_FOUND, id));
        } catch (DataIntegrityViolationException e){
            throw new EntityInUseException(
                    String.format(KITCHEN_IN_USE, id));
        }
    }
}
