package me.seo.studywebmvc.controller;

import me.seo.studywebmvc.dto.UserForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
//@SessionAttributes("userForm") // 이 이름에 해당하는 modelAttribute를 넣어 준다 model.addAttribute("userForm",userForm);
@SessionAttributes("multiForm")
public class FormController {

    @GetMapping("/form/posting")
    public String formPosting(Model model){
        UserForm userForm = new UserForm();
        userForm.setId(1);
        userForm.setName("default");
        model.addAttribute("userForm",userForm);
        return "/form/posting";
    }
    // redirect prefix post redirect get (prg
    // Post 이후에 브라우저를 리프래시 하더라도 폼 서브밋이 발생하지 않도록 하는 패턴


    @PostMapping("/postings")
    public String getForm(@Validated @ModelAttribute UserForm userForm, BindingResult bindingResult,Model model){
        if(bindingResult.hasErrors()){

            return "/form/posting";
        }


        List<UserForm> formList = new ArrayList<>();
        // db 사용시 값 저장
        formList.add(userForm);
        model.addAttribute("formList",formList);
        return "redirect:/form/list";
    }

    @GetMapping("/form/list")
    public String getFromList(Model model){
        // db 사용시 읽어 올 값
        UserForm userForm = new UserForm();
        userForm.setName("db");
        userForm.setId(1);

        List<UserForm> list = new ArrayList<>();
        list.add(userForm);

        model.addAttribute("formList",list);
        return "/form/list";
    }

    // session 예시
    @GetMapping("/form/session")
    public String getFromS(Model model, HttpSession httpSession , SessionStatus sessionStatus){

        UserForm userForm = new UserForm();
        userForm.setId(1);
        userForm.setName("default");
        // 특정한 폼처리가 끝났을 때 sessionStatus을 통해 처리 완료를 할려 줄 수 있음
        // sessionStatus.setComplete(); // 폼 처리 끝나고 세션을 비울 때 사용한다
        model.addAttribute("userForm",userForm);
        httpSession.setAttribute("userForm",userForm);
        return "/form/posting";
    }
    // multiform
    @GetMapping("/form/name")
    public String getMutiName(Model model){
        model.addAttribute("multiForm",new UserForm());
        return "/form/name";
    }
    @PostMapping("/form/name")
    public String postMutiName(@Validated @ModelAttribute UserForm multiForm,BindingResult bindingResult){
        System.out.println(multiForm.getName());
        if(bindingResult.hasErrors())
            return "/form/name";

        return "redirect:/form/user/id";
    }
    @GetMapping("/form/user/id")
    public String getMutiId(@ModelAttribute UserForm multiForm, Model model){
        System.out.println(multiForm.getName());
        model.addAttribute("multiForm",multiForm);
        return "/form/userid";
    }
    @PostMapping("/form/user/id")
    public String posttMutiId(@Validated @ModelAttribute UserForm multiForm,BindingResult bindingResult,SessionStatus sessionStatus){
        if(bindingResult.hasErrors())
            return "/form/userid";
        sessionStatus.setComplete();
        return "redirect:/form/list";
    }
}
