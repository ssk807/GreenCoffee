package echogaurd.greencoffe.service;

import echogaurd.greencoffe.controller.AccountForm;
import echogaurd.greencoffe.controller.LoginForm;
import echogaurd.greencoffe.domain.Account;
import echogaurd.greencoffe.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public Long join(Account account) {
        validateDuplicateMember(account);
        accountRepository.save(account);
        return account.getId();
    }

    public Account login(String _userId, String _passwd) {
        String userId = _userId;
        String passwd = _passwd;
       return accountRepository.loginCheck(userId, passwd);
    }

    @Transactional
    public int savePoint(Long _Id){
        Account account = accountRepository.findId(_Id);
        account.savePoint();
        System.out.println("account point : " + account.getPoint());
        return account.getPoint();
    }

    public List<Account> findMembers(){
        return accountRepository.findAll();
    }

    public Account findOne(Long id) {
        return accountRepository.findId(id);
    }


    private void validateDuplicateMember(Account account) {
        List<Account> findAccounts = accountRepository.findByUserID(account.getUserId());
        if (!findAccounts.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
