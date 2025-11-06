package com.medina.eva03.service.impl;

import com.medina.eva03.model.Mesa;
import com.medina.eva03.repository.MesaRepository;
import com.medina.eva03.service.MesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // ¡IMPORTANTE! Marca esta clase como un Bean de Servicio
public class MesaServiceImpl implements MesaService {

    @Autowired
    private MesaRepository mesaRepository;

    @Override
    public Mesa guardar(Mesa mesa) {
        return mesaRepository.save(mesa);
    }

    @Override
    public Mesa buscarPorId(Integer id) {
        // Usa orElse(null) o maneja el Optional según tu preferencia
        return mesaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Mesa> listarTodos() {
        return mesaRepository.findAll();
    }

    @Override
    public void eliminar(Integer id) {
        mesaRepository.deleteById(id);
    }
}