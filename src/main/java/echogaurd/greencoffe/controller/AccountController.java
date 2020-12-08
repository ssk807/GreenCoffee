package echogaurd.greencoffe.controller;

import com.sun.net.httpserver.Authenticator;
import echogaurd.greencoffe.domain.Account;
import echogaurd.greencoffe.domain.Address;
import echogaurd.greencoffe.service.AccountService;
import echogaurd.greencoffe.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.dom4j.rule.Mode;
import org.json.simple.JSONObject;
import org.junit.runner.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import javax.naming.spi.DirStateFactory;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@Controller
@RequiredArgsConstructor
@RequestMapping(value="/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<JSONObject> create(@RequestBody AccountForm accountForm) {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Address address = new Address(accountForm.getCity(), accountForm.getStreet(), accountForm.getZipcode());

        Account account = new Account();
        account.setAddress(address);
        account.setName(accountForm.getName());
        account.setPasswd(accountForm.getPasswd());
        account.setUserId(accountForm.getUserId());
        account.setPoint(0);
        account.setAuth(0);
        try {
            accountService.join(account);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", "This is temp token");
            stopWatch.stop();
            System.out.println("Create Query Time : " + stopWatch.getTotalTimeMillis());
            return new ResponseEntity<>(jsonObject, HttpStatus.OK);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value ="로그인",notes="로그인")
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<JSONObject> loginPage(@ApiParam(value="회원 정보",required = true) @RequestBody LoginForm loginForm) {

        JSONObject jsonObject = new JSONObject();
        try {
            String userId = loginForm.getUserId();
            String passwd = loginForm.getPasswd();
            Account account = accountService.login(userId, passwd);

            String token = jwtService.create(account);
            jsonObject.put("jwt-auth-token", token);
            jsonObject.put("key", account.getAuth());
            // 로그인 성공했다면 token 생성

            // 토큰 정보는 request의 헤더로 보내고 나머지는 Map에 담아주기.
            
            return new ResponseEntity<>(jsonObject, HttpStatus.ACCEPTED);
        }catch(IllegalStateException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/savePoint")
    @ResponseBody
    public ResponseEntity<JSONObject> savePoint(@RequestBody TokenForm token){
        try {
            Map<String, Object> stringObjectMap = jwtService.get(token.getToken());

            Object object = stringObjectMap.get("ID");
            Long id = Long.parseLong(object.toString());

            int ret = accountService.savePoint(id);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("point", ret);

            return new ResponseEntity<>(jsonObject,HttpStatus.ACCEPTED);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}