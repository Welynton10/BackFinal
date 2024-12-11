package com.pedidos.app.Services;


import com.pedidos.app.Models.PedidoModel;
import com.pedidos.app.Repositories.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PedidoService {

    final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional
    public PedidoModel save(PedidoModel pedidoModel) {
        return pedidoRepository.save(pedidoModel);
    }

    public List<PedidoModel> findAll() {
        return pedidoRepository.findAll();
    }

    public Optional<PedidoModel> findById(UUID id) {
        return pedidoRepository.findById(id);
    }

    @Transactional
    public void delete(UUID id) {
        pedidoRepository.deleteById(id);
    }
}