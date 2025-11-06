package com.medina.eva03.service;

import com.medina.eva03.model.Usuario;
import java.util.List;

public interface UsuarioService {
    Usuario guardar(Usuario usuario);
    Usuario buscarPorId(Integer id);
    List<Usuario> listarTodos();
    void eliminar(Integer id);
}