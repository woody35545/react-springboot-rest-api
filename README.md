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


# :chart_with_upwards_trend: Progress
* 각 Repository를 바로 구현체로 구현하지 않고 Interface와 구현체를 분리하였다
* 현재는 NamedParameterJdbcTemplate을 이용한 jdbcRepository의 구현체로 사용중
* 추후 JPA로도 구현하여 적절하게 혼합하여 사용할 예정


# :bug: Bugs

### 1. UUID type 문제
UUID를 insert 할 때 byte로 변환하는 과정에서 문제가 있는것으로 확인.
문제 해결될 때까지 나머지 구현이 지체되지 않도록 `UUID` 타입을 `String`으로 변경하여 다루었다. 
```
/* pseudo */
productId := UUID.RandomUUID().toString();
```
또한 이에 따라 DB Schema도 `BINARY`에서 `VARCHAR`로 변경하였음.

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

### 2. Embedded Mysql Connection은 되는데 테이블 추가하면 오류나는 문제
    
![image](https://user-images.githubusercontent.com/84436996/230707085-6327eb23-4673-41fd-be34-c3d21a0d686d.png)
    

- 직접 구축한 mysql server에 connection 하여 테스트하면 성공하는데 `embedded mysql`을 이용하면 실패하는 것을 보아서, Product table을 추가하는 sql문인 `scehma.sql`을 제대로 읽지 못하는 것으로 보인다.
- 아직 문제를 해결하지 못하여 embedded mysql를 사용하지 않고 직접 구축한 db 서버에 connection 하여 진행하였다.
