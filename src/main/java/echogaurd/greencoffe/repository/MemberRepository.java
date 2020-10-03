package echogaurd.greencoffe.repository;

import echogaurd.greencoffe.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }

    public Member findId(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        TypedQuery<Member> m = em.createQuery("select m from Member m", Member.class);
        return m.getResultList();
    }

    public List<Member> findByName(String name){
        TypedQuery<Member> m = em.createQuery("select m from Member m where m.name = :name", Member.class);
        return m.setParameter("name", name).getResultList();
    }


}
