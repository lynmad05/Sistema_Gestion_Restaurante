package com.medina.eva03.controller;

import com.medina.eva03.model.EstadoMesa;
import com.medina.eva03.model.Mesa;
import com.medina.eva03.service.MesaService; // ¡CORRECCIÓN CLAVE! Usar la capa de servicio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;

@Controller
@RequestMapping("/mesas")
public class MesaController {

    // CORRECCIÓN: Inyectar MesaService en lugar de MesaRepository
    @Autowired
    private MesaService mesaService;

    /**
     * RF3: Muestra la lista de mesas con su estado actual.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')") // Restricción de Seguridad
    public String listarMesas(Model model) {
        model.addAttribute("mesas", mesaService.listarTodos());
        return "mesas/lista-mesas";
    }

    /**
     * Muestra el formulario para crear una nueva mesa.
     */
    @GetMapping("/nuevo")
    @PreAuthorize("hasRole('ADMIN')")
    public String nuevaMesaForm(Model model) {
        Mesa nuevaMesa = new Mesa();
        // Asume que la entidad Mesa usa el Enum EstadoMesa.DISPONIBLE por defecto
        model.addAttribute("mesa", nuevaMesa);
        model.addAttribute("accion", "Crear");
        return "mesas/formulario_mesa";
    }

    /**
     * Guarda o actualiza una mesa.
     */
    @PostMapping("/guardar")
    @PreAuthorize("hasRole('ADMIN')")
    public String guardarMesa(@ModelAttribute Mesa mesa, RedirectAttributes redirect) {
        // Validación de estado: Si viene como String del formulario, debe mapearse a Enum.
        // Asumimos que el @ModelAttribute lo maneja correctamente o que es un campo no editable.
        mesaService.guardar(mesa); // La auditoría AOP se activa aquí
        redirect.addFlashAttribute("success", "Mesa guardada exitosamente.");
        return "redirect:/mesas";
    }

    /**
     * Muestra el formulario para editar una mesa existente.
     */
    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editarMesaForm(@PathVariable Integer id, Model model, RedirectAttributes redirect) {
        Mesa mesa = mesaService.buscarPorId(id);

        if (mesa == null) {
            redirect.addFlashAttribute("error", "Mesa no encontrada.");
            return "redirect:/mesas";
        }

        model.addAttribute("mesa", mesa);
        model.addAttribute("accion", "Editar");
        return "mesas/formulario_mesa";
    }

    /**
     * Elimina una mesa.
     */
    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String eliminarMesa(@PathVariable Integer id, RedirectAttributes redirect) {
        mesaService.eliminar(id); // La auditoría AOP se activa aquí
        redirect.addFlashAttribute("success", "Mesa eliminada exitosamente.");
        return "redirect:/mesas";
    }

    // --- LÓGICA RF2: ASIGNAR MESA (Desde /mesas/asignar/{id}) ---
    @GetMapping("/asignar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String asignarMesa(@PathVariable("id") Integer id, RedirectAttributes redirect) {
        Mesa mesa = mesaService.buscarPorId(id);

        if (mesa == null) {
            redirect.addFlashAttribute("error", "Mesa no encontrada.");
        } else if (mesa.getEstado() == EstadoMesa.DISPONIBLE) {
            mesa.setEstado(EstadoMesa.OCUPADA);
            mesaService.guardar(mesa);
            redirect.addFlashAttribute("success", "Mesa " + mesa.getNumero() + " asignada y marcada como OCUPADA.");
        } else {
            redirect.addFlashAttribute("error", "La mesa " + mesa.getNumero() + " no está disponible para ser asignada.");
        }

        return "redirect:/mesas";
    }

    // --- LÓGICA RF2: LIBERAR MESA (Desde /mesas/liberar/{id}) ---
    @GetMapping("/liberar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String liberarMesa(@PathVariable("id") Integer id, RedirectAttributes redirect) {
        Mesa mesa = mesaService.buscarPorId(id);

        if (mesa == null) {
            redirect.addFlashAttribute("error", "Mesa no encontrada.");
        } else if (mesa.getEstado() == EstadoMesa.OCUPADA) {
            mesa.setEstado(EstadoMesa.DISPONIBLE);
            mesaService.guardar(mesa);
            redirect.addFlashAttribute("success", "Mesa " + mesa.getNumero() + " liberada y marcada como DISPONIBLE.");
        } else {
            redirect.addFlashAttribute("error", "La mesa " + mesa.getNumero() + " no estaba ocupada para ser liberada.");
        }

        return "redirect:/mesas";
    }

    // NOTA: Se elimina el método duplicado @GetMapping("/cambiar_estado/{id}/{nuevoEstado}")
}