package com.app.productservice.application.support;


import static com.app.productservice.application.config.AuthorizationEnum.SELLER_ROLE;
import static com.app.productservice.application.config.AuthorizationEnum.USER_ROLE;

import com.app.productservice.application.config.AuthorizationEnum;
import com.app.productservice.application.config.HeaderConsts;
import java.util.Map;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HeaderTools {

  /**
   * Extract user id from header.
   *
   * @param headers all http headers
   * @return userId if present
   */
  public static Optional<String> extractUserId(Map<String, String> headers) {
    return Optional.ofNullable(headers.get(HeaderConsts.HEADER_USER_ID));
  }

  /**
   * Extract Email from header.
   *
   * @param headers all http headers
   * @return Email if present
   */
  public static Optional<String> extractEmail(Map<String, String> headers) {
    return Optional.ofNullable(headers.get(HeaderConsts.HEADER_EMAIL));
  }

  /**
   * Extract Email Verified from header.
   *
   * @param headers all http headers
   * @return emailVerified if present
   */
  public static Optional<String> extractEmailVerified(Map<String, String> headers) {
    return Optional.ofNullable(headers.get(HeaderConsts.HEADER_EMAIL_VERIFIED));
  }


  /**
   * Extract User Role from header.
   *
   * @param headers all http headers
   * @return emailVerified if present
   */
  public static Optional<String> extractUserRole(Map<String, String> headers) {
    return Optional.ofNullable(headers.get(HeaderConsts.HEADER_USER_ROLE));
  }

  public static AuthorizationEnum getUserRole(String userRole) {
    return userRole.contains("seller") ? SELLER_ROLE : USER_ROLE;
  }

}
