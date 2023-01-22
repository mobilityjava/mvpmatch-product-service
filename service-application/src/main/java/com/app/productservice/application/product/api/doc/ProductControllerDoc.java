package com.app.productservice.application.product.api.doc;

import com.app.productservice.application.product.model.ProductRequest;
import com.app.productservice.application.product.model.ProductResponse;
import com.app.productservice.application.product.model.PagingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.server.ResponseStatusException;

@Tag(name = "Product", description = "Product self management")
public interface ProductControllerDoc {

  @Operation(summary = "Get product.", security = {
      @SecurityRequirement(name = "bearer-key")})
  @ApiResponse(responseCode = "200", description = "Successful operation.")
  @ApiResponse(responseCode = "401", description = "User is not authorized.", content = @Content(schema = @Schema(hidden = true)))
  @ApiResponse(responseCode = "404", description = "Product could not be found.", content = @Content(schema = @Schema(hidden = true)))
  @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(schema = @Schema(hidden = true)))
  ResponseEntity<ProductResponse> getProduct(String productId);

  @Operation(summary = "Get all Products", security = {
      @SecurityRequirement(name = "bearer-key")})
  @ApiResponse(responseCode = "200", description = "list of all product with pagination")
  @ApiResponse(responseCode = "401", description = "User not authorized", content = @Content(schema = @Schema(hidden = true)))
  @ApiResponse(responseCode = "500", description = "internal error", content = @Content(schema = @Schema(hidden = true)))
  PagingResponse<ProductResponse> getAllProducts(
      @Parameter(description = "Maximum items to display on page.") int size,
      @Parameter(description = "Number of page to return (starting with 0).") int page,
      @Parameter(description = "Query string for searching product") String query);


  @Operation(summary = "Create product.", security = {@SecurityRequirement(name = "bearer-key")})
  @ApiResponse(responseCode = "200", description = "Successful operation.")
  @ApiResponse(responseCode = "400", description = "Bad request, mismatched data.", content = @Content(schema = @Schema(hidden = true)))
  @ApiResponse(responseCode = "401", description = "User is not authorized.", content = @Content(schema = @Schema(hidden = true)))
  @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(schema = @Schema(hidden = true)))
  ResponseEntity<ProductResponse> createProduct(ProductRequest request, Map<String, String> headers)
      throws ResponseStatusException;

  @Operation(summary = "Update product.", security = {@SecurityRequirement(name = "bearer-key")})
  @ApiResponse(responseCode = "200", description = "Successful operation.")
  @ApiResponse(responseCode = "400", description = "Bad request, mismatched data.", content = @Content(schema = @Schema(hidden = true)))
  @ApiResponse(responseCode = "401", description = "User is not authorized.", content = @Content(schema = @Schema(hidden = true)))
  @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(schema = @Schema(hidden = true)))
  ResponseEntity<ProductResponse> updateProduct(ProductRequest request, Map<String, String> headers)
      throws ResponseStatusException;

  @Operation(summary = "Delete product.", security = {@SecurityRequirement(name = "bearer-key")})
  @ApiResponse(responseCode = "200", description = "Successful operation.")
  @ApiResponse(responseCode = "401", description = "User is not authorized.", content = @Content(schema = @Schema(hidden = true)))
  @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(schema = @Schema(hidden = true)))
  ResponseEntity<Void> deleteProduct(String productId, Map<String, String> headers) throws ResponseStatusException;

}
