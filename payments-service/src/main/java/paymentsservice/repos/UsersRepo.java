package paymentsservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paymentsservice.models.User;

@Repository
public interface UsersRepo extends JpaRepository<User, String> {

}
