package api.product.repository;

import api.product.model.ProductCategoryDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ProductCategoryDbRepository extends JpaRepository<ProductCategoryDb, Long> {
}
