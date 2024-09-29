package com.desafio.luizalabs.wishlist.api;

import com.desafio.luizalabs.wishlist.api.dto.BuscarWishlistDTO;
import com.desafio.luizalabs.wishlist.api.dto.WishlistDTO;
import com.desafio.luizalabs.wishlist.domain.bo.WishlistBO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WishlistDTOMapper {

   WishlistBO toBO(WishlistDTO dto);

   BuscarWishlistDTO toDTO(WishlistBO bo);
}
