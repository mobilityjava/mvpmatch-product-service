package com.app.productservice.infrastructure.persistence;

import java.time.LocalDateTime;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * Products entry saved in Mongo.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "products")
public class ProductDocument {

    @Id
    private String productId;

    private String name;

    @Indexed
    private String sellerUserId;

    private String cost;

    @CreatedDate
    @Setter(AccessLevel.PRIVATE)
    private LocalDateTime created;

    @LastModifiedDate
    @Setter(AccessLevel.PRIVATE)
    private LocalDateTime updated;

}
