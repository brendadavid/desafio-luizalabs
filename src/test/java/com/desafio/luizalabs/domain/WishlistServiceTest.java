package com.desafio.luizalabs.domain;

import com.desafio.luizalabs.WishListFixture;
import com.desafio.luizalabs.wishlist.domain.WishListValidator;
import com.desafio.luizalabs.wishlist.domain.WishlistMapper;
import com.desafio.luizalabs.wishlist.domain.WishlistMapperImpl;
import com.desafio.luizalabs.wishlist.domain.WishlistService;
import com.desafio.luizalabs.wishlist.domain.model.Wishlist;
import com.desafio.luizalabs.wishlist.infrastructure.WishlistRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WishlistServiceTest {

    private WishlistService service;
    private WishlistRepository repository;
    private WishlistMapper mapper;
    private MongoOperations mongoOperations;

    @BeforeEach
    protected void setUp() {
        repository = mock(WishlistRepository.class);
        WishListValidator validator = new WishListValidator();
        mapper = new WishlistMapperImpl();
        mongoOperations = mock(MongoOperations.class);

        service = new WishlistService(
                repository,
                validator,
                mapper,
                mongoOperations
        );
    }

    @Test
    @SneakyThrows
    @DisplayName("Deve criar uma nova wishlist e adicionar os produtos")
    void deveCriarNovaWishlistEAdicionarProdutos() {
        var bo = WishListFixture.criarBO().produtoIds(List.of(4L, 6L)).build();

        when(repository.buscarProduto(mongoOperations, bo.getProdutoIds(), bo.getClienteId())).thenReturn(false);
        when(repository.findByClienteId(123L)).thenReturn(Optional.empty());

        var wishlistCaptor = ArgumentCaptor.forClass(Wishlist.class);

        service.adicionarProdutos(bo);

        verify(mongoOperations).save(wishlistCaptor.capture());

        var novaWishlist = wishlistCaptor.getValue();
        assertEquals(bo.getClienteId(), novaWishlist.getClienteId());
        assertEquals(bo.getProdutoIds(), novaWishlist.getProdutoIds());
    }

    @Test
    @SneakyThrows
    @DisplayName("Deve adicionar produtos a uma wishlist existente")
    void deveAdicionarProdutosAWishlistExistente() {
        var wishlistExistente = WishListFixture.criarWishList().build();
        var bo = WishListFixture.criarBO().produtoIds(List.of(4L, 6L)).build();

        when(repository.buscarProduto(mongoOperations, bo.getProdutoIds(), bo.getClienteId())).thenReturn(false);
        when(repository.findByClienteId(123L)).thenReturn(Optional.of(wishlistExistente));

        var mapperBO = mapper.toModel(bo);
        service.adicionarProdutos(bo);

        assertNotNull(mapperBO.getProdutoIds());
        assertEquals(5, wishlistExistente.getProdutoIds().size());
        verify(mongoOperations).save(wishlistExistente);
    }

    @Test
    @SneakyThrows
    @DisplayName(value = "Deve retornar erro ao tentar adicionar um produto já existente na wishlist com mesmo clienteId")
    void deveRetornarErroSeJaExisteProdutoCadastrado() {
        var bo = WishListFixture.criarBO().build();

        when(repository.buscarProduto(mongoOperations, bo.getProdutoIds(), bo.getClienteId())).thenReturn(true);

        String expectedMessage = "Já existe um produto com mesmo id cadastrado na Wishlist com clienteId 123.";

        Exception exception = assertThrows(Exception.class, () ->
                service.adicionarProdutos(bo)
        );


        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @SneakyThrows
    @DisplayName(value = "Deve retornar erro ao tentar adicionar mais de 20 produtos em uma wishlist com mesmo clienteId")
    void deveRetornarErroSeProdutosExcederLimite() {
        var wishlist = WishListFixture.criarWishList().build();
        var bo = WishListFixture.criarBO().produtoIds(gerarIdsAleatorios()).build();

        String expectedMessage = "Já existem 20 produtos cadastrados na Wishlist com clienteId 123.";

        when(repository.buscarProduto(mongoOperations, bo.getProdutoIds(), bo.getClienteId())).thenReturn(false);
        when(repository.findByClienteId(123L)).thenReturn(Optional.of(wishlist));


        Exception exception = assertThrows(Exception.class, () ->
                service.adicionarProdutos(bo)
        );

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @SneakyThrows
    @DisplayName("Deve deletar produto existente por ID e clienteId com sucesso")
    void deveDeletarProdutoExistentePorIdComSucesso() {
        Long clienteId = 123L;
        Long produtoId = 456L;

        var projection = WishListFixture.criarProjection().build();

        when(repository.buscarProdutoPorId(mongoOperations, clienteId, produtoId))
                .thenReturn(projection);

        service.deletarPorId(clienteId, produtoId);

        verify(repository).deletarPorCodigo(mongoOperations, produtoId, clienteId);
    }

    @Test
    @SneakyThrows
    @DisplayName(value = "Deve retornar erro ao buscar um produto quando não houver produto cadastrado para o produtoId e clienteId informados.")
    void deveRetornarErroAoBuscarProdutoPorIdAoTentarDeletar() {
        var clienteId = 2L;
        var produtoId = 1567L;

        when(repository.buscarProdutoPorId(mongoOperations, clienteId, produtoId)).thenReturn(null);

        String expectedMessage = "Não existe um produto com id 1567 cadastrado na Wishlist para esse clienteId: 2.";

        Exception exception = assertThrows(Exception.class, () ->
                service.deletarPorId(clienteId, produtoId)
        );

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        verify(repository, never()).deletarPorCodigo(any(), anyLong(), anyLong());
    }

    List<Long> gerarIdsAleatorios() {
        var random = new Random();
        return LongStream.generate(() -> Math.abs(random.nextLong()))
                .limit(18)
                .boxed()
                .toList();
    }
}
