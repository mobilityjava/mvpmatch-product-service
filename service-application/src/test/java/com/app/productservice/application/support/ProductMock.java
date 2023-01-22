package com.app.productservice.application.support;

import com.app.productservice.application.product.model.ProductRequest;
import com.app.productservice.application.product.model.ProductResponse;
import com.app.productservice.domain.product.Product;
import com.app.productservice.infrastructure.persistence.ProductDocument;
import java.util.List;
import lombok.Getter;

public final class ProductMock {

    @Getter
    private final ProductDocument productDocument1;
    @Getter
    private final ProductDocument productDocument2;

    @Getter
    private final List<ProductDocument> products;

    @Getter
    private final List<ProductDocument> product;

    @Getter
    private final Product product1;

    @Getter
    private final Product product2;

    @Getter
    private final ProductResponse productResponse;

    @Getter
    private final ProductRequest productRequest;

    @Getter
    private final String productId1 = "productId_1";
    @Getter
    private final String productId2 = "productId_2";
    @Getter
    private final String sellerUserId1 = "sellerUserId_1";
    @Getter
    private final String name = "productName";
    @Getter
    private final int cost = 50;


    public ProductMock() {

        this.product1 = Product.builder()
            .productId(productId1)
            .name(name)
            .sellerUserId(sellerUserId1)
            .cost(cost)
            .build();

        this.product2 = Product.builder()
            .productId(productId2)
            .name(name)
            .sellerUserId(sellerUserId1)
            .cost(cost)
            .build();

        this.productResponse = ProductResponse.builder()
            .productId(productId1)
            .name(name)
            .sellerUserId(sellerUserId1)
            .cost(String.valueOf(cost))
            .build();

        this.productRequest = ProductRequest.builder()
            .productId(productId1)
            .name(name)
            .cost(String.valueOf(cost))
            .build();

        productDocument1 = ProductDocument.builder()
            .productId(productId1)
            .name(name)
            .sellerUserId(sellerUserId1)
            .cost(String.valueOf(cost))
            .build();

        productDocument2 = ProductDocument.builder()
            .productId(productId2)
            .name(name)
            .sellerUserId(sellerUserId1)
            .cost(String.valueOf(cost))
            .build();

        products = List.of(productDocument1, productDocument2);
        product = List.of(productDocument1);
    }

    /**
     * Returns the instance.
     *
     * @return Instance on {@link ProductMock}
     */
    public static ProductMock getInstance() {

        return InstanceHolder.instance;
    }

    private static final class InstanceHolder {

        private static final ProductMock instance =
                new ProductMock();
    }
}
