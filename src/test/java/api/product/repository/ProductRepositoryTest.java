package api.product.repository;

import api.product.model.ProductCategoryDb;
import api.product.model.ProductDb;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

public class ProductRepositoryTest {
    private JdbcTemplate jdbcTemplate;
    private ProductDbRepository productDbRepository;
    private ProductCategoryDbRepository productCategoryDbRepository;

    private ProductRepository productRepository;

    @Before
    public void setup() {
        jdbcTemplate = Mockito.mock(JdbcTemplate.class);
        productDbRepository = Mockito.mock(ProductDbRepository.class);
        productCategoryDbRepository = Mockito.mock(ProductCategoryDbRepository.class);

        productRepository = new ProductRepository();
        productRepository.setJdbcTemplate(jdbcTemplate);
        productRepository.setProductDbRepository(productDbRepository);
        productRepository.setProductCategoryDbRepository(productCategoryDbRepository);
    }

    @Test
    public void testCreateProduct() {
        ProductDb productDb = Mockito.mock(ProductDb.class);
        Mockito.when(productDb.getCategoryId()).thenReturn(1L);

        Optional<ProductCategoryDb> productCategoryDbOptional = Optional.of(new ProductCategoryDb());
        Mockito.when(productCategoryDbRepository.findById(1L)).thenReturn(productCategoryDbOptional);

        productRepository.createProduct(productDb);

        Mockito.verify(jdbcTemplate, Mockito.times(1)).update(anyString(), new Object[]{any(), any(), any()});
    }
}
