package com.medina.eva03.controller;

import com.medina.eva03.model.Usuario;
import com.medina.eva03.model.Rol; // Usar el Enum de Rol
import com.medina.eva03.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
@PreAuthorize("hasRole('ADMIN')") // TODAS las rutas en este Controller requieren ADMIN
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // 1. Mostrar lista de usuarios
    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "usuarios/lista-usuarios";
    }

    // 2. Formulario para nuevo usuario
    @GetMapping("/nuevo")
    public String nuevoUsuarioForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", Rol.values()); // Pasa todos los roles al formulario
        model.addAttribute("accion", "Crear");
        return "usuarios/formulario-usuario";
    }

    // 3. Formulario para editar usuario
    @GetMapping("/editar/{id}")
    public String editarUsuarioForm(@PathVariable Integer id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario != null) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("roles", Rol.values());
            model.addAttribute("accion", "Editar");
            // Borramos la contraseña para que no se muestre cifrada en el formulario
            usuario.setContrasena("");
            return "usuarios/formulario-usuario";
        }
        return "redirect:/usuarios";
    }

    // 4. Guardar usuario (Crea o Edita)
    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario, RedirectAttributes redirect) {
        usuarioService.guardar(usuario);
        redirect.addFlashAttribute("success", "Usuario guardado exitosamente.");
        return "redirect:/usuarios";
    }

    // 5. Eliminar usuario
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Integer id, RedirectAttributes redirect) {
        usuarioService.eliminar(id);
        redirect.addFlashAttribute("success", "Usuario eliminado exitosamente.");
        return "redirect:/usuarios";
    }
}