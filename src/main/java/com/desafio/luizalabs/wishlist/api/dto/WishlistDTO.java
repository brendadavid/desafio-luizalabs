package com.desafio.luizalabs.wishlist.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class WishlistDTO {

    @NotNull(message = "O campo 'clienteId' não pode ser vazio")
    private Long clienteId;
    @NotEmpty(message = "O campo 'nomeProduto' não pode ser vazio")
    @Size(max = 50, message = "O campo 'nomeProduto' não pode conter mais de 50 caracteres.")
    private String nomeProduto;
    @NotEmpty(message = "O campo 'codigoProduto' não pode ser vazio")
    @Size(max = 10, message = "O campo 'codigoProduto' não pode conter mais de 10 caracteres.")
    private String codigoProduto;
}
