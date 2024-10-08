package com.desafio.luizalabs.wishlist.domain.bo;

import lombok.Builder;
import lombok.Value;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@Value
@Builder
public class WishlistBO {

    String wishListId;
    Long clienteId;
    List<Long> produtoIds;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}