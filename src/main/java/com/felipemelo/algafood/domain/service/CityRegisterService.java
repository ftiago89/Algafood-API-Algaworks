package com.felipemelo.algafood.domain.service;

import com.felipemelo.algafood.domain.entity.City;
import com.felipemelo.algafood.domain.entity.State;
import com.felipemelo.algafood.domain.exception.CityNotFoundException;
import com.felipemelo.algafood.domain.exception.EntityInUseException;
import com.felipemelo.algafood.domain.repository.ICityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityRegisterService {

    public static final String CITY_IN_USE = "City with code %d cannot be removed. It's in use by other entities";

    @Autowired
    private ICityRepository cityRepository;

    @Autowired
    private StateRegisterService stateRegisterService;

    public List<City> list(){
        return cityRepository.findAll();
    }

    public City findOrFail(Long id){
        return cityRepository.findById(id).orElseThrow(
                () -> new CityNotFoundException(id));
    }

    public City save(City city){
        Long stateId = city.getState().getId();
        State state = stateRegisterService.findOrFail(stateId);
        city.setState(state);

        return cityRepository.save(city);
    }

    public void delete(Long id){
        try{
            cityRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new CityNotFoundException(id);
        } catch (DataIntegrityViolationException e){
            throw new EntityInUseException(
                    String.format(CITY_IN_USE, id));
        }
    }
}
