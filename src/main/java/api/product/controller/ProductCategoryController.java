package api.product.controller;

import api.product.helper.ProductHelper;
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

@RestController
public class ProductCategoryController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductHelper productHelper;

    @GetMapping("/api/products/category/{categoryId}")
    public ProductCategory getAllProductsForCategory(@PathVariable Long categoryId) {
        ProductCategoryDb productCategoryDb = productCategoryService.getProductCategoryById(categoryId);

        List<ProductDb> productDbs = productService.getAllProductsByCategoryId(categoryId);

        return productHelper.enrichProductsForCategory(productCategoryDb, productDbs);
    }

    @PostMapping("/api/products/category")
    public void createProductCategory(@RequestBody ProductCategoryDb productCategoryDb) {

        productCategoryService.createProductCategory(productCategoryDb);
    }

    @PutMapping("/api/products/category/{categoryId}")
    public void updateProductCategory(@PathVariable Long categoryId, @RequestBody ProductCategoryDb productCategoryDb) {
        productCategoryDb.setId(categoryId);
        productCategoryService.updateProductCategory(productCategoryDb);
    }

    @DeleteMapping("/api/products/category/{categoryId}")
    public void deleteProductCategory(@PathVariable Long categoryId) {
        productCategoryService.deleteProductCategory(categoryId);
    }
}
