package api.product.controller;

import api.product.helper.ProductHelper;
import api.product.model.ProductCategory;
import api.product.model.ProductCategoryDb;
import api.product.model.ProductDb;
import api.product.service.ProductCategoryService;
import api.product.service.ProductService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class ProductCategoryControllerTest {

    private ProductService productService;
    private ProductCategoryService productCategoryService;
    private ProductHelper productHelper;

    private ProductCategoryController productCategoryController = new ProductCategoryController();

    @Before
    public void setup() {
        productService = Mockito.mock(ProductService.class);
        productCategoryService = Mockito.mock(ProductCategoryService.class);
        productHelper = new ProductHelper();

        productCategoryController = new ProductCategoryController();
        productCategoryController.setProductService(productService);
        productCategoryController.setProductCategoryService(productCategoryService);
        productCategoryController.setProductHelper(productHelper);
    }

    @Test
    public void testGetAllProductsForCategory() {
        ProductCategoryDb productCategoryDb = new ProductCategoryDb();
        productCategoryDb.setId(1L);
        productCategoryDb.setName("Home");

        Mockito.when(productCategoryService.getProductCategoryById(productCategoryDb.getId())).thenReturn(productCategoryDb);

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

        Mockito.when(productService.getAllProductsByCategoryId(productCategoryDb.getId())).thenReturn(products);

        ProductCategory resultingProductCategory = productCategoryController.getAllProductsForCategory(productCategoryDb.getId());

        Assert.assertEquals(1L, resultingProductCategory.getId().longValue());
        Assert.assertEquals("Home", resultingProductCategory.getCategory());
        Assert.assertEquals(2, resultingProductCategory.getProducts().size());
    }
}
