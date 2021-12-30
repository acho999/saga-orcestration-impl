package paymentsservice.services.api;
import com.angel.models.DTO.UserDTO;
import paymentsservice.models.Payment;

public interface UsersService {

    UserDTO getUser(String userId);

    void changeBalance(String userId, Payment payment);

    UserDTO createUser(UserDTO dto);

    void reverseUserBalance(String userId, double amount);
}
