package api.product.helper;

import api.product.model.ProductCategory;
import api.product.model.ProductCategoryDb;
import api.product.model.ProductDb;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ProductHelperTest {

    private ProductHelper productHelper;

    @Before
    public void setup() {
        productHelper = new ProductHelper();
    }

    @Test
    public void testGetAllEnrichedProductsByCategory() {
        ProductCategoryDb productCategoryDb = new ProductCategoryDb();
        productCategoryDb.setId(1L);
        productCategoryDb.setName("Home");

        List<ProductCategoryDb> productCategories = new ArrayList<>();
        productCategories.add(productCategoryDb);

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

        List<ProductCategory> resultingProducts = productHelper.getAllEnrichedProductsByCategory(products, productCategories);

        List<ProductCategory> productsList = new ArrayList<>(resultingProducts);

        Assert.assertNotNull(resultingProducts);
        Assert.assertEquals(1, productsList.size());
        Assert.assertEquals(2, productsList.get(0).getProducts().size());
    }
}
