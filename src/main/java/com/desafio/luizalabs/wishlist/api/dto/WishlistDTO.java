package com.desafio.luizalabs.wishlist.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class WishlistDTO {

    @NotNull(message = "O campo 'clienteId' não pode ser vazio")
    private Long clienteId;
    @NotEmpty(message = "O campo 'produtosIds' não pode ser vazio")
    @Size(max = 20, message = "O campo 'produtosIds' não pode conter mais de 20 ids.")
    private List<Long> produtoIds;
}
