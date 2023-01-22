package com.app.productservice.infrastructure.factory;

import com.app.productservice.infrastructure.persistence.ProductDocument;
import com.app.productservice.domain.product.Product;
import java.util.List;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductFactory {

    /**
     * Creates a Product from an ProductDTO.
     *
     * @param productDocument of {@link ProductDocument} type.
     * @return product.
     */
    Product toProduct(@NonNull ProductDocument productDocument);

    /**
     * Creates a Products List from ProductDTOs List.
     *
     * @param productDocuments List of {@link ProductDocument} type.
     * @return Products List.
     */
    List<Product> toProductList(List<ProductDocument> productDocuments);

    /**
     * Creates a ProductDTO from a Product.
     *
     * @param product of {@link Product} type.
     * @return ProductDTO.
     */
    ProductDocument toProductDTO(Product product);

    /**
     * Creates a ProductDTOs List from Products List.
     *
     * @param products List of {@link Product} type.
     * @return ProductDTOs List.
     */
    List<ProductDocument> toProductDTOList(List<Product> products);

}
