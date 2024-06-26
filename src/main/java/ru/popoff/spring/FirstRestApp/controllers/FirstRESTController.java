package ru.popoff.spring.FirstRestApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//@RestController //  @Controller + @ResponseBody над каждым методом
@RequestMapping("/api")
public class FirstRESTController {

    @ResponseBody
    @GetMapping("sayHello")
    public String sayHello() {
        return "Hello world";
    }
}