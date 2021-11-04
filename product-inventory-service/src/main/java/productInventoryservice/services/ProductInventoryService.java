package productInventoryservice.services;

import DTO.ProductDTO;
import productInventoryservice.models.Product;

import java.util.Collection;

public interface ProductInventoryService {

    Collection<ProductDTO> getAll();

}
