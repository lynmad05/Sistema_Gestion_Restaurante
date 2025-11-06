package com.medina.eva03.service;

import com.medina.eva03.model.Mesa;
import java.util.List;

public interface MesaService {
    // Definición de métodos CRUD
    Mesa guardar(Mesa mesa);
    Mesa buscarPorId(Integer id);
    List<Mesa> listarTodos();
    void eliminar(Integer id);

    // Métodos de lógica de negocio (asignar/liberar)
    // Aunque la lógica esté en el controller, la persistencia pasa por aquí
}