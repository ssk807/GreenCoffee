package echogaurd.greencoffe.controller;

import echogaurd.greencoffe.domain.Manager;
import echogaurd.greencoffe.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.Media;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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

    @GetMapping(value = "/getQRImage", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> getQRimage() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(IOUtils
                .toByteArray(new FileInputStream(new File("src/main/resources/image/image.jpg"))),
                headers,
                HttpStatus.CREATED);
    }
}
