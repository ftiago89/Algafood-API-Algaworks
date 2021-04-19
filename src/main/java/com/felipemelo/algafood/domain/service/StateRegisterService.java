package com.felipemelo.algafood.domain.service;

import com.felipemelo.algafood.domain.entity.State;
import com.felipemelo.algafood.domain.exception.EntityInUseException;
import com.felipemelo.algafood.domain.exception.EntityNotFoundException;
import com.felipemelo.algafood.domain.repository.IStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateRegisterService {

    @Autowired
    private IStateRepository stateRepository;

    public List<State> list(){
        return stateRepository.findAll();
    }

    public State find(Long id){
        return stateRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException((String.format("State with code %d not found.", id))));
    }

    public State save(State state){
        return stateRepository.save(state);
    }

    public void delete(Long id){
        try{
            stateRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new EntityNotFoundException(String.format("There is no state with code: %d", id));
        } catch (DataIntegrityViolationException e){
            throw new EntityInUseException(
                    String.format("State with code %d cannot be removed. It's in use by other entities", id));
        }
    }
}
