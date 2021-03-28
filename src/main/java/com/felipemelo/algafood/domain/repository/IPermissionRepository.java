package com.felipemelo.algafood.domain.repository;

import com.felipemelo.algafood.domain.entity.Permission;

import java.util.List;

public interface IPermissionRepository {

    public List<Permission> list();

    public Permission find(Long id);

    public Permission save(Permission permission);

    public void delete(Long id);
}
