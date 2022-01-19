package productInventoryservice.endpoints;

import com.angel.models.DTO.ProductDTO;
import com.angel.models.DTO.UserDTO;
import com.angel.models.states.PaymentState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import productInventoryservice.services.api.ProductInventoryService;

@RestController
@RequestMapping(value = "/products")
public class ProductsController {


    @Autowired
    private ProductInventoryService service;

    @RequestMapping(value = "/create",
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO prod){

        ProductDTO dto = this.service.createProduct(prod);
        return new ResponseEntity<ProductDTO>(dto, null, HttpStatus.CREATED);
    }
    @RequestMapping(value = "/get/{id}",
        method = RequestMethod.GET,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ProductDTO> getProduct(@PathVariable String id){

        ProductDTO dto = this.service.getProduct(id);
        return new ResponseEntity<ProductDTO>(dto, null, HttpStatus.CREATED);
    }
//tested
    @RequestMapping(value = "/extract/{productId}",
        method = RequestMethod.GET,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity extractQuantity(@PathVariable String productId, @RequestParam int quantity){

        this.service.extractQuantity(productId, quantity);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/reset/{productId}",
        method = RequestMethod.GET,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity resetQuantity(@PathVariable String productId){

        this.service.resetQuantity(productId, 0, PaymentState.REJECTED);
        return ResponseEntity.ok().build();
    }
//tested
    @RequestMapping(value = "/isAvailable/{productId}",
        method = RequestMethod.GET,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity isAvailable(@PathVariable String productId, @RequestParam int quantity){

        boolean isAv = this.service.isAvailable(productId, quantity);
        return ResponseEntity.ok(isAv);
    }

}
