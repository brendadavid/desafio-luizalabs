package com.desafio.luizalabs.domain;

import com.desafio.luizalabs.WishListFixture;
import com.desafio.luizalabs.wishlist.domain.WishListValidator;
import com.desafio.luizalabs.wishlist.domain.WishlistListagemService;
import com.desafio.luizalabs.wishlist.domain.WishlistMapper;
import com.desafio.luizalabs.wishlist.domain.WishlistMapperImpl;
import com.desafio.luizalabs.wishlist.infrastructure.WishlistRepository;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WishlistListagemServiceTest {

    private WishlistListagemService service;
    private WishlistRepository repository;
    private WishlistMapper mapper;
    private MongoOperations mongoOperations;

    @BeforeEach
    protected void setUp() {
        repository = mock(WishlistRepository.class);
        var validator = new WishListValidator();
        mapper = new WishlistMapperImpl();
        mongoOperations = mock(MongoOperations.class);

        service = new WishlistListagemService(
                repository,
                validator,
                mapper,
                mongoOperations
        );
    }

    @Test
    @SneakyThrows
    @DisplayName(value = "Deve buscar todos os produtos cadastrados para o clienteId.")
    void deveBuscarTodosOsProdutosPorClienteId() {
        var id = UUID.randomUUID().toString();
        var wishList = WishListFixture.criarWishList().wishListId(id).build();

        when(repository.buscarTodosOsProdutos(mongoOperations, 123L)).thenReturn(wishList);
        mapper.toBO(wishList);

        var wishlistBO = service.buscarProdutos(123L);

        assert wishlistBO != null;

        assertThat(wishlistBO.getWishListId(), Matchers.is(wishList.getWishListId()));
        assertThat(wishlistBO.getClienteId(), Matchers.is(wishList.getClienteId()));
        assertThat(wishlistBO.getProdutoIds(), Matchers.is(wishList.getProdutoIds()));
    }

    @Test
    @SneakyThrows
    @DisplayName(value = "Deve apresentar erro ao buscar todos os produtos quando não houver produtos cadastrados para o clienteId.")
    void deveApresentarErroAoBuscarTodosOsProdutosPorClienteId() {
        Exception exception = assertThrows(Exception.class, () ->
                service.buscarProdutos(1L)
        );

        String expectedMessage = "Não existe nenhum produto cadastrado na Wishlist com clienteId 1.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @SneakyThrows
    @DisplayName(value = "Deve buscar um produto cadastrado para o produtoId e clienteId informados.")
    void deveBuscarUmProdutoPorId() {
        var projection = WishListFixture.criarProjection().build();

        when(repository.buscarProdutoPorId(mongoOperations, 123L, 3L)).thenReturn(projection);

        var produtoBO = service.buscarProdutoPorId(123L, 3L);

        assert produtoBO != null;

        assertThat(produtoBO.getProdutoId(), Matchers.is(projection.getProdutoIds().getFirst()));
    }

    @Test
    @SneakyThrows
    @DisplayName(value = "Deve apresentar erro ao buscar um produto quando não houver produto cadastrado para o produtoId e clienteId informados.")
    void deveApresentarErroAoBuscarUmProdutoPorId() {
        Exception exception = assertThrows(Exception.class, () ->
                service.buscarProdutoPorId(1L, 1567L)
        );

        String expectedMessage = "Não existe um produto com id 1567 cadastrado na Wishlist para esse clienteId: 1.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
