package com.desafio.luizalabs.wishlist.domain;

import com.desafio.luizalabs.wishlist.domain.bo.ProdutoBO;
import com.desafio.luizalabs.wishlist.domain.bo.WishlistBO;
import com.desafio.luizalabs.wishlist.domain.model.Wishlist;
import com.desafio.luizalabs.wishlist.infrastructure.ProdutoProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = {UUID.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WishlistMapper {

    @Mapping(target = "wishListId", expression = "java(UUID.randomUUID().toString())")
    Wishlist toModel(WishlistBO bo);

    WishlistBO toBO(Wishlist wishlist);

    @Mapping(target = "produtoId", expression = "java(projection.getProdutoIds().get(0))")
    ProdutoBO toBO(ProdutoProjection projection);
}