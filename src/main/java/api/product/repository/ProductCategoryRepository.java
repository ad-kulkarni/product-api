package api.product.repository;

import api.product.model.ProductCategoryDb;
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
public class ProductCategoryRepository {
    private Logger logger = LogManager.getLogger(ProductCategoryRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ProductCategoryDbRepository productCategoryDbRepository;

    public List<ProductCategoryDb> getAllProductCategories() {
        return jdbcTemplate.query("select * from product_category", new BeanPropertyRowMapper<>(ProductCategoryDb.class));
    }

    public ProductCategoryDb getProductCategoryById(Long categoryId) {
        Optional<ProductCategoryDb> productCategoryDbOptional = productCategoryDbRepository.findById(categoryId);
        if(!productCategoryDbOptional.isPresent()) {
            logger.error("Product Category not found for id:" + categoryId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Category not found for id:" + categoryId);
        }
        return productCategoryDbOptional.get();
    }

    public void createProductCategory(ProductCategoryDb productCategoryDb) {
        try{
            ProductCategoryDb existingProductCategory = jdbcTemplate.queryForObject("select * from product where name = ?", new Object[]{productCategoryDb.getName()}, new BeanPropertyRowMapper<>(ProductCategoryDb.class));

            if(existingProductCategory != null) {
                logger.info("Product Category with name " + productCategoryDb.getName() + " already exists!");
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Product Category with name " + productCategoryDb.getName() + " already exists!");
            }
        } catch (IncorrectResultSizeDataAccessException e) {
            logger.info("Creating new product category record");
            jdbcTemplate.update("insert into product_category (name) values(?)", new Object[]{productCategoryDb.getName()});
        }
    }

    public void updateProductCategory(ProductCategoryDb productCategoryDb) {
        if(!productCategoryDbRepository.findById(productCategoryDb.getId()).isPresent()) {
            logger.error("Product Category does not exist, hence cannot be updated!");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Category does not exist, hence cannot be updated!");
        }
        jdbcTemplate.update("update product_category set name = ? where id = ?", new Object[]{productCategoryDb.getName(), productCategoryDb.getId()});
    }

    public void deleteProductCategory(Long categoryId) {
        Optional<ProductCategoryDb> productCategoryDbOptional = productCategoryDbRepository.findById(categoryId);
        if(!productCategoryDbOptional.isPresent()) {
            logger.error("Product Category not found for id:" + categoryId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Category not found for id:" + categoryId);
        }
        jdbcTemplate.update("delete from product where category_id = ?", new Object[]{categoryId});
        jdbcTemplate.update("delete from product_category where id = ?", new Object[]{categoryId});
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setProductCategoryDbRepository(ProductCategoryDbRepository productCategoryDbRepository) {
        this.productCategoryDbRepository = productCategoryDbRepository;
    }
}
