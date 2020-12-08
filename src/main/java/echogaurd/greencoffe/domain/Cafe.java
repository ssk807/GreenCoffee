package echogaurd.greencoffe.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.awt.*;

@Entity
@Getter @Setter
public class Cafe {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String content;
    private String url;

    private Float latitude;
    private Float longitude;

    private byte[] file;

}
