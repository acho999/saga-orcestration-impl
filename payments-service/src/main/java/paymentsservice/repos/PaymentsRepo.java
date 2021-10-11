package paymentsservice.repos;

import paymentsservice.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsRepo extends JpaRepository<Payment, String> {

}
