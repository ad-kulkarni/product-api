package api.product.repository;

import api.product.model.ProductDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ProductDbRepository extends JpaRepository<ProductDb, Long> {
}
