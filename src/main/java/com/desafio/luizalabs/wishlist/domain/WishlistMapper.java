package com.desafio.luizalabs.wishlist.domain;

import com.desafio.luizalabs.wishlist.domain.bo.WishlistBO;
import com.desafio.luizalabs.wishlist.domain.model.Wishlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class, UUID.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WishlistMapper {

    @Mapping(target = "id", expression = "java(UUID.randomUUID().toString())")
    Wishlist toModel(WishlistBO bo);
}
