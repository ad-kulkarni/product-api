package api.product.controller;

import api.product.helper.ProductHelper;
import api.product.model.Product;
import api.product.model.ProductCategory;
import api.product.model.ProductCategoryDb;
import api.product.model.ProductDb;
import api.product.service.ProductCategoryService;
import api.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductHelper productHelper;

    @GetMapping("/api/products")
    public Set<ProductCategory> getAllProducts() {

        List<ProductDb> productDbs = productService.getAllProducts();

        List<ProductCategoryDb> productCategoriesDb = productCategoryService.getAllProductCategories();

        return productHelper.getAllEnrichedProductsByCategory(productDbs, productCategoriesDb);
    }

    @GetMapping("/api/products/{productId}")
    public Product getProductById(@PathVariable Long productId) {

        ProductDb productDb = productService.getProductById(productId);

        return productHelper.getEnrichedProduct(productDb);
    }

    @PostMapping(value = "/api/products")
    public void createProduct(@RequestBody ProductDb productDb) {
        productService.createProduct(productDb);
    }

    @PutMapping(value = "/api/products/{productId}")
    public void updateProduct(@PathVariable Long productId, @RequestBody ProductDb productDb) {
        productDb.setId(productId);
        productService.updateProduct(productDb);
    }

    @DeleteMapping(value = "/api/products/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setProductCategoryService(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    public void setProductHelper(ProductHelper productHelper) {
        this.productHelper = productHelper;
    }
}
