package com.felipemelo.algafood.controller;

import com.felipemelo.algafood.domain.entity.City;
import com.felipemelo.algafood.domain.exception.BusinessException;
import com.felipemelo.algafood.domain.exception.EntityInUseException;
import com.felipemelo.algafood.domain.exception.EntityNotFoundException;
import com.felipemelo.algafood.domain.service.CityRegisterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {
    
    @Autowired
    private CityRegisterService cityRegisterService;

    @GetMapping
    public List<City> list(){
        return cityRegisterService.list();
    }

    @GetMapping("/{id}")
    public City find(@PathVariable Long id){
        return cityRegisterService.findOrFail(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public City save(@RequestBody City city){
        try {
            return cityRegisterService.save(city);
        } catch (EntityNotFoundException e){
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public City update(@PathVariable Long id, @RequestBody City city){
        City foundCity = cityRegisterService.findOrFail(id);
        BeanUtils.copyProperties(city, foundCity, "id");

        try {
            return cityRegisterService.save(foundCity);
        } catch (EntityNotFoundException e){
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        cityRegisterService.delete(id);
    }
}
