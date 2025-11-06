package com.medina.eva03.controller;


import com.medina.eva03.repository.BitacoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.data.domain.Sort;

@Controller
@RequestMapping("/bitacora")
public class BitacoraController {

    @Autowired
    private BitacoraRepository bitacoraRepository;

    @GetMapping
    public String listarBitacora(Model model) {
        // Ordena por fechaHora descendente para ver las últimas acciones
        model.addAttribute("registros", bitacoraRepository.findAll(Sort.by(Sort.Direction.DESC, "fechaHora")));
        return "bitacora/lista_bitacora";
    }
}