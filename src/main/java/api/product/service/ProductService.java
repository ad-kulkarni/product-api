package api.product.service;

import api.product.model.ProductDb;
import api.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDb> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public List<ProductDb> getAllProductsByCategoryId(Long categoryId) {
        return productRepository.getAllProductsByCategoryId(categoryId);
    }

    public ProductDb getProductById(Long productId) {
        return productRepository.getProductById(productId);

    }
    public void createProduct(ProductDb productDb) {
        productRepository.createProduct(productDb);
    }

    public void updateProduct(ProductDb productDb) {
        productRepository.updateProduct(productDb);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteProduct(productId);
    }
}
