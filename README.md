# :checkered_flag: PRODUCT MGMT Cloning 완료
**Commit**: `05685053c9c3581b84030ce7fd9b0d3c8c6f6fef`  
**Branch**: [react-springboot-rest-api/product-mgmt](https://github.com/woody35545/react-springboot-rest-api/tree/product-mgmt)

# :checkered_flag: ORDER API Cloning 완료
**Commit**: `0748524d5ff6a6908c5529fec341971926c00245`  
**Branch**: [react-springboot-rest-api/order-api](https://github.com/woody35545/react-springboot-rest-api/tree/order-api)


# :heavy_check_mark: Requirements
* 보안상 application.properties를 git에서 제외하였다. 따라서 프로젝트를 run 하기 위해서는 아래와 같은 설정들이 별도로 작성되어야 한다.

**.../resoucres/application.properties**
```
# MySQL
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# DB Source URL
spring.datasource.url=jdbc:mysql://{DB Server Address}:{Port}/{Database Name}
a
# DB username
spring.datasource.username={id for DB user}

# DB password
spring.datasource.password={password for DB user}
```

**.../resources/application-test.yml** -> test에 사용되는 database 정보
```
spring:
  datasource:
    url: jdbc:mysql://{DB Server Address}:2215/{Database Name}
    username: {id for DB user}
    password: {password for user}

```  
# 📑 Database Schema

### **products**

![image](https://user-images.githubusercontent.com/84436996/230774454-3971a8e9-0e07-451a-92a3-1555aec278b2.png)
### o******rders******

![image](https://user-images.githubusercontent.com/84436996/230774460-76f41dfc-caab-4415-85cc-69f9c57f9054.png)
### order_items

![image](https://user-images.githubusercontent.com/84436996/230774465-f38cf9a7-05e9-42f1-bb72-25c37c16b402.png)
# :chart_with_upwards_trend: Progress
* 각 Repository를 바로 구현체로 구현하지 않고 Interface와 구현체를 분리하였다
* 현재는 NamedParameterJdbcTemplate을 이용한 jdbcRepository의 구현체로 사용중
* 추후 JPA로도 구현하여 적절하게 혼합하여 사용할 예정


# :bug: Bugs

### 1. UUID type 문제 (해결 완료)

UUID를 insert 할 때 byte로 변환하는 과정에서 문제가 있는것으로 확인.
문제 해결될 때까지 나머지 구현이 지체되지 않도록 `UUID` 타입을 `String`으로 변경하여 다루었다. 
```
/* pseudo */
productId := UUID.RandomUUID().toString();
```

```java
public class Product {
    private final UUID productId;
    private String productName;
    private Category category;
    private long price;

    private String description;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
```

### DB Schema 변경 (products)
또한 이에 따라 DB Schema도 `BINARY`에서 `VARCHAR`로 변경하였다.  
<img width="500" alt="image" src="https://user-images.githubusercontent.com/84436996/230708842-2c65eb64-e202-4862-adb1-6db28c8a39ed.png">  


**ProductJdbcRepository.java**
```java
@Override
public Product insert(Product product) {

/*  String SQL ="INSERT INTO products(product_id, product_name, category, price, description, created_at, updated_at) " +
                "VALUES(UUID_TO_BIN(:productId), :productName, :category, :price, :description, :createdAt, :updatedAt)"; */

        String SQL ="INSERT INTO products(product_id, product_name, category, price, description, created_at, updated_at) " +
                "VALUES(UUID_TO_BIN(:productId), :productName, :category, :price, :description, :createdAt, :updatedAt)";

        var update = jdbcTemplate.update(SQL, toParamMap(product));
    if(update != 1)
        throw new RuntimeException("Nothing was inserted");
    return product;
}
```
UUID_TO_BIN 도 사용하지 않도록 바꿔주었다. 

### 변경 후 product insert test 

**테스트 코드 작성(ProductJdbcRepositoryTest.java)** 
```java
@Autowired
ProductRepository productRepository;
private final Product newProduct = new Product(UUID.randomUUID().toString().replace("-",""), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L,"", LocalDateTime.now(), LocalDateTime.now());

    @Test
    @Order(1)
    @DisplayName("product insert test")
    void testInsert(){
        productRepository.insert(newProduct);
        var all = productRepository.findAll();
        assertThat(all.isEmpty(), is(false));
    }
```

### 테스트 결과
<img width="315" alt="image" src="https://user-images.githubusercontent.com/84436996/230708615-ed50e4e5-6692-45b7-aa7d-7c89821db761.png">
정상적으로 추가되는 것을 확인하였다.


### 2. Embedded Mysql Connection은 되는데 테이블 추가하면 오류나는 문제 (해결 완료)
<img width="700" alt="image" src="https://user-images.githubusercontent.com/84436996/230707085-6327eb23-4673-41fd-be34-c3d21a0d686d.png">  

- 직접 구축한 mysql server에 connection 하여 테스트하면 성공하는데 `embedded mysql`을 이용하면 실패하는 것을 보아서, Product table을 추가하는 sql문인 `scehma.sql`을 제대로 읽지 못하는 것으로 보인다.  
- 아직 문제를 해결하지 못하여 embedded mysql를 사용하지 않고 직접 구축한 db 서버에 connection 하여 진행하였다.

### 3. LocalDateTime이 DB에 추가될 때 밀리세컨드가 절삭되어 반영되는 문제 (해결 완료)

- Update는 되는데 Test를 실패해서 확인해보니 다음과 같은 문제가 있었다.

Java에 구현된 Enitiy의 CreatedAt:  `createdAt: <2023-04-08T21:02:36.338311>`

Mysql server에 반영된 값: `createdAt: <2023-04-08T21:02:36>`

### **사용한 테스트 코드**

```java
...
@Autowired
    ProductRepository productRepository;
    private static final Product newProduct = new Product(UUID.randomUUID().toString().replace("-",""), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L);

@Test
    @Order(5)
    @DisplayName("update test")
    void testUpdate() {
        newProduct.setProductName("updated-product");
        productRepository.update(newProduct);

        var product = productRepository.findById(newProduct.getProductId());
        assertThat(product.isEmpty(), is(false));
        assertThat(product.get(), samePropertyValuesAs(newProduct));
    }
```

### **테스트 결과**

![image](https://user-images.githubusercontent.com/84436996/230720499-2ca75f53-42a9-4c0c-b2b0-7e448b177fba.png)  

확인 결과 entity를 영속화 할 때, createdAt의 밀리세컨드 단위가 절삭되어 들어가면서 문제가 생기는 것이었다.

- product.createdAt = `createdAt: <2023-04-08T21:09:32.196161>`
- 실제 DB에 반영된 값 = `createdAt: <2023-04-08T21:09:32>`

![image](https://user-images.githubusercontent.com/84436996/230720509-6cce0156-cecb-49bf-8436-21294279e267.png)  

그래서 테스트 할 때 밀리세컨드 차이 때문에, 동일한 Record임에도 불구하고 

samePropertyValuesAs ⇒ False가 발생한 것이었다.

따라서 Entiy에서 LocalDateTime을 초기화 하는 부분에서 밀리세컨드 단위를 아예 사용하지 않도록 하여 해결하였다.

다음과 같이 모든 LocalDateTime 초기화 파트를 같은 방식으로 변경하여 해결하였다.

**Product.java** 

```java
public Product(String productId, String productName, Category category, long price) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        //this.createdAt = LocalDateTime.now();
        this.createdAt = LocalDateTime.now().withNano(0);
				//this.updatedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now().withNano(0);
    }
```

### 수정 후 테스트 결과
<img width="400" alt="image" src="https://user-images.githubusercontent.com/84436996/230720622-58afa7e5-1c39-443a-955f-31304d96627d.png">

