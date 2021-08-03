package com.felipemelo.algafood.api.controller;

import com.felipemelo.algafood.domain.entity.State;
import com.felipemelo.algafood.domain.service.StateRegisterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/states")
public class StateController {

    @Autowired
    private StateRegisterService stateRegisterService;

    @GetMapping
    public List<State> list(){
        return stateRegisterService.list();
    }

    @GetMapping("/{id}")
    public State find(@PathVariable Long id){
        return stateRegisterService.findOrFail(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public State save(@RequestBody State state){
        return stateRegisterService.save(state);
    }

    @PutMapping("/{id}")
    public State update(@PathVariable Long id, @RequestBody State state){
        State foundState = stateRegisterService.findOrFail(id);
        BeanUtils.copyProperties(state, foundState, "id");

        return stateRegisterService.save(foundState);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        stateRegisterService.delete(id);
    }
}
