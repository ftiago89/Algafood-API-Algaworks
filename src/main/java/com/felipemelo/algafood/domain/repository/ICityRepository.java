package com.felipemelo.algafood.domain.repository;

import com.felipemelo.algafood.domain.entity.City;

import java.util.List;

public interface ICityRepository {

    public List<City> list();

    public City find(Long id);

    public void save(City city);

    public void delete(Long id);
}
