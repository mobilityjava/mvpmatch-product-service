package com.app.productservice.infrastructure.boundary;

import com.app.productservice.infrastructure.persistence.ProductRepository;
import com.app.productservice.domain.common.PagingQuery;
import com.app.productservice.domain.common.PagingResult;
import com.app.productservice.domain.product.Product;
import com.app.productservice.domain.product.ProductService;
import com.app.productservice.infrastructure.factory.ProductFactory;
import com.app.productservice.infrastructure.persistence.ProductDocument;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final ProductFactory productFactory;

  @Override
  public Optional<Product> getProduct(String productId) {

    Optional<ProductDocument> byProductId = productRepository.findByProductId(productId);
    return byProductId.map(productFactory::toProduct);
  }

  @Override
  public Optional<Product> getProduct(String productId, String sellerUserId) {

    Optional<ProductDocument> byProductId = productRepository.findByProductIdAndSellerUserId(productId, sellerUserId);
    return byProductId.map(productFactory::toProduct);
  }

  @Override
  public Optional<Product> createProduct(@NonNull Product product) {
    var savedProduct = productRepository.save(productFactory.toProductDTO(product));
    return Optional.ofNullable(productFactory.toProduct(savedProduct));
  }

  @Override
  public Optional<Product> updateProduct(@NonNull Product product) {
    final ProductDocument updatedProductDTO = productFactory.toProductDTO(product);
    var savedProduct = productRepository.save(updatedProductDTO);
    return Optional.ofNullable(productFactory.toProduct(savedProduct));
  }

  @Override
  public void deleteProduct(@NonNull String productId) {
    productRepository.deleteById(productId);
  }

  @Override
  public PagingResult<Product> getProducts(PagingQuery pagingQuery, String productQuery) {
    PageRequest pageRequest = PageRequest.of(pagingQuery.getNumber(), pagingQuery.getSize());

    final var page = (productQuery == null || productQuery.isBlank()) ?
        productRepository.findAll(pageRequest) :
        productRepository.findByProductIdContaining(productQuery, pageRequest);
    var products = page.getContent()
        .stream()
        .map(productFactory::toProduct)
        .collect(Collectors.toList());
    return PagingResult.of(products, page.getTotalElements(), page.getTotalPages());
  }

  @Override
  public boolean exists(String productName, String sellerUserId) {
    return productRepository.existsByNameAndSellerUserId(productName, sellerUserId);
  }

}
