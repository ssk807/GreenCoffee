package echogaurd.greencoffe.service;

import echogaurd.greencoffe.domain.Cafe;
import echogaurd.greencoffe.repository.CafeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CafeService {
    @Autowired
    private CafeRepository cafeRepository;

    public void save(Cafe _cafe){
        Cafe cafe = new Cafe();
        cafe.setId(_cafe.getId());
        cafe.setContent(_cafe.getContent());
        cafe.setFile(_cafe.getFile());
        cafe.setLatitude(_cafe.getLatitude());
        cafe.setLongitude(_cafe.getLongitude());
        cafe.setName(_cafe.getName());
        cafe.setUrl(_cafe.getUrl());

        cafeRepository.save(cafe);
    }
}
