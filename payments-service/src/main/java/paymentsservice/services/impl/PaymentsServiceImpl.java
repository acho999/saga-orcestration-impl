package paymentsservice.services.impl;

import com.angel.models.DTO.UserDTO;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import paymentsservice.models.Payment;
import paymentsservice.models.User;
import paymentsservice.repos.PaymentsRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paymentsservice.services.api.PaymentsService;
import paymentsservice.services.api.UsersService;

import java.util.Collection;

@Service
@Transactional
public class PaymentsServiceImpl implements PaymentsService {

    @Autowired
    private PaymentsRepo repo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UsersService usersService;

    public PaymentsServiceImpl(){
        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Override
    public boolean savePayment(String userId, Payment payment) {

        User user = this.mapper.map(this.usersService.getUser(userId), User.class);

        if (0 <= (user.getBalance() - payment.getAmount())){

            //to do invoke reject payment
            return false;
        }

        this.usersService.changeBalance(userId, payment.getAmount());

        this.repo.saveAndFlush(payment);

        return true;
    }



}
