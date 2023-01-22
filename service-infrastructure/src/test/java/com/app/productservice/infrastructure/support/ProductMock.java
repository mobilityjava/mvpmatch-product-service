package com.app.productservice.infrastructure.support;

import com.app.productservice.domain.product.Product;
import com.app.productservice.infrastructure.persistence.ProductDocument;
import lombok.Getter;

public final class ProductMock {

    @Getter
    private final Product product;

    public ProductMock() {

        this.product = Product.builder()
            .productId("productId123")
            .name("productName")
            .sellerUserId("userId")
            .cost(50)
            .build();

    }

    /**
     * @return Since productDocument is mutable, we need to return a fresh instance each time it's used
     */
    public ProductDocument getProductDTO() {
        return ProductDocument.builder()
            .productId("productId123")
            .name("productName")
            .sellerUserId("userId")
            .cost("50")
            .build();
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
