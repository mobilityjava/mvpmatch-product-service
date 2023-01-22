package com.app.productservice.application.product.factory;

import com.app.productservice.application.product.model.ProductRequest;
import com.app.productservice.application.product.model.ProductResponse;
import com.app.productservice.application.support.ProductMock;
import com.app.productservice.domain.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProductRequestResponseFactoryTest {

  private ProductRequestResponseFactory productRequestResponseFactory;

  @BeforeEach
  void setUp() {
    this.productRequestResponseFactory = new ProductRequestResponseFactoryImpl();
  }


  @DisplayName("should map ProductRequest to Product")
  @Test
  void shouldMapProductRequestToProduct() {

    Product product = ProductMock.getInstance().getProduct1();
    ProductRequest productRequest = ProductMock.getInstance().getProductRequest();
    String sellerUserId1 = ProductMock.getInstance().getSellerUserId1();

    Product actual = this.productRequestResponseFactory.toProduct(productRequest, sellerUserId1);

    assertNotNull(actual);
    assertEquals(product, actual);
  }

  @DisplayName("should map ProductRequest to Product for update operation")
  @Test
  void shouldMapProductRequestToProductForUpdateOperation() {
    Product product = ProductMock.getInstance().getProduct1();
    ProductRequest productRequest = ProductMock.getInstance().getProductRequest();
    String sellerUserId1 = ProductMock.getInstance().getSellerUserId1();

    Product actual = this.productRequestResponseFactory.toProductUpdate(productRequest, product, sellerUserId1);

    assertNotNull(actual);
    assertEquals(product, actual);

  }

  @DisplayName("should map Product to ProductResponse")
  @Test
  void shouldMapProductToProductResponse() {

    Product productResponseType = ProductMock.getInstance().getProduct1();
    ProductResponse productStatusResponseType = ProductMock.getInstance().getProductResponse();

    ProductResponse actual = this.productRequestResponseFactory.toProductResponse(productResponseType);

    assertNotNull(actual);
    assertEquals(productStatusResponseType, actual);
  }

}
