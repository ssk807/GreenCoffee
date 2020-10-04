package echogaurd.greencoffe.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class AccountForm {

    @NotEmpty(message = "회원 이름은 필수랍니다*^^*")
    private String name;

    @NotEmpty(message = "아이디도 필수~")
    private String userId;
    @NotEmpty(message = "비밀번호도 필수*^-^*~")
    private String passwd;

    private String city;
    private String street;
    private String zipcode;
}
