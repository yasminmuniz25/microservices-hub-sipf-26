package com.github.yasminmuniz25.ms_pedidos.service;


import com.github.yasminmuniz25.ms_pedidos.dto.ItemDoPedidoDto;
import com.github.yasminmuniz25.ms_pedidos.dto.PedidoDto;
import com.github.yasminmuniz25.ms_pedidos.entities.ItemDoPedido;
import com.github.yasminmuniz25.ms_pedidos.entities.Pedido;
import com.github.yasminmuniz25.ms_pedidos.entities.Status;
import com.github.yasminmuniz25.ms_pedidos.exceptions.ResourceNotFoundException;
import com.github.yasminmuniz25.ms_pedidos.repositories.ItemDoPedidoRepository;
import com.github.yasminmuniz25.ms_pedidos.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PedidoService {

    //é uma anotação do Spring que diz ao framework para injetar
    // automaticamente uma instância de PedidoRepository nessa classe.
    @Autowired
    private PedidoRepository pedidoRepository;
    //fornece métodos prontos para acessar o banco de dados

    private ItemDoPedidoRepository itemDoPedidoRepository;


    @Transactional(readOnly = true)
    public List<PedidoDto> findAllPedidos(){
        return pedidoRepository.findAll().
                stream().map(PedidoDto::new).toList();
    }

    @Transactional(readOnly = true)
    //readOnly = true-  otimiza a operação, pois o banco sabe que não haverá alterações (apenas leitura).
    public PedidoDto findPedidoById(Long id){
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado. ID: "+ id)
        );
        return new PedidoDto(pedido);
    }

    public PedidoDto savePedido(PedidoDto pedidoDto){
        Pedido pedido = new Pedido();
        pedido.setData(LocalDate.now());
        pedido.setStatus(Status.CRIADO);
        mapDtoToPedido(pedidoDto, pedido);
        pedido.calcularValorTotalDoPedido();
        pedido = pedidoRepository.save(pedido);
        return new PedidoDto(pedido);
    }

    public void mapDtoToPedido (PedidoDto pedidoDto, Pedido pedido){

        pedido.setNome(pedidoDto.getNome());
        pedido.setCpf(pedidoDto.getCpf());

        for(ItemDoPedidoDto itemDto : pedidoDto.getItens()){
            ItemDoPedido itemPedido = new ItemDoPedido();
            itemPedido.setQuantidade(itemDto.getQuantidade());
            itemPedido.setDescricao(itemDto.getDescricao());
            itemPedido.setPrecoUnitario(itemDto.getPrecoUnitario());
            itemPedido.setPedido(pedido);
            pedido.getItens().add(itemPedido);
        }
    }



}
