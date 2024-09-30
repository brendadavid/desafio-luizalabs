package com.desafio.luizalabs.wishlist.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BuscarProdutoDTO {

    private Long produtoId;
}
