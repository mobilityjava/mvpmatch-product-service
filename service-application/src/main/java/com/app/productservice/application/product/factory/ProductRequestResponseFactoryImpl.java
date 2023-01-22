package com.app.productservice.application.product.factory;

import com.app.productservice.application.product.model.ProductRequest;
import com.app.productservice.application.product.model.ProductResponse;
import com.app.productservice.domain.product.Product;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class ProductRequestResponseFactoryImpl implements ProductRequestResponseFactory {


  @Override
  public Product toProductUpdate(ProductRequest productRequest, Product currentProduct, String sellerUserId) {

    Product.ProductBuilder product = Product.builder();

    product.productId(currentProduct.getProductId());
    product.name(
        (Objects.isNull(productRequest.getName()) ? currentProduct.getName() : productRequest.getName()));
    product.sellerUserId(sellerUserId);
    product.cost((Objects.isNull(productRequest.getCost())) ? currentProduct.getCost()
        : Integer.valueOf(productRequest.getCost()));

    return product.build();
  }

  @Override
  public Product toProduct(ProductRequest productRequest, String sellerUserId) {

    Product.ProductBuilder product = Product.builder();

    product.productId(productRequest.getProductId());
    product.name(productRequest.getName());
    product.sellerUserId(sellerUserId);
    product.cost(Integer.valueOf(productRequest.getCost()));

    return product.build();
  }

  @Override
  public ProductResponse toProductResponse(Product product) {

    ProductResponse.ProductResponseBuilder productResponse = ProductResponse.builder();

    productResponse.productId(product.getProductId());
    productResponse.name(product.getName());
    productResponse.sellerUserId(product.getSellerUserId());
    productResponse.cost(String.valueOf(product.getCost()));

    return productResponse.build();
  }

}
