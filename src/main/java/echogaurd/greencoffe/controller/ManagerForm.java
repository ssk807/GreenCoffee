package echogaurd.greencoffe.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class ManagerForm {
    @NotEmpty(message = "Id를 입력해주세요.")
    private String userId;

    @NotEmpty(message = "Password를 입력해주세요.")
    private String passwd;
}
