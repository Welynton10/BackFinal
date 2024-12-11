package com.pedidos.app.Controllers;

import com.pedidos.app.Dtos.ClienteDto;
import com.pedidos.app.Models.ClienteModel;
import com.pedidos.app.Models.PedidoModel;
import com.pedidos.app.Services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/salvar")
    public ResponseEntity<Object> salvarCliente(@RequestBody @Valid ClienteDto clienteDto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> mensagensDeErro = result.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(mensagensDeErro);
        }

        var clienteModel = new ClienteModel();
        BeanUtils.copyProperties(clienteDto, clienteModel);

        List<PedidoModel> pedidos = clienteDto.getPedidos().stream().map(pedidoDto -> {
            PedidoModel pedidoModel = new PedidoModel();
            BeanUtils.copyProperties(pedidoDto, pedidoModel);
            pedidoModel.setCliente(clienteModel);
            return pedidoModel;
        }).collect(Collectors.toList());

        clienteModel.setPedidos(pedidos);

        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(clienteModel));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ClienteModel>> listarClientes() {
        return ResponseEntity.ok(clienteService.findAll());
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Object> editarCliente(@PathVariable UUID id, @RequestBody @Valid ClienteDto clienteDto) {
        Optional<ClienteModel> clienteOptional = clienteService.findById(id);

        if (!clienteOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
        }

        var clienteModel = clienteOptional.get();
        BeanUtils.copyProperties(clienteDto, clienteModel, "id");

        List<PedidoModel> pedidos = clienteDto.getPedidos().stream().map(pedidoDto -> {
            PedidoModel pedidoModel = new PedidoModel();
            BeanUtils.copyProperties(pedidoDto, pedidoModel);
            pedidoModel.setCliente(clienteModel);
            return pedidoModel;
        }).collect(Collectors.toList());

        clienteModel.setPedidos(pedidos);

        return ResponseEntity.ok(clienteService.save(clienteModel));
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Object> buscarClientePorId(@PathVariable UUID id) {
        Optional<ClienteModel> clienteOptional = clienteService.findById(id);

        if (!clienteOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
        }

        return ResponseEntity.ok(clienteOptional.get());
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Object> deletarCliente(@PathVariable UUID id) {
        Optional<ClienteModel> clienteOptional = clienteService.findById(id);

        if (!clienteOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
        }

        clienteService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Cliente removido com sucesso.");
    }
}