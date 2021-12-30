package paymentsservice.endpoints;

import com.angel.models.DTO.PaymentRequestDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import paymentsservice.services.api.PaymentsService;

@RestController
@RequestMapping(value = "/payments")
public class PaymentsController {

    @Autowired
    private PaymentsService paymentsService;

    @Autowired
    private ModelMapper mapper;

//    @RequestMapping(value = "/create",
//        method = RequestMethod.POST,
//        consumes = MediaType.APPLICATION_JSON_VALUE,
//        produces = MediaType.APPLICATION_JSON_VALUE
//    )
//    public ResponseEntity<Payment> createPayment(@RequestBody PaymentRequestDTO payment){
//        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//        Payment pment = this.mapper.map(this.paymentsService.createPayment(payment), Payment.class);
//        return new ResponseEntity<>(pment, null, HttpStatus.CREATED);
//    }

    @RequestMapping(value = "/reverse",
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity reversePayment(@RequestParam String userId, @RequestParam(required = false) String paymentId){

        this.paymentsService.reversePayment(userId, paymentId);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/save",
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity savePayment(@RequestParam String userId, @RequestBody PaymentRequestDTO pmnt){
        pmnt.setUserId(userId);
        this.paymentsService.savePayment(userId, pmnt);
        return ResponseEntity.ok().build();
    }


}
