package com.app.productservice.application;

import com.app.productservice.infrastructure.persistence.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public abstract class ApplicationIT {

  @MockBean
  private MappingMongoConverter mappingMongoConverter;

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  private ApplicationContext context;

  /**
   * Prevent creation of mongo repositories
   */
  @MockBean
  protected ProductRepository productRepository;

  @Test
  void contextLoads() {
    assertNotNull(context);
  }

}
