package com.app.productservice.domain.product;

import com.app.productservice.domain.common.PagingQuery;
import com.app.productservice.domain.common.PagingResult;
import java.util.Optional;
import lombok.NonNull;

/**
 * All domain actions regarding an {@link Product}.
 */
public interface ProductService {

  /**
   * Get Product
   *
   * @param productId
   * @return current product.
   */
  Optional<Product> getProduct(String productId);

  /**
   * Get Product
   *
   * @param productId
   * @return current product.
   */
  Optional<Product> getProduct(String productId, String sellerUserId);

  /**
   * Create a Product
   *
   * @param product object. Cannot be null.
   * @return Return created Product value.
   */
  Optional<Product> createProduct(@NonNull Product product);

  /**
   * Update a product
   *
   * @param product object. Cannot be null.
   * @return Return updated Product value.
   */
  Optional<Product> updateProduct(@NonNull Product product);

  /**
   * Deletes a product's account.
   * @param productId Cannot be {@code null}.
   */
  void deleteProduct(String productId);

  /**
   * Get all available products sorted by e-mail.
   *
   * @param pagingQuery paging details
   * @return requested page of products
   */
  PagingResult<Product> getProducts(PagingQuery pagingQuery, String productQuery);

  /**
   * Check if product with id already exists
   * @param productName
   * @param sellerUserId
   * @return true if product with id already exists
   */
  boolean exists(String productName, String sellerUserId);
}
