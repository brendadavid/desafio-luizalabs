package com.desafio.luizalabs.wishlist.api;

import com.desafio.luizalabs.wishlist.api.dto.BuscarWishlistDTO;
import com.desafio.luizalabs.wishlist.api.dto.WishlistDTO;
import com.desafio.luizalabs.wishlist.domain.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishList")
@RequiredArgsConstructor
@Tag(name = "WishList")
public class WishListController {

    private final WishlistService service;
    private final WishlistDTOMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Operação para adicionar produtos na Wishlist do cliente.")
    public void adicionarProdutos(@Valid WishlistDTO dto) {
         service.adicionarProdutos(this.mapper.toBO(dto));
    }

    @GetMapping("produtos/{clienteId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Operação para buscar todos os produtos adicionados na Wishlist por clienteId.")
    public List<BuscarWishlistDTO> buscarProdutosPorClienteId(
            @PathVariable("clienteId") Long clienteId
    ) {
         return service.buscarProdutos(clienteId)
                 .stream()
                 .map(this.mapper::toDTO)
                 .toList();
    }

    @GetMapping("produto/{clienteId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Operação para buscar um produto na Wishlist do cliente por código do produto.")
    public BuscarWishlistDTO buscarProdutosPorNomeOuCodigo(
            @PathVariable("clienteId") Long clienteId,
            @Parameter(name = "codigoProduto", description = "Código do produto cadastrado.")
            @RequestParam(value = "codigoProduto") String codigoProduto
    ) {
        return this.mapper.toDTO(service.buscarProdutoPorCodigo(clienteId, codigoProduto));
    }

    @DeleteMapping("/{clienteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Operação para deletar um registro da Wishlist do cliente por codigo do produto.")
    public void deletarProdutoPorCodigo(
            @PathVariable("clienteId") Long clienteId,
            @Parameter(name = "codigoProduto", description = "Código do produto cadastrado.")
            @RequestParam(value = "codigoProduto") String codigoProduto
    ) {
        service.deletarPorCodigo(clienteId, codigoProduto);
    }
}
