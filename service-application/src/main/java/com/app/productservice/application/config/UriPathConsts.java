package com.app.productservice.application.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constants for the Product Uri Path.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UriPathConsts {

  /**
   * Swagger Path
   */
  public static final String SWAGGER_PATH = "/swagger-ui/api-docs.html";

  /**
   * Admin Path
   */
  public static final String ADMIN = "/admin";

  /**
   * Public
   */
  private static final String VERSION = "/v1";

  private static final String PRODUCT = "/product";
  private static final String PRODUCTS = "/products";


  /**
   * Product Uri path.
   */
  public static final String PUBLIC_PRODUCT = VERSION + PRODUCT;
  public static final String PUBLIC_PRODUCT_GET_ALL = VERSION + PRODUCTS;
  public static final String PUBLIC_PRODUCT_BY_ID = VERSION + PRODUCT + "/{productId}";

  /**
   * Routes to admin functionality. '/contents' prefix is used for routing purposes.
   */
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class AdminUriPathConsts {

    /**
     * Product Admin URI path
     */
    public static final String ADMIN_PRODUCT = ADMIN + PUBLIC_PRODUCT;
    public static final String ADMIN_PRODUCT_BY_ID = ADMIN_PRODUCT + "/{productId}";

  }

}
