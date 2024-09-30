package com.desafio.luizalabs.wishlist.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class ProdutoProjection {

    private List<Long> produtoIds;
}
