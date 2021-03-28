package com.felipemelo.algafood.domain.repository;

import com.felipemelo.algafood.domain.entity.Kitchen;

import java.util.List;

public interface IKitchenRepository {

    public List<Kitchen> list();

    public Kitchen find(Long id);

    public Kitchen save(Kitchen kitchen);

    public void delete(Long id);
}
