package com.felipemelo.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class RestaurantInput {

    @NotBlank
    private String name;

    @PositiveOrZero
    @NotNull
    private BigDecimal deliveryTax;

    @Valid
    @NotNull
    private KitchenIdInput kitchen;
}
