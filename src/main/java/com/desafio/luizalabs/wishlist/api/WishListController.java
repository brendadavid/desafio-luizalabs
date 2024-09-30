package com.desafio.luizalabs.wishlist.api;

import com.desafio.luizalabs.wishlist.api.dto.BuscarProdutoDTO;
import com.desafio.luizalabs.wishlist.api.dto.BuscarWishlistDTO;
import com.desafio.luizalabs.wishlist.api.dto.WishlistDTO;
import com.desafio.luizalabs.wishlist.domain.WishlistListagemService;
import com.desafio.luizalabs.wishlist.domain.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishList")
@RequiredArgsConstructor
@Tag(name = "WishList")
public class WishListController {

    private final WishlistService service;
    private final WishlistListagemService listagemService;
    private final WishlistDTOMapper mapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Operação para adicionar produtos na Wishlist do cliente.")
    public void adicionarProdutos(@Valid @RequestBody WishlistDTO dto) {
         service.adicionarProdutos(this.mapper.toBO(dto));
    }

    @GetMapping("produtos/{clienteId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Operação para buscar todos os produtos adicionados na Wishlist por clienteId.")
    public BuscarWishlistDTO buscarProdutosPorClienteId(
            @PathVariable("clienteId") Long clienteId
    ) {
         return this.mapper.toDTO(listagemService.buscarProdutos(clienteId));
    }

    @GetMapping("produto/{clienteId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Operação para buscar um produto na Wishlist do cliente por id do produto.")
    public BuscarProdutoDTO buscarProdutoPorId(
            @PathVariable("clienteId") Long clienteId,
            @Parameter(name = "produtoId", description = "Id do produto cadastrado.")
            @RequestParam(value = "produtoId") Long produtoId
    ) {
        return this.mapper.toDTO(listagemService.buscarProdutoPorId(clienteId, produtoId));
    }

    @DeleteMapping("/{clienteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Operação para deletar um produto da Wishlist do cliente por id do produto.")
    public void deletarProdutoPorCodigo(
            @PathVariable("clienteId") Long clienteId,
            @Parameter(name = "produtoId", description = "Id do produto cadastrado.")
            @RequestParam(value = "produtoId") Long produtoId
    ) {
        service.deletarPorCodigo(clienteId, produtoId);
    }
}
