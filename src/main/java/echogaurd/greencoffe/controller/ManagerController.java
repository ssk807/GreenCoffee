package echogaurd.greencoffe.controller;

import echogaurd.greencoffe.domain.Account;
import echogaurd.greencoffe.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(value="/manager")
public class ManagerController {

    private final AccountService accountService;

/*    @GetMapping(value = "/getQRImage", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> getQRimage() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(IOUtils
                .toByteArray(new FileInputStream(new File("src/main/resources/image/frame.png"))),
                headers,
                HttpStatus.CREATED);
    }*/
    @GetMapping(value = "/getQRImage",produces = MediaType.IMAGE_PNG_VALUE)
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

        ResponseEntity<byte[]> qrcode = restTemplate.exchange(builder.toString(), HttpMethod.GET, entity, byte[].class);

        return qrcode.getBody();

    }
}