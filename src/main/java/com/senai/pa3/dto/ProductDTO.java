package com.senai.pa3.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.senai.pa3.entities.Product;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO implements Serializable {

    private Long id;

    @NotBlank(message = "Campo obrigatório")
    @JsonProperty("name")
    private String name;

    @NotBlank(message = "Campo obrigatório")
    @JsonProperty("description")
    private String description;

    public ProductDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
    }
}
