package productInventoryservice.services.api;

import com.angel.models.DTO.ProductDTO;

import java.util.Collection;

public interface ProductInventoryService {

    Collection<ProductDTO> getAll();

}
