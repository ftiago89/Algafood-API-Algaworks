package com.felipemelo.algafood.domain.service;

import com.felipemelo.algafood.domain.entity.State;
import com.felipemelo.algafood.domain.exception.EntityInUseException;
import com.felipemelo.algafood.domain.exception.EntityNotFoundException;
import com.felipemelo.algafood.domain.exception.StateNotFoundException;
import com.felipemelo.algafood.domain.repository.IStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateRegisterService {

    public static final String STATE_IN_USE = "State with code %d cannot be removed. It's in use by other entities";

    @Autowired
    private IStateRepository stateRepository;

    public List<State> list(){
        return stateRepository.findAll();
    }

    public State findOrFail(Long id){
        return stateRepository.findById(id).orElseThrow(
                () -> new StateNotFoundException(id));
    }

    public State save(State state){
        return stateRepository.save(state);
    }

    public void delete(Long id){
        try{
            stateRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new StateNotFoundException(id);
        } catch (DataIntegrityViolationException e){
            throw new EntityInUseException(
                    String.format(STATE_IN_USE, id));
        }
    }
}
