package com.felipemelo.algafood.domain.service;

import com.felipemelo.algafood.domain.entity.City;
import com.felipemelo.algafood.domain.entity.State;
import com.felipemelo.algafood.domain.exception.EntityInUseException;
import com.felipemelo.algafood.domain.exception.EntityNotFoundException;
import com.felipemelo.algafood.domain.repository.ICityRepository;
import com.felipemelo.algafood.domain.repository.IStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityRegisterService {

    @Autowired
    private ICityRepository cityRepository;

    @Autowired
    private IStateRepository stateRepository;

    public List<City> list(){
        return cityRepository.findAll();
    }

    public City find(Long id){
        return cityRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException((String.format("City with code %d not found.", id))));
    }

    public City save(City city){
        Long stateId = city.getState().getId();
        State state = stateRepository.findById(stateId).orElseThrow(() -> new EntityNotFoundException(
                (String.format("State with code %d not found.", stateId))));

        city.setState(state);
        return cityRepository.save(city);
    }

    public void delete(Long id){
        try{
            cityRepository.delete(find(id));
        } catch (EmptyResultDataAccessException e){
            throw new EntityNotFoundException(String.format("There is no city with code: %d", id));
        } catch (DataIntegrityViolationException e){
            throw new EntityInUseException(
                    String.format("City with code %d cannot be removed. It's in use by other entities", id));
        }
    }
}
