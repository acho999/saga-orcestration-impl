package paymentsservice.endpoints;

import com.angel.models.DTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import paymentsservice.services.api.UsersService;

@RestController
@RequestMapping(value = "/users")
public class UsersController {

    @Autowired
    private UsersService service;

    @RequestMapping(value = "/create",
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user){

        UserDTO dto = this.service.createUser(user);
        return new ResponseEntity<>(dto, null, HttpStatus.CREATED);
    }

}
