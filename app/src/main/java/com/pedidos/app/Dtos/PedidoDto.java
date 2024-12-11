package com.pedidos.app.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PedidoDto {

    private UUID id;

    @NotBlank
    @Size(min = 2, max = 50, message = "A descrição deve ter entre 2 e 50 caracteres.")
    private String descricao;

    @NotNull(message = "O valor do pedido não pode ser nulo.")
    @Positive(message = "O valor deve ser positivo.")
    private BigDecimal valor;

    @NotBlank(message = "O status é obrigatório.")
    private String status = "Pendente";

    @NotNull(message = "O ID do cliente é obrigatório.")
    private UUID clienteId;
}