package paymentsservice.services.api;
import com.angel.models.DTO.UserDTO;
import paymentsservice.models.Payment;

public interface UsersService {

    UserDTO getUser(String userId);

    void changeBalance(String userId, Payment payment);
}
