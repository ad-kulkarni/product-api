package api.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Product {

    private Long id;
    private Long categoryId;
    private String name;
    private Long quantity;

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Product product = (Product) obj;

        return this.name.equalsIgnoreCase(product.getName())
                && this.quantity == product.getQuantity().longValue();
    }

    @Override
    public int hashCode() {
        return this.name.hashCode() + this.quantity.hashCode();
    }
}
