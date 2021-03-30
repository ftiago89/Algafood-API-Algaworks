package com.felipemelo.algafood.controller;

import com.felipemelo.algafood.domain.entity.City;
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
    public ResponseEntity<City> find(@PathVariable Long id){
        City city = cityRegisterService.find(id);

        if (city == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(city);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody City city){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(cityRegisterService.save(city));
        } catch (EntityNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody City city){
        City foundCity = cityRegisterService.find(id);

        if (city == null){
            return ResponseEntity.notFound().build();
        }

        BeanUtils.copyProperties(city, foundCity, "id");
        try {
            return ResponseEntity.ok(cityRegisterService.save(city));
        } catch (EntityNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try{
           cityRegisterService.delete(id);
           return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (EntityInUseException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
