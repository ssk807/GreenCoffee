package echogaurd.greencoffe.controller;

import echogaurd.greencoffe.domain.Manager;
import echogaurd.greencoffe.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.Media;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

@Controller
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @GetMapping("/managerLogin")
    @ResponseBody
    public String login(@RequestBody ManagerForm managerform) {
        Manager manager = new Manager();
        manager.setUserId(managerform.getUserId());
        manager.setPasswd(managerform.getPasswd());

        managerService.join(manager);
        managerService.login(managerform.getUserId(), managerform.getPasswd());
        return "로그인 완료";
    }

    @GetMapping(value = "/getQRImage", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> getQRimage() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(IOUtils
                .toByteArray(new FileInputStream(new File("src/main/resources/image/frame.png"))),
                headers,
                HttpStatus.CREATED);
    }
    @GetMapping(value = "getQRImageTest",produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] redirectQR(){
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();

        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "http://api.qrserver.com/v1/create-qr-code/?";
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("data","http://localhost:8080/getQRImage")
                .queryParam("size","100x100")
                .build(false);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        // HttpEntity란?
        // Http 관련 알아야겠다..

        ResponseEntity<byte[]> qrcode = restTemplate.exchange(builder.toString(), HttpMethod.GET, entity, byte[].class);

        return qrcode.getBody();

    }
}