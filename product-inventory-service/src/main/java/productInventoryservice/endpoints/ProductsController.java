package productInventoryservice.endpoints;

import com.angel.models.DTO.ProductDTO;
import com.angel.models.DTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

}
