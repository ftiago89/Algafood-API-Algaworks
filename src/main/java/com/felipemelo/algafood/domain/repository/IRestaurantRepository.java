package com.felipemelo.algafood.domain.repository;

import com.felipemelo.algafood.domain.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface IRestaurantRepository
        extends CustomJpaRepository<Restaurant, Long>, IRestaurantRepositoryQueries,
        JpaSpecificationExecutor<Restaurant> {

    @Query("from Restaurant r join fetch r.kitchen left join fetch r.paymentMethods")
    List<Restaurant> findAll();

    List<Restaurant> queryByDeliveryTaxBetween(BigDecimal initialTax, BigDecimal finalTax);

    @Query("from Restaurant where name like %:name% and kitchen.id = :id")
    List<Restaurant> consultingByName(String name, @Param("id") Long kitchen);

    Optional<Restaurant> findFirstRestaurantByNameContaining(String nome);

    List<Restaurant> findTop2ByNameContaining(String nome);

    int countByKitchenId(Long cozinha);
}
