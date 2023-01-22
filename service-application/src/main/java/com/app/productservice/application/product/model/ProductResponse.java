package com.app.productservice.application.product.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;


/**
 * UI model for a Product's profile response.
 */
@Schema(name = "ProductResponse", description = "Represents a Product's profile on a response.", accessMode = AccessMode.READ_ONLY)
@Value
@Builder
public class ProductResponse {

  @Schema(description = "Product ID", example = "asewe234234Kw34sv")
  @NonNull
  String productId;

  @Schema(description = "Product's seller userId", example = "userId")
  String sellerUserId;

  @Schema(description = "Product Name", example = "productName")
  String name;

  @Schema(description = "Product's cost", example = "25")
  String cost;

}
