package productInventoryservice.services.api;

import com.angel.models.DTO.ProductDTO;

import java.util.Collection;

public interface ProductInventoryService {

    ProductDTO getProduct(String productId);
    boolean isAvailable(String productId, int quantity);
    void resetQuantity(String productId);
    ProductDTO createProduct(ProductDTO product);


}
