package paymentsservice.services.api;

import com.angel.models.DTO.PaymentRequestDTO;
import com.angel.models.DTO.UserDTO;
import paymentsservice.models.Payment;

import java.util.Collection;

public interface PaymentsService {

    Payment savePayment(String userId, PaymentRequestDTO payment);

    boolean reversePayment(String userId, String paymentId);

    Payment createPayment(PaymentRequestDTO payment);

    Payment getPayment(String id);
}
