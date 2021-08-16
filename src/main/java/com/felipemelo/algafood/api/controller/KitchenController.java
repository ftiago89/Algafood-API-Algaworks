package com.felipemelo.algafood.api.controller;

import com.felipemelo.algafood.domain.entity.Kitchen;
import com.felipemelo.algafood.domain.service.KitchenRegisterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public Kitchen find(@PathVariable Long id){
        return kitchenRegisterService.findOrFail(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Kitchen save(@RequestBody @Valid Kitchen kitchen){
        return kitchenRegisterService.save(kitchen);
    }

    @PutMapping("/{id}")
    public Kitchen update(@PathVariable Long id, @RequestBody @Valid Kitchen kitchen){
        Kitchen foundKitchen = kitchenRegisterService.findOrFail(id);
        BeanUtils.copyProperties(kitchen, foundKitchen, "id");

        return kitchenRegisterService.save(foundKitchen);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        kitchenRegisterService.delete(id);
    }
}
