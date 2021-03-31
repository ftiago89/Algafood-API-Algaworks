package com.felipemelo.algafood.domain.repository;

import com.felipemelo.algafood.domain.entity.Kitchen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IKitchenRepository extends JpaRepository<Kitchen, Long> {
}
