package echogaurd.greencoffe.repository;

import echogaurd.greencoffe.domain.Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Repository
@RequiredArgsConstructor
public class ManagerRepository {

    private final EntityManager em;

    public Long save(Manager manager) {

        em.persist(manager);
        return manager.getId();
    }

    public Long loginCheck(String userId, String passwd) {
        try {
            TypedQuery<Manager> m = em.createQuery("select m from Manager m where m.userId = :userId", Manager.class);
            Manager userId1 = m.setParameter("userId", userId).getSingleResult();
            if (userId1.getPasswd() != passwd) {
                throw new IllegalStateException("ID 혹은 Password가 일치하지 않습니다");
            } else {
                return userId1.getId();
            }
        } catch (NoResultException e) {
            throw new IllegalStateException("존재하지 않는 ID입니다.");
        }
    }

}
