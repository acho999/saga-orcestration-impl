package paymentsservice.services.impl;

import com.angel.models.DTO.PaymentRequestDTO;
import com.angel.models.DTO.UserDTO;
import com.angel.models.states.PaymentState;
import org.modelmapper.convention.MatchingStrategies;
import paymentsservice.exceptions.NotFoundException;
import paymentsservice.models.Payment;
import paymentsservice.models.User;
import paymentsservice.repos.PaymentsRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paymentsservice.services.api.PaymentsService;
import paymentsservice.services.api.UsersService;

import java.util.Objects;
import java.util.Optional;


@Service
@Transactional
public class PaymentsServiceImpl implements PaymentsService {

    private final PaymentsRepo repo;
    private final ModelMapper mapper;
    private final UsersService usersService;

    private double productPrice;

    @Autowired
    public PaymentsServiceImpl(PaymentsRepo repo, ModelMapper mapper,
                               UsersService usersService) {
        this.repo = repo;
        this.mapper = mapper;
        this.usersService = usersService;
    }

    @Override
    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public Payment savePayment(String userId, PaymentRequestDTO pmnt) {

        if(userId.isEmpty() || Objects.isNull(pmnt)){
            throw new IllegalArgumentException("UserId can not be null or empty string. PaymentRequest can not be null!");
        }

        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Payment payment = this.createPayment(pmnt);

        System.out.println(this.productPrice + " from save payment");

        payment.setAmount(this.productPrice * payment.getQuantity());

        User user = this.mapper.map(this.usersService.getUser(userId), User.class);

        if ((user.getBalance() - payment.getAmount()) < 0){
            payment.setState(PaymentState.REJECTED);
        }

        this.repo.saveAndFlush(payment);
        this.usersService.changeBalance(userId, payment);
        return payment;
    }

    @Override
    public boolean reversePayment(String userId, String paymentId){

        if(userId.isEmpty() || paymentId.isEmpty()){
            throw new IllegalArgumentException("UserId and paymentId can not be empty string!");
        }

        Optional<Payment> pmt = this.repo.findById(paymentId);

        if (pmt.isEmpty()){
            throw new NotFoundException("Payment not found!");
        }

        pmt = this.repo.findAll().stream().filter(x->x.getUserId().getUserId().equals(userId)).findFirst();

        this.usersService.reverseUserBalance(userId, pmt.get().getAmount());

        return true;
    }

    @Override
    public Payment createPayment(PaymentRequestDTO payment) {

        if(Objects.isNull(payment)){
            throw new IllegalArgumentException("PaymentRequestDTO can not be null!");
        }

        Payment payment1 = this.mapper.map(payment, Payment.class);

        UserDTO usr = this.usersService.getUser(payment.getUserId());

        payment1.setUserId(this.mapper.map(usr, User.class));

        return payment1;
    }

    @Override
    public Payment getPayment(String id) {

        if(id.isEmpty() || Objects.isNull(id)){
            throw new IllegalArgumentException("Id can not be null or empty string!");
        }

       Optional<Payment> payment = this.repo.findById(id);

        if (payment.isEmpty()){
            throw new NotFoundException("Payment not found!");
        }

       return payment.get();
    }

}
