package api.product.service;

import api.product.model.ProductCategory;
import api.product.model.ProductCategoryDb;
import api.product.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public List<ProductCategoryDb> getAllProductCategories() {
        return productCategoryRepository.getAllProductCategories();
    }

    public ProductCategoryDb getProductCategoryById(Long categoryId) {
        return productCategoryRepository.getProductCategoryById(categoryId);
    }

    public void createProductCategory(ProductCategoryDb productCategoryDb) {
        productCategoryRepository.createProductCategory(productCategoryDb);
    }

    public void updateProductCategory(ProductCategoryDb productCategoryDb) {
        productCategoryRepository.updateProductCategory(productCategoryDb);
    }

    public void deleteProductCategory(Long categoryId) {
        productCategoryRepository.deleteProductCategory(categoryId);
    }
}
