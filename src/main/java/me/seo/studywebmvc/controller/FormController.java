package me.seo.studywebmvc.controller;

import me.seo.studywebmvc.dto.UserForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
//@SessionAttributes("userForm") // 이 이름에 해당하는 modelAttribute를 넣어 준다 model.addAttribute("userForm",userForm);
@SessionAttributes("multiform")
public class FormController {

    /*
    @InitBinder("userForm") // 안에 문자 있는 경우  그 이름의 modelAttribute를 바인딩 받을 때만 사용 가능   // return void 여야 한다
    //WebDataBinder는 반드시 있어야한다, 모든 요청전에 initFormBinder 호출
    // 바인딩 설정, 포매터 설정, Vaildator 설정 가능
    public void initFormBinder(WebDataBinder webDataBinder){
        webDataBinder.setDisallowedFields("id"); // id를 바인딩 하지 않음 // 입력 받지 않을 필드 입력
        webDataBinder.setAllowedFields("name"); // 입력받고 싶은 필드 바인딩

    }

     */



    // 컨트롤러 안에서 공통적인 참고해야하는 모델 정보 라면
    // 모든 뷰에서 categories 참고 할때
    @ModelAttribute
    public void categories(Model model){
        model.addAttribute("categories", List.of("study","seminar","social"));
    }
    /* 이건도 가능
    @ModelAttribute("categories")
    public List<String> categories2(Model model){
        return List.of("study","seminar","social");
    }

     */



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
    // SessionAttribute 예시
    @GetMapping("/form/list")
    public String getFromList(Model model, @SessionAttribute LocalDateTime visitTime /*,HttpSession session*/){
        // flash example 예시
        // flashAttributes로 들어오는 값은 model에 들어 있다
        String name = (String) model.asMap().get("flash");
        System.out.println(name);


        // VisitTimeInterceptor에서 넣어 준 session 꺼낼 수 있음
        // http session도 가능
        //LocalDateTime visitTimeHttp = (LocalDateTime) session.getAttribute("visitTime");
        // db 사용시 읽어 올 값
        System.out.println(visitTime);
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
        // httpSession 가능
        //httpSession.setAttribute("userForm",userForm);
        return "/form/posting";
    }


    // multiform
    // @SessionAttributes의 파라미터와 같은 이름이 @ModelAttribute에 있을 경우 세션에 있는 객체를 가져온 후, 클라이언트로 전송받은 값을 설정합니다.

    @GetMapping("/form/name")
    public String getMutiName(Model model){
        model.addAttribute("multiform",new UserForm());
        return "/form/name";
    }

    @PostMapping("/form/name")
    public String postMutiName(@Validated @ModelAttribute(value="multiform") UserForm multiform,BindingResult bindingResult){
        System.out.println(multiform.getName());
        if(bindingResult.hasErrors())
            return "/form/name";

        return "redirect:/form/id";
    }
    @GetMapping("/form/id")
    public String getMutiId(@ModelAttribute(value = "multiform") UserForm multiform,Model model){
        System.out.println(multiform.getName());
        model.addAttribute("multiform",multiform);
        return "/form/userid";
    }

    @PostMapping("/form/id")
    public String posttMutiId(@Validated @ModelAttribute(value = "multiform")  UserForm multiform,BindingResult bindingResult,SessionStatus sessionStatus){
        if(bindingResult.hasErrors())
            return "/form/userid";
        sessionStatus.setComplete();
        return "redirect:/form/list";
    }

    // RedirectAttributes
    @ResponseBody
    @GetMapping("/form/redirectexample")
                                                     // Model model
    public String example(@PathVariable String name, RedirectAttributes attributes){
        // model의 경우
        // model.addAttribute("name", name);
        // spring webmvc 자동으로 model의 primitive type data는 query로 날라간다
        // ex) 예시 이런식으로.. return "redirect:/form/list?name={name}";
        // spring boot 경우 properties로 추가해줄 수 있음, 자동 아님

        // 원하는 값만 redirect로 보내고 싶을 경우
        // 명시 된 것들만 query 파라미터로 url 전달 된다
        // uri path에 붙기 때무에 String으로 변환이 가능해야한다
        attributes.addAttribute("name",name);
        return "redirect:/form/list";
    }

    //
    @GetMapping("/form/flashexample/{name}")
    public String example2(@PathVariable String name, RedirectAttributes attributes){
        attributes.addFlashAttribute("flash",name); // http session에 들어간다
        // redirect한 요청이 처리되면 data는 세션에서 제거된다
        // reidrect한 요청은 model로 들어온다
        return "redirect:/form/list";
    }
}
