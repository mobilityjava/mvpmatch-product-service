package com.app.productservice.infrastructure.boundary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.app.productservice.domain.product.Product;
import com.app.productservice.infrastructure.factory.ProductFactory;
import com.app.productservice.infrastructure.persistence.ProductDocument;
import com.app.productservice.infrastructure.persistence.ProductRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

  @Mock
  private ProductRepository productRepository;

  @Mock
  private ProductFactory productFactory;

  private ProductServiceImpl productService;

  @BeforeEach
  void setup() {
    productService = new ProductServiceImpl(productRepository, productFactory);
  }


  @Test
  void getProductByProductIdShouldReturnProduct() {

    String productId = "productId";

    Optional<ProductDocument> productDTO = productRepository.findByProductId(productId);
    Optional<Product> expected = productDTO.map(productFactory::toProduct);

    Optional<Product> actual = productService.getProduct(productId);

    assertEquals(actual, expected);

  }

}
