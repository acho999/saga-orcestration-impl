package paymentsservice.services.api;

import com.angel.models.DTO.UserDTO;

import java.util.Collection;

public interface PaymentsService {

    Collection<UserDTO> getAll();

    boolean savePayment();

    UserDTO getUser();



}
