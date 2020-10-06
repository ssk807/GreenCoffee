package echogaurd.greencoffe.service;

import echogaurd.greencoffe.controller.AccountForm;
import echogaurd.greencoffe.controller.LoginForm;
import echogaurd.greencoffe.domain.Account;
import echogaurd.greencoffe.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public String login(String _userId, String _passwd) {
        String userId = _userId;
        String passwd = _passwd;
        return accountRepository.loginCheck(userId, passwd);
    }

    public List<Account> findMembers(){
        return accountRepository.findAll();
    }

    public Account findOne(Long id) {
        return accountRepository.findId(id);
    }


    private void validateDuplicateMember(Account account) {
        List<Account> findAccounts = accountRepository.findByName(account.getUserId());
        if (!findAccounts.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
