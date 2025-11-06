package com.medina.eva03.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    // Maneja la URL raíz (http://localhost:8081/)
    @GetMapping("/")
    public String index() {
        return "index"; // Esto buscará la plantilla 'index.html'
    }
}