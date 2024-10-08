package com.desafio.luizalabs.wishlist.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class BuscarWishlistDTO {

    private String wishListId;
    private Long clienteId;
    private List<Long> produtoIds;
}