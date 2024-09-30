package com.desafio.luizalabs;

import com.desafio.luizalabs.wishlist.api.dto.WishlistDTO;
import com.desafio.luizalabs.wishlist.domain.bo.WishlistBO;
import com.desafio.luizalabs.wishlist.domain.model.Wishlist;
import com.desafio.luizalabs.wishlist.infrastructure.ProdutoProjection;

import java.util.List;

public class WishListFixture {

    public static Wishlist.WishlistBuilder criarWishList() {
        return Wishlist.builder()
                .clienteId(123L)
                .produtoIds(List.of(1L, 3L, 5L));
    }

    public static WishlistBO.WishlistBOBuilder criarBO() {
        return WishlistBO.builder()
                .clienteId(123L)
                .produtoIds(List.of(1L, 3L, 5L));
    }

    public static WishlistDTO.WishlistDTOBuilder criarDTO() {
        return WishlistDTO.builder()
                .clienteId(123L)
                .produtoIds(List.of(1L, 3L, 5L));
    }

    public static ProdutoProjection.ProdutoProjectionBuilder criarProjection() {
        return ProdutoProjection.builder()
                .produtoIds(List.of(3L));
    }
}
