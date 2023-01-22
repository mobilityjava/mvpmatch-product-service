package com.app.productservice.domain.product;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Represents Product
 */
@Value
@Builder
public class Product {

  @NonNull
  String productId;
  @NonNull
  String name;
  @NonNull
  String sellerUserId;
  @NonNull
  Integer cost;

}
