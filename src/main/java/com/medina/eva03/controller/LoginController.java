package com.medina.eva03.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        // Simplemente devuelve el nombre de la plantilla de login
        return "login";
    }
}