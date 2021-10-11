package paymentsservice.services;

import paymentsservice.models.Payment;

import java.util.Collection;

public interface PaymentsService {

    Collection<Payment> getAll();

}
