package com.felipemelo.algafood.infrastructure.repository.specs;

import com.felipemelo.algafood.domain.entity.Restaurant;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class RestaurantSpecs {

    public static Specification<Restaurant> withFreeDelivery(){
        return ((root, query, builder) -> builder.equal(root.get("deliveryTax"), BigDecimal.ZERO));
    }

    public static Specification<Restaurant> withAlikeName(String name){
        return ((root, query, builder) -> builder.like(root.get("name"),"%" + name + "%"));
    }
}
