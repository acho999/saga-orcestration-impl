package paymentsservice.services;

import paymentsservice.models.Product;

import java.util.Collection;

public interface ProductInventoryService {

    Collection<Product> getAll();

}
