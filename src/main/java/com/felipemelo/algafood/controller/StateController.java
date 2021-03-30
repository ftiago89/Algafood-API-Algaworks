package com.felipemelo.algafood.controller;

import com.felipemelo.algafood.domain.entity.State;
import com.felipemelo.algafood.domain.exception.EntityInUseException;
import com.felipemelo.algafood.domain.exception.EntityNotFoundException;
import com.felipemelo.algafood.domain.service.StateRegisterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<State> find(@PathVariable Long id){
        State state = stateRegisterService.find(id);

        if (state == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(state);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public State save(@RequestBody State state){
        return stateRegisterService.save(state);
    }

    @PutMapping("/{id}")
    public ResponseEntity<State> update(@PathVariable Long id, @RequestBody State state){
        State foundState = stateRegisterService.find(id);

        if (foundState == null){
            return ResponseEntity.notFound().build();
        }

        BeanUtils.copyProperties(state, foundState, "id");
        foundState = stateRegisterService.save(foundState);
        return ResponseEntity.ok(foundState);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try{
            stateRegisterService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (EntityInUseException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
