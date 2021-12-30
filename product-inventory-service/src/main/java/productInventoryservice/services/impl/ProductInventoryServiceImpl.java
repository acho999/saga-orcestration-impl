package productInventoryservice.services.impl;

import com.angel.models.DTO.ProductDTO;
import org.apache.kafka.common.quota.ClientQuotaAlteration;
import org.modelmapper.convention.MatchingStrategies;
import productInventoryservice.models.Product;
import productInventoryservice.repos.ProductsInventoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import productInventoryservice.services.api.ProductInventoryService;

import java.util.Optional;


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

        Optional<Product> product = this.repo.findById(productId);
        ProductDTO prod = this.mapper.map(product.get(), ProductDTO.class);

        this.oldQuantity = prod.getQuantity();

        return prod;
    }
//tested
    @Override
    public boolean  isAvailable(String productId, int desiredQuantity){

        ProductDTO product = this.getProduct(productId);

        int quantity = product.getQuantity();

        if (quantity <= 0 || desiredQuantity > quantity){
            //invoke product reserve cancelation command
            return false;
        }
        return true;

    }
//tested
    @Override
    public void resetQuantity(String productId) {
        Product prod = this.repo.findById(productId).get();
        prod.setQuantity(this.oldQuantity);
        this.repo.saveAndFlush(prod);
    }

    @Override
    public ProductDTO createProduct(ProductDTO product) {
        Product prod = new Product();
        prod.setQuantity(product.getQuantity());
        prod.setDescription(product.getDescription());
        prod.setPrice(product.getPrice());
        prod.setName(product.getName());
        this.repo.saveAndFlush(prod);

        ProductDTO dto = product;
        dto.setId(prod.getId());
        System.out.println(dto.getId());

        return dto;
    }
//tested
    @Override
    public void extractQuantity(String productId, int qty) {
        Product prod = this.repo.findById(productId).get();
        this.oldQuantity = prod.getQuantity();
        int quantity = prod.getQuantity() - qty;
        if (quantity <= 0){
            return;
        }
        prod.setQuantity(quantity);
        this.repo.saveAndFlush(prod);
    }
}
