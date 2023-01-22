package com.app.productservice.infrastructure.persistence;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository for the Products
 */
@Repository
public interface ProductRepository extends MongoRepository<ProductDocument, String> {


  Optional<ProductDocument> findByProductId(String productId);
  Optional<ProductDocument> findByProductIdAndSellerUserId(String productId, String sellerUserId);

  Page<ProductDocument> findByProductIdContaining(String productId, PageRequest pagingQuery);

  boolean existsByNameAndSellerUserId(String productName, String sellerUserId);

}
