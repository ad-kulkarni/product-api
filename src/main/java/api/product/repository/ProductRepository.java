package api.product.repository;

import api.product.model.ProductCategoryDb;
import api.product.model.ProductDb;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    private Logger logger = LogManager.getLogger(ProductRepository.class);

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
            logger.error("Product not found for id:" + productId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found!");
        }
        return productDbOptional.get();
    }

    public void createProduct(ProductDb productDb) {
        Optional<ProductCategoryDb> productCategoryDbOptional = productCategoryDbRepository.findById(productDb.getCategoryId());
        if(!productCategoryDbOptional.isPresent()) {
            logger.error("Product category does not exist for category id:" + productDb.getCategoryId());
        }
        try {
            ProductDb existingProduct = jdbcTemplate.queryForObject("select * from product where name = ?", new Object[]{productDb.getName()}, new BeanPropertyRowMapper<>(ProductDb.class));
            if(existingProduct != null) {
                logger.info("Product with name " + productDb.getName() + " already exists!");
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Product with name " + productDb.getName() + " already exists!");
            }
        } catch (IncorrectResultSizeDataAccessException e) {
            logger.info("Creating new product record");
            jdbcTemplate.update("insert into product (name, category_id, quantity) values(?, ?, ?)", new Object[]{productDb.getName(), productDb.getCategoryId(), productDb.getQuantity()});
        }
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

    public void setProductDbRepository(ProductDbRepository productDbRepository) {
        this.productDbRepository = productDbRepository;
    }

    public void setProductCategoryDbRepository(ProductCategoryDbRepository productCategoryDbRepository) {
        this.productCategoryDbRepository = productCategoryDbRepository;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
