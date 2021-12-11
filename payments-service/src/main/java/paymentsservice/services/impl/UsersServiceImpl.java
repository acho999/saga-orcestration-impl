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

    public UserDTO createUser(UserDTO dto){

        User user = new User();
        user.setUserPayments(new ArrayList<Payment>());
        user.setBalance(dto.getBalance());
        this.repo.saveAndFlush(user);
        UserDTO userDTO = dto;
        userDTO.setUserId(user.getId());
        System.out.println(userDTO.getUserId());
        return userDTO;

    }

    public UserDTO getUser(String userId){
        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDTO dto = mapper.map(this.repo.getById(userId), UserDTO.class);

        return dto;
    }

    public void changeBalance(String userId, Payment payment){
        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        User user = this.mapper.map(this.getUser(userId), User.class);

        user.getUserPayments().add(payment);

        user.setBalance(user.getBalance() - payment.getAmount());

        this.repo.saveAndFlush(user);

    }

}
