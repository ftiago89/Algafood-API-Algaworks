package com.felipemelo.algafood.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.felipemelo.algafood.core.validation.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class City {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @JsonIgnoreProperties(value = "name", allowGetters = true)
    @ManyToOne
    @Valid
    @ConvertGroup(from = Default.class, to = Groups.StateId.class)
    @NotNull
    @JoinColumn(nullable = false)
    private State state;
}
