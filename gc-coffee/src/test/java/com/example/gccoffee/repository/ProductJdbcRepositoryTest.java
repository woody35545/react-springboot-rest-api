package com.example.gccoffee.repository;
import org.junit.jupiter.api.Test;
import com.example.gccoffee.model.Category;
import com.example.gccoffee.model.Product;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.UUID;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static com.wix.mysql.config.MysqldConfig.*;
import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class ProductJdbcRepositoryTest {
    static EmbeddedMysql embeddedMysql;
    @BeforeAll
    static void setup() {
        var config = aMysqldConfig(v8_0_11)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul").
                build();
        embeddedMysql = anEmbeddedMysql(config)
                .addSchema("test-order-mgmt", ScriptResolver.classPathScripts("schema.sql"))
                .start();
    }

    @AfterAll
    static void cleanup() {
        embeddedMysql.stop();
    }

    @Autowired
    ProductRepository productRepository;
    private static final Product newProduct = new Product(UUID.randomUUID().toString().replace("-",""), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L);

    @Test
    @Order(1)
    @DisplayName("상품을 추가할 수 있다.")
    void testInsert(){
        productRepository.insert(newProduct);
        var all = productRepository.findAll();
        assertThat(all.isEmpty(), is(false));
    }

//    @Test
//    @Order(1)
//    @DisplayName("findAll 테스트")
//    void testFindAll(){
//        var all = productRepository.findAll();
//        assertThat(all.isEmpty(), is(true));
//    }


    @Test
    @Order(2)
    @DisplayName("상품을 이름으로 조회할 수 있다.")
    void testFindByName() {
        var product = productRepository.findByName(newProduct.getProductName());
        assertThat(product.isEmpty(), is(false));
    }

    @Test
    @Order(3)
    @DisplayName("상품을아이디로 조회할 수 있다.")
    void testFindById() {
        var product = productRepository.findById(newProduct.getProductId());
        assertThat(product.isEmpty(), is(false));
    }

    @Test
    @Order(4)
    @DisplayName("상품들을 카테고리로 조회할 수 있다.")
    void testFindByCategory() {
        var product = productRepository.findByCategory(Category.COFFEE_BEAN_PACKAGE);
        assertThat(product.isEmpty(), is(false));
    }

}