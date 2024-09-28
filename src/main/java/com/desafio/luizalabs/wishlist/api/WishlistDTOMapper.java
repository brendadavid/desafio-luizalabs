package com.desafio.luizalabs.wishlist.api;

import com.desafio.luizalabs.wishlist.api.dto.WishlistDTO;
import com.desafio.luizalabs.wishlist.domain.bo.WishlistBO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WishlistDTOMapper {

   WishlistBO toBO(WishlistDTO dto);
}
