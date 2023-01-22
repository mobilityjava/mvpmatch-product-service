package com.app.productservice.infrastructure.factory;

import com.app.productservice.infrastructure.persistence.ProductDocument;
import com.app.productservice.domain.product.Product;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ProductFactoryImpl implements ProductFactory {

    @Override
    public Product toProduct(@NonNull ProductDocument productDocument) {

        Product.ProductBuilder product = Product.builder();

        product.productId(productDocument.getProductId());
        product.name(productDocument.getName());
        product.sellerUserId(productDocument.getSellerUserId());
        product.cost(Integer.valueOf(productDocument.getCost()));

        return product.build();
    }

    @Override
    public List<Product> toProductList(List<ProductDocument> productDocuments) {
        return productDocuments.stream()
                .map(this::toProduct)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDocument toProductDTO(Product product) {

        ProductDocument.ProductDocumentBuilder productDTO = ProductDocument.builder();

        productDTO.productId(product.getProductId());
        productDTO.name(product.getName());
        productDTO.sellerUserId(product.getSellerUserId());
        productDTO.cost(String.valueOf(product.getCost()));

        return productDTO.build();
    }

    @Override
    public List<ProductDocument> toProductDTOList(List<Product> products) {
        return products.stream()
            .map(this::toProductDTO)
            .collect(Collectors.toList());
    }

}
