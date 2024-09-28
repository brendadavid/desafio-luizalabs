package com.desafio.luizalabs.wishlist.api;

import com.desafio.luizalabs.wishlist.api.dto.WishlistDTO;
import com.desafio.luizalabs.wishlist.domain.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishList")
@RequiredArgsConstructor
@Tag(name = "WishList")
public class WishListController {

    private final WishlistService service;
    private final WishlistDTOMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Operação para adicionar produtos na Wishlist")
    public void adicionarProdutos(@Valid WishlistDTO dto) {
         service.adicionarProdutos(this.mapper.toBO(dto));
    }
}
