package echogaurd.greencoffe.controller;

import com.sun.net.httpserver.Authenticator;
import echogaurd.greencoffe.domain.Account;
import echogaurd.greencoffe.domain.Address;
import echogaurd.greencoffe.service.AccountService;
import echogaurd.greencoffe.service.JwtService;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.dom4j.rule.Mode;
import org.json.simple.JSONObject;
import org.junit.runner.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.spi.DirStateFactory;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

@Controller
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @Autowired
    private JwtService jwtService;
    /*@GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new AccountForm());
        return "members/createMemberForm";
    }*/

    @PostMapping("/account/create")
    @ResponseBody
    public ResponseEntity<JSONObject> create(@RequestBody AccountForm accountForm) {

        Address address = new Address(accountForm.getCity(), accountForm.getStreet(), accountForm.getZipcode());

        Account account = new Account();
        account.setAddress(address);
        account.setName(accountForm.getName());
        account.setPasswd(accountForm.getPasswd());
        account.setUserId(accountForm.getUserId());
        account.setPoint(Integer.toUnsignedLong(0));
        try {
            accountService.join(account);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", "This is temp token");
            return new ResponseEntity<>(jsonObject, HttpStatus.OK);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<JSONObject> loginPage(@RequestBody LoginForm loginForm) {

        JSONObject jsonObject = new JSONObject();
        try {
            String userId = loginForm.getUserId();
            String passwd = loginForm.getPasswd();
            Account account = accountService.login(userId, passwd);

            String token = jwtService.create(account);
            jsonObject.put("jwt-auth-token", token);
            jsonObject.put("data", account);
            // 로그인 성공했다면 token 생성

            // 토큰 정보는 request의 헤더로 보내고 나머지는 Map에 담아주기.
            
            return new ResponseEntity<>(jsonObject, HttpStatus.ACCEPTED);
        }catch(IllegalStateException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}