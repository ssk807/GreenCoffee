package echogaurd.greencoffe.controller;

import echogaurd.greencoffe.domain.Account;
import echogaurd.greencoffe.domain.Address;
import echogaurd.greencoffe.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.dom4j.rule.Mode;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    /*@GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new AccountForm());
        return "members/createMemberForm";
    }*/

    @PostMapping("/account/create")
    @ResponseBody
    public String create(@RequestBody AccountForm accountForm, Model model) {

        Address address = new Address(accountForm.getCity(), accountForm.getStreet(), accountForm.getZipcode());

        Account account = new Account();
        account.setAddress(address);
        account.setName(accountForm.getName());
        account.setPasswd(accountForm.getPasswd());
        account.setUserId(accountForm.getUserId());


        accountService.join(account);

        return account.getName();
    }

    @PostMapping("/login")
    @ResponseBody
    public JSONObject loginPage(@RequestBody LoginForm loginForm) {

        JSONObject jsonObject = new JSONObject();
        try {
            String userId = loginForm.getUserId();
            String passwd = loginForm.getPasswd();
            accountService.login(userId, passwd);
            jsonObject.put("userId", userId);
        }catch(IllegalStateException e){
            e.printStackTrace();
        }
        return jsonObject;
    }

}