package api.product.repository;

import api.product.model.ProductCategoryDb;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public class ProductCategoryRepositoryTest {

    private JdbcTemplate jdbcTemplate;
    private ProductCategoryDbRepository productCategoryDbRepository;

    private ProductCategoryRepository productCategoryRepository;

    @Before
    public void setup() {
        jdbcTemplate = Mockito.mock(JdbcTemplate.class);
        productCategoryDbRepository = Mockito.mock(ProductCategoryDbRepository.class);

        productCategoryRepository = new ProductCategoryRepository();
        productCategoryRepository.setJdbcTemplate(jdbcTemplate);
        productCategoryRepository.setProductCategoryDbRepository(productCategoryDbRepository);
    }

    @Test
    public void testGetProductCategoryByValidId() {
        Optional<ProductCategoryDb> productCategoryDbOptional = Optional.ofNullable(new ProductCategoryDb());

        Mockito.when(productCategoryDbRepository.findById(1L)).thenReturn(productCategoryDbOptional);

        Assert.assertNotNull(productCategoryRepository.getProductCategoryById(1L));
    }

    @Test(expected = ResponseStatusException.class)
    public void testGetProductCategoryByInvalidId() {
        Optional<ProductCategoryDb> productCategoryDbOptional = Optional.ofNullable(null);

        Mockito.when(productCategoryDbRepository.findById(1L)).thenReturn(productCategoryDbOptional);

        Assert.assertNotNull(productCategoryRepository.getProductCategoryById(1L));
    }
}
