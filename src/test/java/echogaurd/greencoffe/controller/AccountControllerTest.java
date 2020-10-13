package echogaurd.greencoffe.controller;

import echogaurd.greencoffe.domain.Account;
import echogaurd.greencoffe.repository.AccountRepository;
import echogaurd.greencoffe.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JsonbTester;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AccountControllerTest {

    @Autowired
    AccountController accountController;

    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;

    @Test
    public void account() throws Exception{
        //given
        AccountForm account1 = new AccountForm();
        account1.setUserId("ssk807");
        account1.setPasswd("123");
        account1.setName("김성수");

        AccountForm account2 = new AccountForm();
        account2.setUserId("ssk807");
        account2.setPasswd("123");
        account2.setName("김성수");

        //when
        ResponseEntity<JSONObject> jsonObjectResponseEntity = accountController.create(account1);
        ResponseEntity<JSONObject> jsonObjectResponseEntity1 = accountController.create(account2);
        //then
        //Assert.assertEquals(jsonObjectResponseEntity.getStatusCode(), "200");
        Assert.assertEquals(jsonObjectResponseEntity1.getStatusCode(), "500");
    }
}