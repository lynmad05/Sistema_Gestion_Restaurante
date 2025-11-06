package com.medina.eva03.controller;


import com.medina.eva03.model.Cliente;
import com.medina.eva03.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    // RF1: Consultar clientes
    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        return "clientes/lista_clientes";
    }

    @GetMapping("/nuevo")
    public String nuevoClienteForm(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("accion", "Crear");
        return "clientes/formulario_cliente";
    }

    // RF1: Registrar clientes
    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute Cliente cliente) {
        clienteRepository.save(cliente); // La auditoría AOP se activa aquí
        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String editarClienteForm(@PathVariable Integer id, Model model) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de cliente inválido: " + id));
        model.addAttribute("cliente", cliente);
        model.addAttribute("accion", "Editar");
        return "clientes/formulario_cliente";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable Integer id) {
        clienteRepository.deleteById(id); // La auditoría AOP se activa aquí
        return "redirect:/clientes";
    }
}
