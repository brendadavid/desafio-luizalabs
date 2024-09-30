package com.desafio.luizalabs.api;

import com.desafio.luizalabs.IntegrationTests;
import com.desafio.luizalabs.WishListFixture;
import com.desafio.luizalabs.wishlist.infrastructure.WishlistRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@IntegrationTests
class WishListControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private WishlistRepository repository;

    @AfterEach
    protected void setupClear() {
        repository.deleteAll();
    }

    @Test
    @SneakyThrows
    @DisplayName(value = "Deve adicionar produtos em uma Wishlist já criada.")
    void deveCriarWishlistEAdicionarProdutos() {
        var wishListDTO = WishListFixture.criarDTO().build();

        this.mvc.perform(
                post("/wishList")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(wishListDTO))
        ).andExpect(status().isCreated());
    }

    @Test
    @SneakyThrows
    @DisplayName(value = "Deve retornar todos os produtos da wishlist por clienteId.")
    void deveRetornarProdutosPorClienteId() {
        var clienteId = 123L;
        var wishlist = WishListFixture.criarWishList().build();
        repository.save(wishlist);

        var response = this.mvc.perform(
                get("/wishList/produtos/" + clienteId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        response.andExpect(jsonPath("$.clienteId", equalTo(wishlist.getClienteId().intValue())))
                .andExpect(jsonPath("$.produtoIds", containsInAnyOrder(1, 3, 5)));
    }

    @Test
    @SneakyThrows
    @DisplayName(value = "Deve retornar um produto por id e por clienteId.")
    void deveRetornarProdutoPorIdEClienteId() {
        var clienteId = 123L;
        var produtoId = 1L;
        var wishlist = WishListFixture.criarWishList().build();
        repository.save(wishlist);

        var response = this.mvc.perform(
                get("/wishList/produto/" + clienteId)
                        .queryParam("produtoId", String.valueOf(produtoId))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        response.andExpect(jsonPath("$.produtoId", equalTo(wishlist.getProdutoIds().getFirst().intValue())));
    }

    @Test
    @SneakyThrows
    @DisplayName(value = "Deve retornar um produto por id e por clienteId.")
    void deveDeletarProdutoPorIdEClienteId() {
        var clienteId = 123L;
        var produtoId = 5L;
        var wishlist = WishListFixture.criarWishList().build();
        repository.save(wishlist);

        this.mvc.perform(
                delete("/wishList/" + clienteId)
                        .queryParam("produtoId", String.valueOf(produtoId))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());

        var wishlistAtualizada = repository.findByClienteId(wishlist.getClienteId()).orElseThrow();
        assertFalse(wishlistAtualizada.getProdutoIds().contains(5L));
    }
}
