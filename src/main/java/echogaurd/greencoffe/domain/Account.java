package echogaurd.greencoffe.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Account {
    @Id
    @GeneratedValue
    @Column(name = "ACCOUNT_ID")
    private Long id;
    private String name;

    @Column(unique = true, nullable = false)
    private String userId;

    @Column(nullable = false)
    private String passwd;

    private Long point;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "account")
    private List<Order> orderList = new ArrayList<>();

}
