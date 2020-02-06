package api.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class ProductCategory {

    private Long id;
    private String category;
    private List<Product> products;

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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ProductCategory productCategory = (ProductCategory) obj;

        return this.category.equalsIgnoreCase(productCategory.getCategory());
    }

    @Override
    public int hashCode() {
        return this.category.hashCode();
    }
}
