package paymentsservice.services.api;

import com.angel.models.DTO.UserDTO;
import paymentsservice.models.Payment;

import java.util.Collection;

public interface PaymentsService {

    boolean savePayment(String userId, Payment payment);

    boolean reversePayment(String userId, String paymentId);
}
