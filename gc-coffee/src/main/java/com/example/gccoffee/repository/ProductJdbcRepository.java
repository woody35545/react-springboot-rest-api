package com.example.gccoffee.repository;

import com.example.gccoffee.model.Category;
import com.example.gccoffee.model.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.*;

import com.example.gccoffee.Utils;
import org.springframework.stereotype.Repository;

@Repository
public class ProductJdbcRepository implements ProductRepository{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProductJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query("select * FROM products", productRowMapper);
    }

    @Override
    public Product insert(Product product){
        String SQL ="INSERT INTO products(product_id, product_name, category, price, description, created_at, updated_at) " +
                "VALUES(:productId, :productName, :category, :price, :description, :createdAt, :updatedAt)";

        var update = jdbcTemplate.update(SQL, toParamMap(product));

    if(update != 1)
        throw new RuntimeException("Nothing was inserted");
    return product;
    }

    @Override
    public Product update(Product product) {
        return null;
    }

    @Override
    public Optional<Product> findById(UUID productId) {
        String SQL = "SELECT * FROM products WHERE product_id = :productId";
        try {
            return Optional.of(
                    jdbcTemplate.queryForObject("SELECT ", Collections.singletonMap("productId", productId), productRowMapper)
        );
        }catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> findByName(String productName) {
        String SQL = "SELECT * FROM products WHERE product_name = :productName";
        try {
            return Optional.of(
                    jdbcTemplate.queryForObject("SELECT ", Collections.singletonMap("productName", productName), productRowMapper)
            );
        }catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findByCategory(Category category) {
        String SQL = "SELECT * FROM products WHERE category = :category";
        return jdbcTemplate.query(SQL, Collections.singletonMap("category", category) ,productRowMapper);
    }

    @Override
    public void deleteAll() {
    }


    private static final RowMapper<Product> productRowMapper = (resultSet, i) -> {
//        var productId = Utils.toUUID(resultSet.getBytes("product_id"));
        var productId = resultSet.getString("product_id");
        var productName =resultSet.getString("product_name");
        var category =Category.valueOf(resultSet.getString("category"));
        var price =resultSet.getLong("price");
        var description =resultSet.getString("description");
        var createdAt =Utils.toLocalDateTime(resultSet.getTimestamp("created_at"));
        var updatedAt =Utils.toLocalDateTime(resultSet.getTimestamp("updated_at"));
        return new Product(productId, productName, category, price, description, createdAt, updatedAt);
    };
    private Map<String, Object> toParamMap(Product product) {
        var paramMap = new HashMap<String, Object>();
//        paramMap.put("productId", product.getProductId().toString().getBytes());
        paramMap.put("productId", product.getProductId().toString());
        paramMap.put("productName",  product.getProductName());
        paramMap.put("category", product.getCategory().toString());
        paramMap.put("price",product.getPrice());
        paramMap.put("description", product.getDescription());
        paramMap.put("createdAt", product.getCreatedAt());
        paramMap.put("updatedAt", product.getUpdatedAt());

        return paramMap;
    }


}
