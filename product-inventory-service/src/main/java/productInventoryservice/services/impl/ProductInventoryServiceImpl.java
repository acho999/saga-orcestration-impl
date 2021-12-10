package productInventoryservice.services.impl;

import com.angel.models.DTO.ProductDTO;
import org.modelmapper.convention.MatchingStrategies;
import productInventoryservice.models.Product;
import productInventoryservice.repos.ProductsInventoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import productInventoryservice.services.api.ProductInventoryService;


@Service
@Transactional
public class ProductInventoryServiceImpl implements ProductInventoryService {


    @Autowired
    private ProductsInventoryRepo repo;

    @Autowired
    private ModelMapper mapper;

    private int oldQuantity;

    @Override
    public ProductDTO getProduct(String productId) {

        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        ProductDTO prod = this.mapper.map(this.repo.getById(productId), ProductDTO.class);

        this.oldQuantity = prod.getQuantity();

        return prod;
    }

    @Override
    public boolean  isAvailable(String productId, int quantity){

        ProductDTO product = this.getProduct(productId);

        if (product.getQuantity() <= 0){
            //invoke product reserve cancelation command
            return false;
        }

        Product prod = this.repo.getById(productId);
        prod.setQuantity(prod.getQuantity() - quantity);

        this.repo.saveAndFlush(prod);

        return true;

    }

    @Override
    public void resetQuantity(String productId) {
        Product prod = this.repo.getById(productId);
        prod.setQuantity(this.oldQuantity);
        this.repo.saveAndFlush(prod);
    }
}
