package com.app.productservice.application.product.api;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.app.productservice.application.config.AuthorizationEnum;
import com.app.productservice.application.product.factory.ProductRequestResponseFactory;
import com.app.productservice.application.product.model.ProductRequest;
import com.app.productservice.application.product.model.PagingResponse;
import com.app.productservice.application.config.UriPathConsts;
import com.app.productservice.application.product.api.doc.ProductControllerDoc;
import com.app.productservice.application.product.model.ProductResponse;
import com.app.productservice.application.support.HeaderTools;
import com.app.productservice.domain.common.PagingQuery;
import com.app.productservice.domain.common.PagingResult;
import com.app.productservice.domain.product.Product;
import com.app.productservice.domain.product.ProductService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Validated
@RequiredArgsConstructor
public class ProductController implements ProductControllerDoc {

  private final ProductService productService;
  private final ProductRequestResponseFactory requestResponseFactory;

  @GetMapping(path = UriPathConsts.PUBLIC_PRODUCT_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<ProductResponse> getProduct(@PathVariable String productId) {

    var product = productService.getProduct(productId);
    if (product.isEmpty()) {
      throw new ResponseStatusException(NOT_FOUND, "Product does not found");
    }
    ProductResponse result = requestResponseFactory.toProductResponse(product.get());

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @GetMapping(path = UriPathConsts.PUBLIC_PRODUCT_GET_ALL, produces = MediaType.APPLICATION_JSON_VALUE)
  public PagingResponse<ProductResponse> getAllProducts(
      @RequestParam(name = "size", defaultValue = "25") int pageSize,
      @RequestParam(name = "page", defaultValue = "0") int pageNumber,
      @RequestParam(name = "query", required = false) String productQuery) {
    PagingResult<Product> page = productService.getProducts(PagingQuery.of(pageSize, pageNumber), productQuery);

    List<ProductResponse> products = page.getContent()
        .stream()
        .map(requestResponseFactory::toProductResponse)
        .collect(Collectors.toList());
    return PagingResponse.of(products, page.getTotalElements(), page.getTotalPages());
  }

  @PostMapping(path = UriPathConsts.PUBLIC_PRODUCT)
  public ResponseEntity<ProductResponse> createProduct(
      @RequestBody ProductRequest request,
      @RequestHeader Map<String, String> headers)
      throws ResponseStatusException {

    String sellerUserId = extractUserId(headers);

    if (productService.exists(request.getName(), sellerUserId)) {
      throw new ResponseStatusException(CONFLICT,
          String.format("A product with the name '%s' already exists.", request.getName()));
    }

    var currentProductStatusResponse = productService.createProduct(
            requestResponseFactory.toProduct(request, sellerUserId))
        .orElseThrow(
            () -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "Product can not be created"));

    ProductResponse result = requestResponseFactory.toProductResponse(currentProductStatusResponse);

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @PutMapping(path = UriPathConsts.PUBLIC_PRODUCT)
  public ResponseEntity<ProductResponse> updateProduct(
      @RequestBody ProductRequest request,
      @RequestHeader Map<String, String> headers)
      throws ResponseStatusException {

    String sellerUserId = extractUserId(headers);
    AuthorizationEnum userRole = extractUserRole(headers);

    if(userRole != AuthorizationEnum.SELLER_ROLE) {
      throw new ResponseStatusException(BAD_REQUEST, "User should have SELLER role to be able to Proceed");
    }

    Product currentProduct = productService.getProduct(request.getProductId(), sellerUserId)
        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
            String.format("There exists no product with the ID '%s'.", request.getProductId())));

    final var updatedProduct = requestResponseFactory.toProductUpdate(request, currentProduct, sellerUserId);

    var currentProductStatusResponse = productService
        .updateProduct(updatedProduct)
        .orElseThrow(
            () -> new ResponseStatusException(INTERNAL_SERVER_ERROR, "Product could not be updated."));

    ProductResponse result = requestResponseFactory.toProductResponse(currentProductStatusResponse);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @DeleteMapping(path = UriPathConsts.PUBLIC_PRODUCT_BY_ID)
  public ResponseEntity<Void> deleteProduct(
      @PathVariable String productId,
      @RequestHeader Map<String, String> headers)
      throws ResponseStatusException {

    String sellerUserId = extractUserId(headers);

    AuthorizationEnum userRole = extractUserRole(headers);

    if(userRole != AuthorizationEnum.SELLER_ROLE) {
      throw new ResponseStatusException(BAD_REQUEST, "User should have SELLER role to be able to Proceed");
    }

    Product currentProduct = productService.getProduct(productId, sellerUserId)
        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
            String.format("There exists no product with the ID '%s'.", productId)));

    if (currentProduct != null) {
      productService.deleteProduct(productId);
    }
    return ResponseEntity.ok().build();
  }

  //  We always take userId from tokenHeader
  private String extractUserId(Map<String, String> headers) {
    return HeaderTools.extractUserId(headers).orElseThrow(
        () -> new ResponseStatusException(NOT_FOUND, "User ID must not be null."));
  }

  private AuthorizationEnum extractUserRole(Map<String, String> headers) {
    return HeaderTools.getUserRole(HeaderTools.extractUserRole(headers).orElse(""));
  }

}
