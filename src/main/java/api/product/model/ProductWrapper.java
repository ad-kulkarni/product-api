package api.product.model;

import java.util.List;

public class ProductWrapper {

    private List<ProductCategory> categories;

    public List<ProductCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<ProductCategory> categories) {
        this.categories = categories;
    }
}
