package com.app.productservice.infrastructure.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.app.productservice.infrastructure.persistence.ProductDocument;
import com.app.productservice.infrastructure.support.ProductMock;
import com.app.productservice.domain.product.Product;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductFactoryTest {

    private ProductFactory productFactory;

    @BeforeEach
    void setUp() {
        this.productFactory = new ProductFactoryImpl();
    }

    @DisplayName("should map ProductDTO to Product")
    @Test
    void shouldMapProductDTOToProduct() {

        Product product = ProductMock.getInstance().getProduct();
        ProductDocument productDTOType = ProductMock.getInstance().getProductDTO();

        Product actual = this.productFactory.toProduct(productDTOType);

        assertNotNull(actual);
        assertEquals(product, actual);
    }

    @DisplayName("should map ProductDTO List to Product List")
    @Test
    void shouldMapProductDTOListToProductList() {

        Product product = ProductMock.getInstance().getProduct();
        ProductDocument productDTOType = ProductMock.getInstance().getProductDTO();

        List<Product> actual = this.productFactory.toProductList(List.of(productDTOType));

        assertNotNull(actual);
        assertEquals(product, actual.get(0));
    }

    @DisplayName("should map Product to ProductDTO")
    @Test
    void shouldMapProductToProductDTO() {

        Product product = ProductMock.getInstance().getProduct();
        ProductDocument ProductDTOType = ProductMock.getInstance().getProductDTO();

        ProductDocument actual = this.productFactory.toProductDTO(product);

        assertNotNull(actual);
        assertEquals(ProductDTOType, actual);
    }

    @DisplayName("should map Product List to ProductDTO List")
    @Test
    void shouldMapProductListToProductDTOList() {

        Product product = ProductMock.getInstance().getProduct();
        ProductDocument ProductDTOType = ProductMock.getInstance().getProductDTO();

        List<ProductDocument> actual = this.productFactory.toProductDTOList(List.of(product));

        assertNotNull(actual);
        assertEquals(ProductDTOType, actual.get(0));
    }

    @DisplayName("should fail if ProductDTO is null")
    @Test
    void shouldFailIfProductDTONull() {

        assertThrows(IllegalArgumentException.class, () -> this.productFactory.toProduct(null));
    }

    @DisplayName("should fail if Product is null")
    @Test
    void shouldFailIfProductNull() {

        assertThrows(NullPointerException.class, () -> this.productFactory.toProductDTO(null));
    }

}
