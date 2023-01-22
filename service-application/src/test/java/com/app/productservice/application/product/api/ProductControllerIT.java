package com.app.productservice.application.product.api;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.app.productservice.application.ApplicationIT;
import com.app.productservice.application.config.AuthorizationEnum;
import com.app.productservice.application.config.ExceptionAdvice;
import com.app.productservice.application.config.HeaderConsts;
import com.app.productservice.application.config.UriPathConsts;
import com.app.productservice.application.product.model.ProductRequest;
import com.app.productservice.application.support.ProductMock;
import com.app.productservice.application.support.Matchers;
import com.app.productservice.application.utils.RestRequestUtils;
import com.app.productservice.infrastructure.persistence.ProductDocument;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class ProductControllerIT extends ApplicationIT {

  private static final String PRODUCT_ID = ProductMock.getInstance().getProductId1();
  private static final String SELLER_USER_ID = ProductMock.getInstance().getSellerUserId1();

  @Autowired
  ProductController productController;

  @DisplayName("Get Product should succeed with 200 code")
  @Test
  void getProductShouldSucceedWith200() throws Exception {
    ProductDocument productDocument = ProductMock.getInstance().getProductDocument1();
    when(productRepository.findByProductId(PRODUCT_ID))
        .thenReturn(Optional.ofNullable(productDocument));

    mockMvc
        .perform(MockMvcRequestBuilders.get(UriPathConsts.PUBLIC_PRODUCT_BY_ID, PRODUCT_ID)
            .header(HeaderConsts.HEADER_USER_ID, SELLER_USER_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.productId", is(productDocument.getProductId())))
        .andExpect(jsonPath("$.name", is(productDocument.getName())))
        .andExpect(jsonPath("$.sellerUserId", is(productDocument.getSellerUserId())))
        .andExpect(jsonPath("$.cost", is(productDocument.getCost())))
    ;

  }

  @DisplayName("Get Product should return 404 if Product has no profile and not found.")
  @Test
  void getProductShouldFailWith404IfProductNotFound() throws Exception {
    when(productRepository.findByProductId(PRODUCT_ID)).thenReturn(Optional.empty());

    mockMvc
        .perform(get(UriPathConsts.PUBLIC_PRODUCT_BY_ID, PRODUCT_ID)
            .header(HeaderConsts.HEADER_USER_ID, SELLER_USER_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNotFound())
    ;
  }

  @Test
  void getProductShouldFailWith500OnException() throws Exception {
    doThrow(new RuntimeException("some error")).when(productRepository)
        .findByProductId(anyString());

    mockMvc
        .perform(get(UriPathConsts.PUBLIC_PRODUCT_BY_ID, PRODUCT_ID))
        .andExpect(status().isInternalServerError())
        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
        .andExpect(content().string(ExceptionAdvice.INTERNAL_SERVER_ERROR_MSG));
  }

  @DisplayName("Create Product should succeed with 200 code")
  @Test
  void createProductShouldSucceedWith200() throws Exception {
    ProductDocument productDocument = ProductMock.getInstance().getProductDocument1();
    ProductRequest productRequest = ProductMock.getInstance().getProductRequest();

    when(productRepository.save(any(ProductDocument.class))).thenReturn(productDocument);

    mockMvc
        .perform(post(UriPathConsts.PUBLIC_PRODUCT)
            .header(HeaderConsts.HEADER_USER_ID, SELLER_USER_ID)
            .content(RestRequestUtils.asJsonString(productRequest))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productId", is(productDocument.getProductId())))
        .andExpect(jsonPath("$.name", is(productDocument.getName())))
        .andExpect(jsonPath("$.sellerUserId", is(productDocument.getSellerUserId())))
        .andExpect(jsonPath("$.cost", is(productDocument.getCost())))
    ;
  }

  @DisplayName("Create Product should fail with 409 if already exists")
  @Test
  void createProductShouldFailWith409IfIdAlreadyExists() throws Exception {
    ProductRequest productRequest = ProductMock.getInstance().getProductRequest();

    when(productRepository.existsByNameAndSellerUserId(anyString(), anyString())).thenReturn(true);

    mockMvc
        .perform(post(UriPathConsts.PUBLIC_PRODUCT)
            .header(HeaderConsts.HEADER_USER_ID, SELLER_USER_ID)
            .content(RestRequestUtils.asJsonString(productRequest))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isConflict());
  }

  @DisplayName("Create Product should fail with 400 code if ProductRequest is NUll")
  @Test
  void createProductShouldFailWith400IfProductRequestIsNull() throws Exception {

    mockMvc
        .perform(post(UriPathConsts.PUBLIC_PRODUCT)
            .header(HeaderConsts.HEADER_USER_ID, SELLER_USER_ID)
            .content(RestRequestUtils.asJsonString(null))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().is4xxClientError());
  }

  @DisplayName("Update Product should succeed with 200 code")
  @Test
  void updateProductShouldSucceedWith200() throws Exception {

    ProductRequest productRequest = ProductMock.getInstance().getProductRequest();
    ProductDocument productDocument = ProductMock.getInstance().getProductDocument2();

    when(productRepository.findByProductIdAndSellerUserId(anyString(), anyString())).thenReturn(
        Optional.ofNullable(productDocument));
    when(productRepository.save(any(ProductDocument.class))).thenReturn(productDocument);

    mockMvc
        .perform(put(UriPathConsts.PUBLIC_PRODUCT)
            .header(HeaderConsts.HEADER_USER_ID, SELLER_USER_ID)
            .header(HeaderConsts.HEADER_USER_ROLE, AuthorizationEnum.SELLER_ROLE.getAuthRole())
            .content(RestRequestUtils.asJsonString(productRequest))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productId", is(productDocument.getProductId())))
        .andExpect(jsonPath("$.name", is(productDocument.getName())))
        .andExpect(jsonPath("$.sellerUserId", is(productDocument.getSellerUserId())))
        .andExpect(jsonPath("$.cost", is(productDocument.getCost())))
    ;
  }

  @DisplayName("Update Product should fail with 404 code, No product with id 'productId' exists")
  @Test
  void updateProductShouldFailWith404() throws Exception {
    ProductRequest productRequest = ProductMock.getInstance().getProductRequest();

    when(productRepository.findByProductId(anyString()))
        .thenReturn(Optional.empty());

    mockMvc
        .perform(put(UriPathConsts.PUBLIC_PRODUCT)
            .header(HeaderConsts.HEADER_USER_ID, SELLER_USER_ID)
            .content(RestRequestUtils.asJsonString(productRequest))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().is4xxClientError())
        .andExpect(Matchers.contentIsEmpty())
    ;
  }

  @DisplayName("Delete Product should succeed with 200 code")
  @Test
  void deleteProductShouldSucceedWith200() throws Exception {
    ProductDocument productDocument = ProductMock.getInstance().getProductDocument1();
    when(productRepository.findByProductIdAndSellerUserId(anyString(), anyString()))
        .thenReturn(Optional.ofNullable(productDocument));

    mockMvc
        .perform(delete(UriPathConsts.PUBLIC_PRODUCT_BY_ID, PRODUCT_ID)
            .header(HeaderConsts.HEADER_USER_ID, SELLER_USER_ID)
            .header(HeaderConsts.HEADER_USER_ROLE, AuthorizationEnum.SELLER_ROLE.getAuthRole())
        )
        .andExpect(status().isOk())
        .andExpect(Matchers.contentIsEmpty());

  }

  @DisplayName("Delete Product should fail with 404 code, No product with id 'productId' exists\"")
  @Test
  void deleteProductShouldFailWith404() throws Exception {
    when(productRepository.findByProductId(PRODUCT_ID)).thenReturn(Optional.empty());

    mockMvc
        .perform(delete(UriPathConsts.PUBLIC_PRODUCT_BY_ID, PRODUCT_ID)
            .header(HeaderConsts.HEADER_USER_ID, SELLER_USER_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().is4xxClientError())
        .andExpect(Matchers.contentIsEmpty());
  }

  @DisplayName("Get all products should succeed with 200 code and paged result")
  @Test
  void getAllProductsShouldSucceedWith200() throws Exception {
    final var productMock = ProductMock.getInstance();
    List<ProductDocument> products = productMock.getProducts();
    when(productRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(products));

    mockMvc
        .perform(MockMvcRequestBuilders.get(UriPathConsts.PUBLIC_PRODUCT_GET_ALL))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.totalPages", is(1)))
        .andExpect(jsonPath("$.totalElements", is(2)))
        .andExpect(jsonPath("$.content", hasSize(2)))
        .andExpect(jsonPath("$.content[0].productId", is(productMock.getProduct1().getProductId())))
        .andExpect(jsonPath("$.content[0].name", is(productMock.getProduct1().getName())))
        .andExpect(jsonPath("$.content[0].sellerUserId", is(productMock.getProduct1().getSellerUserId())))
        .andExpect(jsonPath("$.content[0].cost", is(String.valueOf(productMock.getProduct1().getCost()))))
        ;
  }

  @DisplayName("Get all products should succeed with 200 code and empty paged result")
  @Test
  void getAllProductsWithEmptyResultShouldSucceedWith200() throws Exception {
    when(productRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of()));

    mockMvc
        .perform(get(UriPathConsts.PUBLIC_PRODUCT_GET_ALL))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.totalPages", is(1)))
        .andExpect(jsonPath("$.totalElements", is(0)))
        .andExpect(jsonPath("$.content", empty()));
  }

  @DisplayName("Get all products should fail with 500 code")
  @Test
  void getAllProductsShouldFailWith500OnException() throws Exception {
    doThrow(new RuntimeException("some error")).when(productRepository).findAll(any(Pageable.class));

    mockMvc
        .perform(get(UriPathConsts.PUBLIC_PRODUCT_GET_ALL))
        .andExpect(status().isInternalServerError())
        .andExpect(content().string(ExceptionAdvice.INTERNAL_SERVER_ERROR_MSG));
  }

}
