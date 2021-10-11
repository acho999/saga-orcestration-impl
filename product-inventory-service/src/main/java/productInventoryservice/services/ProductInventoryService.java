package productInventoryservice.services;

import productInventoryservice.models.Product;

import java.util.Collection;

public interface ProductInventoryService {

    Collection<Product> getAll();

}
