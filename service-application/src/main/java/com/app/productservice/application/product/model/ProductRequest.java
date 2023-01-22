package com.app.productservice.application.product.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import lombok.With;

/**
 * UI model for a product's profile request.
 */
@Schema(name = "ProductRequest", description = "Represents a product's profile on a request.")
@Value
@Builder
@With
public class ProductRequest {

    @Schema(description = "Product ID", example = "asewe234234Kw34sv")
    String productId;

    @Schema(description = "Product Name", example = "productName")
    String name;

    @Schema(description = "Product's cost", example = "25")
    String cost;

}
