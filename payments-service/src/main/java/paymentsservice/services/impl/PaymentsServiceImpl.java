package paymentsservice.services.impl;

import com.angel.models.DTO.UserDTO;
import paymentsservice.repos.PaymentsRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paymentsservice.services.api.PaymentsService;

import java.util.Collection;

@Service
@Transactional
public class PaymentsServiceImpl implements PaymentsService {

    @Autowired
    private PaymentsRepo repo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Collection<UserDTO> getAll() {
        return null;
    }

    @Override
    public boolean savePayment() {
        return false;
    }

    @Override
    public UserDTO getUser() {
        return null;
    }


}
