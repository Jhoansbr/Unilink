package com.example.Unilink.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirectToInicio() {
        return "landing-page/index";
    }
     @GetMapping("/entrar")
    public String redirectlogin() {
        return "landing-page/login";
    }
}