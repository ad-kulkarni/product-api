package api.product.repository;

import api.product.model.ProductCategoryDb;
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
public class ProductCategoryRepository {
    private Logger logger = LoggerFactory.getLogger(ProductRepository.class);

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
            logger.error("Product Category not found!");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Category not found!");
        }
        return productCategoryDbOptional.get();
    }

    public void createProductCategory(ProductCategoryDb productCategoryDb) {
        jdbcTemplate.update("insert into product_category (name) values(?)", new Object[]{productCategoryDb.getName()});
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
            logger.error("Product Category does not exist!");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Category does not exist!");
        }
        jdbcTemplate.update("delete from product where category_id = ?", new Object[]{categoryId});
        jdbcTemplate.update("delete from product_category where id = ?", new Object[]{categoryId});
    }
}
