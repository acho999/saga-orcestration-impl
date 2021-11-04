package paymentsservice.services;

import DTO.PaymentDTO;

import java.util.Collection;

public interface PaymentsService {

    Collection<PaymentDTO> getAll();

}
