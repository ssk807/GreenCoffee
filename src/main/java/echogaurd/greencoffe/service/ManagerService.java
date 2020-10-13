package echogaurd.greencoffe.service;

import echogaurd.greencoffe.domain.Manager;
import echogaurd.greencoffe.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;

    @Transactional
    public Long join(Manager manager) {
        return managerRepository.save(manager);
    }

    public Long login(String userId,String passwd) {
        return managerRepository.loginCheck(userId,passwd);
    }
}
