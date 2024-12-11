package com.pedidos.app.Repositories;


import com.pedidos.app.Models.PedidoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PedidoRepository extends
        JpaRepository<PedidoModel, UUID> {

}