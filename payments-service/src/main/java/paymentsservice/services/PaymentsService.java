package paymentsservice.services;

import DTO.UserDTO;

import java.util.Collection;

public interface PaymentsService {

    Collection<UserDTO> getAll();

}
