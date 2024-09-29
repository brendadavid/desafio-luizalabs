package com.desafio.luizalabs.wishlist.domain.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@Document(collection = "wishlist")
public class Wishlist {

    @Id
    private String wishListId;
    private Long clienteId;
    private String nomeProduto;
    private String codigoProduto;
}
