package echogaurd.greencoffe.repository;

import echogaurd.greencoffe.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccountRepository {

    private final EntityManager em;

    public Long save(Account account){
        em.persist(account);
        return account.getId();
    }

    public String loginCheck(String userId, String passwd) {
        String userid = "";
        try {
            TypedQuery<Account> m = em.createQuery("select m from Account m where m.userId = :userId", Account.class);
            Account account = m.setParameter("userId", userId).getSingleResult();
            if (!account.getPasswd().equals(passwd)) {
                throw new IllegalStateException("ID 혹은 Password가 일치하지 않습니다.");
            }
            userid = account.getUserId();
        }catch (NoResultException e){
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }
        return userid;
    }

    public Account findId(Long id){
        return em.find(Account.class, id);
    }

    public List<Account> findAll() {
        TypedQuery<Account> m = em.createQuery("select m from Account m", Account.class);
        return m.getResultList();
    }

    public List<Account> findByName(String name){
        TypedQuery<Account> m = em.createQuery("select m from Account m where m.name = :name", Account.class);
        return m.setParameter("name", name).getResultList();
    }

    public Account findByUserID(String userId){
        Account account = em.find(Account.class, userId);
        return account;
    }

}
