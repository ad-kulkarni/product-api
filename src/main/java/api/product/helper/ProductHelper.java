package api.product.helper;

import api.product.model.Product;
import api.product.model.ProductCategory;
import api.product.model.ProductCategoryDb;
import api.product.model.ProductDb;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ProductHelper {

    public Set<ProductCategory> getAllEnrichedProductsByCategory(List<ProductDb> productDbs, List<ProductCategoryDb> productCategoriesDb) {

        Set<Product> products = getAllEnrichedProducts(productDbs);

        return getAllEnrichedProductCategories(productCategoriesDb, products);
    }

    private Set<ProductCategory> getAllEnrichedProductCategories(List<ProductCategoryDb> productCategoriesDb, Set<Product> products) {
        Set<ProductCategory> productCategories = new HashSet<>();
        for(ProductCategoryDb productCategoryDb : productCategoriesDb) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setId(productCategoryDb.getId());
            productCategory.setCategory(productCategoryDb.getName());

            productCategories.add(productCategory);
        }

        for(ProductCategory productCategory : productCategories) {
            Set<Product> categoryBasedProducts = new HashSet<>();
            for(Product product : products) {
                if(productCategory.getId().equals(product.getCategoryId())) {
                    categoryBasedProducts.add(product);
                }
            }
            productCategory.setProducts(categoryBasedProducts);
        }

        return productCategories;
    }

    private Set<Product> getAllEnrichedProducts(List<ProductDb> productDbs) {
        Set<Product> products = new HashSet<>();

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
