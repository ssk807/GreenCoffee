package echogaurd.greencoffe.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CafeForm {
    private String name;
    private String content;
    private String url;

    private Float latitude;
    private Float longitude;

    private byte[] file;
}
