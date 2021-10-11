package productInventoryservice.services;

import productInventoryservice.models.Product;
import productInventoryservice.repos.ProductsInventoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class PaymentsServiceImpl implements ProductInventoryService {

    @Autowired
    private ProductsInventoryRepo repo;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional
    public Collection<Product> getAll() {

        return null;
    }
}
