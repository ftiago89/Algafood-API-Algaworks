package com.felipemelo.algafood.infrastructure.repository;

import com.felipemelo.algafood.domain.entity.Permission;
import com.felipemelo.algafood.domain.repository.IPermissionRepository;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class PermissionRepositoryImpl implements IPermissionRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Permission> list() {
        return entityManager.createQuery("from Permission", Permission.class).getResultList();
    }

    @Override
    public Permission find(Long id) {
        return entityManager.find(Permission.class, id);
    }

    @Override
    public void save(Permission permission) {
        entityManager.merge(permission);
    }

    @Override
    public void delete(Long id) {
        Permission permission = find(id);

        if (permission == null){
            throw new EmptyResultDataAccessException(1);
        }

        entityManager.remove(permission);
    }
}
