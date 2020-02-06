package api.product.controller;

import api.product.helper.ProductHelper;
import api.product.model.ProductCategory;
import api.product.model.ProductCategoryDb;
import api.product.model.ProductDb;
import api.product.model.ProductWrapper;
import api.product.service.ProductCategoryService;
import api.product.service.ProductService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class ProductControllerTest {

    private ProductService productService;
    private ProductCategoryService productCategoryService;
    private ProductHelper productHelper;

    private ProductController productController = new ProductController();

    @Before
    public void setup() {
        productService = Mockito.mock(ProductService.class);
        productCategoryService = Mockito.mock(ProductCategoryService.class);
        productHelper = new ProductHelper();

        productController = new ProductController();
        productController.setProductService(productService);
        productController.setProductCategoryService(productCategoryService);
        productController.setProductHelper(productHelper);
    }

    @Test
    public void testGetAllProducts() {
        ProductCategoryDb productCategoryDb = new ProductCategoryDb();
        productCategoryDb.setId(1L);
        productCategoryDb.setName("Home");

        List<ProductCategoryDb> productCategories = new ArrayList<>();
        productCategories.add(productCategoryDb);

        Mockito.when(productCategoryService.getAllProductCategories()).thenReturn(productCategories);

        ProductDb productDb1 = new ProductDb();
        productDb1.setId(11L);
        productDb1.setCategoryId(1L);
        productDb1.setName("Houseware");

        ProductDb productDb2 = new ProductDb();
        productDb2.setId(12L);
        productDb2.setCategoryId(1L);
        productDb2.setName("Tools");

        List<ProductDb> products = new ArrayList<>();
        products.add(productDb1);
        products.add(productDb2);

        Mockito.when(productService.getAllProducts()).thenReturn(products);

        ProductWrapper productWrapper = productController.getAllProducts();

        List<ProductCategory> resultingProducts = new ArrayList<>(productWrapper.getCategories());

        Assert.assertNotNull(resultingProducts);
        Assert.assertEquals(1, resultingProducts.size());
        Assert.assertEquals(2, resultingProducts.get(0).getProducts().size());
    }
}
