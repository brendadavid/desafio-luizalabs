package com.desafio.luizalabs.wishlist.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class WishlistDTO {

    @NotEmpty(message = "O campo 'nome' não pode ser vazio")
    @Size(max = 50, message = "O campo 'nome' não pode conter mais de 50 caracteres.")
    private String nome;
    @NotEmpty(message = "O campo 'codigo' não pode ser vazio")
    @Size(max = 10, message = "O campo 'codigo' não pode conter mais de 10 caracteres.")
    private String codigo;
}
