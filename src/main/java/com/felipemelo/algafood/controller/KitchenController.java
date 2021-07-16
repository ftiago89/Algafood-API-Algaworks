package com.felipemelo.algafood.controller;

import com.felipemelo.algafood.domain.entity.Kitchen;
import com.felipemelo.algafood.domain.service.KitchenRegisterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kitchens")
public class KitchenController {

    @Autowired
    private KitchenRegisterService kitchenRegisterService;

    @GetMapping
    public List<Kitchen> list(){
        return kitchenRegisterService.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kitchen> find(@PathVariable Long id){
        Kitchen kitchen = kitchenRegisterService.find(id);

        if (kitchen == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(kitchen);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Kitchen save(@RequestBody Kitchen kitchen){
        return kitchenRegisterService.save(kitchen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Kitchen> update(@PathVariable Long id, @RequestBody Kitchen kitchen){
        Kitchen foundKitchen = kitchenRegisterService.find(id);

        if (foundKitchen == null){
            return ResponseEntity.notFound().build();
        }

        BeanUtils.copyProperties(kitchen, foundKitchen, "id");
        foundKitchen = kitchenRegisterService.save(foundKitchen);
        return ResponseEntity.ok(foundKitchen);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        kitchenRegisterService.remove(id);
    }
}
