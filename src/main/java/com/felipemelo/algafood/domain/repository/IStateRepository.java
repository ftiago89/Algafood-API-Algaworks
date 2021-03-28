package com.felipemelo.algafood.domain.repository;

import com.felipemelo.algafood.domain.entity.State;

import java.util.List;

public interface IStateRepository {

    public List<State> list();

    public State find(Long id);

    public State save(State state);

    public void delete(Long id);
}
