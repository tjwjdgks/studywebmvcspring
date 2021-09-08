package me.seo.studywebmvc.controller;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

@Controller
public class FileController {

    @Autowired
    public ResourceLoader resourceLoader;

    @GetMapping("/file")
    public String fileUpLoadForm(Model model){
        return "/files/multiform";
    }

    @PostMapping("/file")
    public String fileUpLoad(@RequestParam MultipartFile file, RedirectAttributes attributes){
        // 스토리지에 save
        String message = file.getOriginalFilename() + "is uploaded";
        attributes.addFlashAttribute("message" , message);
        return "redirect:/file";
    }

    @GetMapping("/file/{filename}")
    //@ResponseBody // <- 줘도 되고 안주어도 가능 ResponseEntity에서 응답 본문을 작성했기 때문에 안붙여도 상관 없다
    public ResponseEntity<Resource> fileDownLoad(@PathVariable String filename) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + filename);
        File file = resource.getFile();

        // 파일 type 알아내기 tika 라이브러리
        Tika tika = new Tika();
        String mediaType = tika.detect(file);

        // header는 문자열
        return ResponseEntity.ok() // 상태코드
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachement; filename=\"" + resource.getFilename() + "\"") // 파일이름
                .header(HttpHeaders.CONTENT_TYPE, mediaType) // 파일 type
                .header(HttpHeaders.CONTENT_LENGTH, file.length()+"") // 파일 길이
                .body(resource);

    }
}
