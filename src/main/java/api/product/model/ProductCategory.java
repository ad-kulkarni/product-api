package api.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;

public class ProductCategory {

    private Long id;
    private String category;
    private Set<Product> products;

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
