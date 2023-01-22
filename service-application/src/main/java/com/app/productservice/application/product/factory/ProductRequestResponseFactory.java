package com.app.productservice.application.product.factory;

import com.app.productservice.application.product.model.ProductRequest;
import com.app.productservice.application.product.model.ProductResponse;
import com.app.productservice.domain.product.Product;

public interface ProductRequestResponseFactory {

  /**
   * Creates a Product from an ProductRequest.
   *
   * @param productRequest of {@link ProductRequest} type.
   * @return Product.
   */
  Product toProductUpdate(ProductRequest productRequest, Product currentProduct, String sellerUserId);

  /**
   * Creates a Product from an ProductRequest.
   *
   * @param productRequest of {@link ProductRequest} type.
   * @return Product.
   */
  Product toProduct(ProductRequest productRequest, String sellerUserId);

  /**
   * Creates a ProductResponse from Product.
   *
   * @param product of {@link Product} type.
   * @return Product.
   */
  ProductResponse toProductResponse(Product product);

}
