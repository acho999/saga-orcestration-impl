package paymentsservice.services.impl;

import com.angel.models.DTO.UserDTO;
import com.angel.saga.logging.CustomLogging;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import paymentsservice.exceptions.NotFoundException;
import paymentsservice.models.Payment;
import paymentsservice.models.User;
import paymentsservice.repos.UsersRepo;
import paymentsservice.services.api.UsersService;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
@Transactional
public class UsersServiceImpl implements UsersService {


    private final ModelMapper mapper;
    private final UsersRepo repo;
    private double currentBalance;

    @Autowired
    public UsersServiceImpl(ModelMapper mapper, UsersRepo repo) {
        this.mapper = mapper;
        this.repo = repo;
    }

    @Override
    public UserDTO createUser(UserDTO dto) {

        if (Objects.isNull(dto)) {
            throw new IllegalArgumentException("Request body can not be null!");
        }

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
    public UserDTO getUser(String userId) {
        if (userId.isEmpty() || Objects.isNull(userId)) {
            throw new IllegalArgumentException("UserId can not be null or empty string!");
        }
        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Optional<User> usr = this.repo.findById(userId);
        if (usr.isEmpty()) {
            throw new NotFoundException("User not found!");
        }
        UserDTO dto = mapper.map(usr.get(), UserDTO.class);
        dto.setPayments(usr.get().getUserPayments());

        return dto;
    }

    @Override
    public void changeBalance(String userId, Payment payment) {

        if (userId.isEmpty() || Objects.isNull(userId) || Objects.isNull(payment)) {
            throw new IllegalArgumentException(
                "UserId can not be null or empty string. Payment can not be null!");
        }

        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Optional<User> user = this.repo.findById(userId);

        if (user.isEmpty()) {
            throw new NotFoundException("User not found!");
        }

        User usr = user.get();

        usr.getUserPayments().add(payment);

        this.currentBalance = usr.getBalance();

        double newBalance = usr.getBalance() - payment.getAmount();

        CustomLogging.log(UsersServiceImpl.class, "before reverse" + " " + newBalance);

        usr.setBalance(newBalance);

        this.repo.saveAndFlush(usr);

    }

    @Override
    public void reverseUserBalance(String userId, double amount) {

        Optional<User> user = this.repo.findById(userId);

        if (user.isEmpty()) {
            throw new NotFoundException("User not found!");
        }

        User usr = user.get();

        usr.setBalance(currentBalance);

        CustomLogging.log(UsersServiceImpl.class, "after reverse" + " " + usr.getBalance());

        this.repo.saveAndFlush(usr);
    }

}
