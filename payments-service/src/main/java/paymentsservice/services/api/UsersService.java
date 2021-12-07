package paymentsservice.services.api;
import com.angel.models.DTO.UserDTO;

public interface UsersService {

    UserDTO getUser(String userId);

    void changeBalance(String userId, double amount);
}
