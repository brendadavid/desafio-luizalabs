package com.desafio.luizalabs.domain.mappers;

import com.desafio.luizalabs.WishListFixture;
import com.desafio.luizalabs.wishlist.api.WishlistDTOMapper;
import com.desafio.luizalabs.wishlist.api.WishlistDTOMapperImpl;
import com.desafio.luizalabs.wishlist.domain.WishlistMapper;
import com.desafio.luizalabs.wishlist.domain.WishlistMapperImpl;
import com.desafio.luizalabs.wishlist.domain.bo.ProdutoBO;
import com.desafio.luizalabs.wishlist.domain.bo.WishlistBO;
import com.desafio.luizalabs.wishlist.domain.model.Wishlist;
import com.desafio.luizalabs.wishlist.infrastructure.ProdutoProjection;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

class WishlistMappersTest {

    private WishlistMapper mapper;
    private WishlistDTOMapper dtoMapper;

    @BeforeEach
    protected void setUp() {
        mapper = new WishlistMapperImpl();
        dtoMapper = new WishlistDTOMapperImpl();
    }

    @Test
    @SneakyThrows
    @DisplayName(value = "Deve retornar retornar um UUID valido para o campo wishlistId.")
    void deveRetornarUUIDValidoParaWishlistId() {
        var bo = WishListFixture.criarBO().build();
        var wishlist = mapper.toModel(bo);

        assertNotNull(wishlist.getWishListId());
        assertTrue(wishlist.getWishListId().matches("[0-9a-fA-F-]{36}"));
        assertEquals(bo.getClienteId(), wishlist.getClienteId());
    }

    @Test
    @SneakyThrows
    @DisplayName("Deve retornar null ao mapear WishlistBO nulo para Model")
    void deveRetornarNullQuandoWishlistBONulo() {
        assertNull(mapper.toModel(null));
    }

    @Test
    @SneakyThrows
    @DisplayName(value = "Deve mapear o produtoId retornado como list para Long no campo produtoId do BO.")
    void deveMapearListParaLong() {
        var projection = WishListFixture.criarProjection().build();
        var bo = mapper.toBO(projection);

        assertEquals(3L, bo.getProdutoId());
    }

    @Test
    @DisplayName("Deve retornar null ao mapear Wishlist nulo para WishlistBO")
    void deveRetornarNullQuandoWishlistNulo() {
        assertNull(mapper.toBO((Wishlist) null));
    }

    @Test
    @DisplayName("Deve retornar null ao mapear ProdutoProjection nulo para ProdutoBO")
    void deveRetornarNullQuandoProjectionNulo() {
        assertNull(mapper.toBO((ProdutoProjection) null));
    }

    @Test
    @SneakyThrows
    @DisplayName("Deve retornar null ao mapear WishlistDTO nulo para WishlistBO")
    void deveRetornarNullQuandoWishlistBONulotoBO() {
        assertNull(dtoMapper.toBO(null));
    }

    @Test
    @SneakyThrows
    @DisplayName("Deve retornar null ao mapear WishlistBO nulo para BuscarWishlistDTO")
    void deveRetornarNullQuandoWishlistBONuloBuscarWishlist() {
        assertNull(dtoMapper.toDTO((WishlistBO) null));
    }

    @Test
    @SneakyThrows
    @DisplayName("Deve retornar null ao mapear ProdutoBO nulo para BuscarProdutoDTO")
    void deveRetornarNullQuandoWishlistBONuloBuscarProduto() {
        assertNull(dtoMapper.toDTO((ProdutoBO) null));
    }
}
