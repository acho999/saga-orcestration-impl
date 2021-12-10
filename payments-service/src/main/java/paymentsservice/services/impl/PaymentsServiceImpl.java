package paymentsservice.services.impl;

import com.angel.models.states.PaymentState;
import org.modelmapper.convention.MatchingStrategies;
import paymentsservice.models.Payment;
import paymentsservice.models.User;
import paymentsservice.repos.PaymentsRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paymentsservice.services.api.PaymentsService;
import paymentsservice.services.api.UsersService;


@Service
@Transactional
public class PaymentsServiceImpl implements PaymentsService {

    @Autowired
    private PaymentsRepo repo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UsersService usersService;


    @Override
    public boolean savePayment(String userId, Payment payment) {
        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        User user = this.mapper.map(this.usersService.getUser(userId), User.class);

        if (0 <= (user.getBalance() - payment.getAmount())){

            //to do invoke reject payment
            return false;
        }

        this.usersService.changeBalance(userId, payment);

        this.repo.saveAndFlush(payment);

        return true;
    }

    @Override
    public boolean reversePayment(String userId, String paymentId){

        Payment pmt = this.repo.getById(paymentId);

        pmt.setState(PaymentState.PAYMENT_REJECTED);

        return true;
    }



}
