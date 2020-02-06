package api.product.repository;

import api.product.model.ProductCategoryDb;
import api.product.model.ProductDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    private Logger logger = LoggerFactory.getLogger(ProductRepository.class);

    @Autowired
    private ProductDbRepository productDbRepository;

    @Autowired
    private ProductCategoryDbRepository productCategoryDbRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ProductDb> getAllProducts() {
        return productDbRepository.findAll();
    }

    public List<ProductDb> getAllProductsByCategoryId(Long categoryId) {
        Optional<ProductCategoryDb> productCategoryDbOptional = productCategoryDbRepository.findById(categoryId);
        if(!productCategoryDbOptional.isPresent()) {
            logger.error("Product Category not found!");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Category not found!");
        }
        return jdbcTemplate.query("select * from product where category_id = ?", new Object[]{categoryId}, new BeanPropertyRowMapper<>(ProductDb.class));
    }

    public ProductDb getProductById(Long productId) {
        Optional<ProductDb> productDbOptional = productDbRepository.findById(productId);
        if(!productDbOptional.isPresent()) {
            logger.error("Product not found!");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found!");
        }
        return productDbOptional.get();
    }

    public void createProduct(ProductDb productDb) {
        Optional<ProductCategoryDb> productCategoryDbOptional = productCategoryDbRepository.findById(productDb.getCategoryId());
        if(!productCategoryDbOptional.isPresent()) {
            logger.error("Product category does not exist!");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Category does not exist!");
        }
        jdbcTemplate.update("insert into product (name, category_id, quantity) values(?, ?, ?)", new Object[]{productDb.getName(), productDb.getCategoryId(), productDb.getQuantity()});
    }

    public void updateProduct(ProductDb productDb) {
        Optional<ProductDb> productDbOptional = productDbRepository.findById(productDb.getId());
        if(!productDbOptional.isPresent()) {
            logger.error("Product does not exist, hence cannot be updated!");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product does not exist, hence cannot be updated!");
        }
        jdbcTemplate.update("update product set name = ?, category_id = ?, quantity = ? where id = ?", new Object[]{productDb.getName(), productDb.getCategoryId(), productDb.getQuantity(), productDb.getId()});
    }

    public void deleteProduct(Long productId) {
        Optional<ProductDb> productDbOptional = productDbRepository.findById(productId);
        if(!productDbOptional.isPresent()) {
            logger.error("Product does not exist!");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found!");
        }
        jdbcTemplate.update("delete from product where id = ?", new Object[]{productId});
    }
}
