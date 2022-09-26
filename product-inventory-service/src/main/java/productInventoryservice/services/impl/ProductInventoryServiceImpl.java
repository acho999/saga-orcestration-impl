package productInventoryservice.services.impl;

import com.angel.models.DTO.ProductDTO;
import com.angel.models.exceptions.NotFoundException;
import com.angel.models.states.PaymentState;
import com.angel.saga.logging.CustomLogging;
import org.apache.kafka.common.quota.ClientQuotaAlteration;
import org.modelmapper.convention.MatchingStrategies;
import productInventoryservice.models.Product;
import productInventoryservice.repos.ProductsInventoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import productInventoryservice.services.api.ProductInventoryService;

import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;


@Service
@Transactional
public class ProductInventoryServiceImpl implements ProductInventoryService {

    private ProductsInventoryRepo repo;
    private ModelMapper mapper;
    private int oldQuantity;

    @Autowired
    public ProductInventoryServiceImpl(ProductsInventoryRepo repo, ModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public ProductDTO getProduct(String productId) {

        if( productId.isEmpty() || Objects.isNull(productId)){
            throw new IllegalArgumentException("ProductId ca not be null or empty string!");
        }

        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Optional<Product> product = this.repo.findById(productId);
        if(product.isEmpty()){
            throw new NotFoundException("Product not found!");
        }
        ProductDTO prod = this.mapper.map(product.get(), ProductDTO.class);

        this.oldQuantity = prod.getQuantity();

        return prod;
    }

    @Override
    public boolean  isAvailable(String productId, int desiredQuantity){

        if(desiredQuantity == 0){
            throw new IllegalArgumentException("Desired quantity should be greater than 0!");
        }

        ProductDTO product = this.getProduct(productId);

        int quantity = product.getQuantity();

        if (quantity <= 0 || desiredQuantity > quantity){
            return false;
        }
        return true;
    }

    @Override
    public void resetQuantity(String productId, int quantity, PaymentState state) {

        if(productId.isEmpty() || Objects.isNull(productId)){
            throw new IllegalArgumentException("ProductId can not be null or empty!");
        }
        if(quantity == 0){
            throw new IllegalArgumentException("Quantity must be greater than 0!");
        }
        if(Objects.isNull(state)){
            throw new IllegalArgumentException("State can not be null!");
        }

        Optional<Product> prod = this.repo.findById(productId);

        if(prod.isEmpty()){
            throw new NotFoundException("Product not found!");
        }

        int prodQuantity = prod.get().getQuantity();

        if(this.oldQuantity <= 0){
            this.oldQuantity = prodQuantity;
        }

        if(quantity <= prodQuantity && state.equals(PaymentState.REJECTED)){
            this.oldQuantity = quantity + prodQuantity;
        }

        CustomLogging.log(ProductInventoryServiceImpl.class, "after reset - " + oldQuantity);

        prod.get().setQuantity(this.oldQuantity);
        this.repo.saveAndFlush(prod.get());
    }

    @Override
    public ProductDTO createProduct(ProductDTO product) {
        if(Objects.isNull(product)){
            throw new IllegalArgumentException("ProductDTO can not be null!");
        }
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

    @Override
    public void extractQuantity(String productId, int qty) {

        if(productId.isEmpty() || Objects.isNull(productId)){
            throw new IllegalArgumentException("ProductId can not be null or empty string!");
        }
        if (qty == 0){
            throw new IllegalArgumentException("Quantity must be greater than o!");
        }

        Optional<Product> prod = this.repo.findById(productId);

        if(prod.isEmpty()){
            throw new NotFoundException("Product not found!");
        }

        Product product = prod.get();

        this.oldQuantity = product.getQuantity();

        int quantity = this.oldQuantity - qty;

        CustomLogging.log(ProductInventoryServiceImpl.class,"before reset - " + quantity);

        if (quantity <= 0){
            return;
        }

        product.setQuantity(quantity);
        this.repo.saveAndFlush(product);
    }
}
