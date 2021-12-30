package paymentsservice.services.impl;

import com.angel.models.DTO.UserDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import paymentsservice.models.Payment;
import paymentsservice.models.User;
import paymentsservice.repos.UsersRepo;
import paymentsservice.services.api.UsersService;

import java.util.ArrayList;

import javax.transaction.Transactional;

@Service
@Transactional
public class UsersServiceImpl implements UsersService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UsersRepo repo;

    private double currentBalance;

    @Override
    public UserDTO createUser(UserDTO dto){

        User user = new User();
        user.setUserPayments(new ArrayList<Payment>());
        user.setBalance(dto.getBalance());
        this.repo.saveAndFlush(user);
        UserDTO userDTO = dto;
        userDTO.setUserId(user.getUserId());
        System.out.println(userDTO.getUserId());
        return userDTO;

    }

    @Override
    public UserDTO getUser(String userId){
        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        User usr = this.repo.findById(userId).get();
        UserDTO dto = mapper.map(usr, UserDTO.class);
        dto.setPayments(usr.getUserPayments());

        return dto;
    }

    @Override
    public void changeBalance(String userId, Payment payment){
        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        User usr = this.repo.findById(userId).get();

        usr.getUserPayments().add(payment);

        this.currentBalance = usr.getBalance() - payment.getAmount();

        if (currentBalance <= 0){
            this.currentBalance = usr.getBalance();
        }

        usr.setBalance(currentBalance);

        this.repo.saveAndFlush(usr);

    }

    @Override
    public void reverseUserBalance(String userId, double amount){

       User usr =  this.repo.findById(userId).get();

       usr.setBalance(currentBalance);

       this.repo.saveAndFlush(usr);
    }

}
