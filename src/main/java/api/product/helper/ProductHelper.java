package api.product.helper;

import api.product.model.Product;
import api.product.model.ProductCategory;
import api.product.model.ProductCategoryDb;
import api.product.model.ProductDb;
import api.product.model.ProductWrapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductHelper {

    public ProductWrapper getAllProducts(List<ProductDb> productDbs, List<ProductCategoryDb> productCategoriesDb) {
        ProductWrapper productWrapper = new ProductWrapper();
        productWrapper.setCategories(getAllEnrichedProductsByCategory(productDbs, productCategoriesDb));

        return productWrapper;
    }

    public List<ProductCategory> getAllEnrichedProductsByCategory(List<ProductDb> productDbs, List<ProductCategoryDb> productCategoriesDb) {

        List<Product> products = getAllEnrichedProducts(productDbs);

        return getAllEnrichedProductCategories(productCategoriesDb, products);
    }

    private List<ProductCategory> getAllEnrichedProductCategories(List<ProductCategoryDb> productCategoriesDb, List<Product> products) {
        List<ProductCategory> productCategories = new ArrayList<>();
        for(ProductCategoryDb productCategoryDb : productCategoriesDb) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setId(productCategoryDb.getId());
            productCategory.setCategory(productCategoryDb.getName());

            productCategories.add(productCategory);
        }

        for(ProductCategory productCategory : productCategories) {
            List<Product> categoryBasedProducts = new ArrayList<>();
            for(Product product : products) {
                if(productCategory.getId().equals(product.getCategoryId())) {
                    categoryBasedProducts.add(product);
                }
            }
            productCategory.setProducts(categoryBasedProducts);
        }

        return productCategories;
    }

    private List<Product> getAllEnrichedProducts(List<ProductDb> productDbs) {
        List<Product> products = new ArrayList<>();

        for(ProductDb productDb : productDbs) {
            products.add(getEnrichedProduct(productDb));
        }

        return products;
    }

    public ProductCategory enrichProductsForCategory(ProductCategoryDb productCategoryDb, List<ProductDb> productDbs) {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(productCategoryDb.getId());
        productCategory.setCategory(productCategoryDb.getName());
        productCategory.setProducts(getAllEnrichedProducts(productDbs));

        return productCategory;
    }

    public Product getEnrichedProduct(ProductDb productDb) {
        Product product = new Product();
        product.setId(productDb.getId());
        product.setCategoryId(productDb.getCategoryId());
        product.setName(productDb.getName());
        product.setQuantity(productDb.getQuantity());

        return product;
    }
}
