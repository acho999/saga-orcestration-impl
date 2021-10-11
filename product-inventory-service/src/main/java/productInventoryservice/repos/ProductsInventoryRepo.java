package productInventoryservice.repos;

import productInventoryservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsInventoryRepo extends JpaRepository<Product, String> {

}
