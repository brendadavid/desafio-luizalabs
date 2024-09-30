package com.desafio.luizalabs.wishlist.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ProdutoProjection {

    private List<Long> produtoIds;
}
