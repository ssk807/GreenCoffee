package echogaurd.greencoffe.repository;

import echogaurd.greencoffe.domain.Cafe;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CafeRepository {
    private final EntityManager em;

    public void save(Cafe cafe) {
        try{
            em.persist(cafe);
        }
        catch(DataIntegrityViolationException e){
            throw new IllegalStateException("존재하는 카페ID 입니다.");
        }
    }
}
