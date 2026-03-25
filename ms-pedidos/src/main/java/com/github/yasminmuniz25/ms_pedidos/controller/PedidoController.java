package com.github.yasminmuniz25.ms_pedidos.controller;


import com.github.yasminmuniz25.ms_pedidos.dto.PedidoDto;
import com.github.yasminmuniz25.ms_pedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoDto>> getAllPedidos(){
        List<PedidoDto> list = pedidoService.findAllPedidos();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity <PedidoDto> getPedido(@PathVariable Long id){

        PedidoDto pedidoDto = pedidoService.findPedidoById(id);
        return ResponseEntity.ok(pedidoDto);
    }
}
