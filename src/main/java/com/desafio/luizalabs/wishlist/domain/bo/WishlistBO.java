package com.desafio.luizalabs.wishlist.domain.bo;

import lombok.Builder;
import lombok.Value;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Value
@Builder
public class WishlistBO {

    Long wishListId;
    Long clienteId;
    String nomeProduto;
    String codigoProduto;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}