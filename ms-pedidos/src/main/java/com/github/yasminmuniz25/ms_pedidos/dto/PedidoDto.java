package com.github.yasminmuniz25.ms_pedidos.dto;

import com.github.yasminmuniz25.ms_pedidos.entities.ItemDoPedido;
import com.github.yasminmuniz25.ms_pedidos.entities.Pedido;
import com.github.yasminmuniz25.ms_pedidos.entities.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PedidoDto {

    private Long id;

    @NotBlank(message = "Nome requerido")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 a 100 caracteres")
    private String nome;

    //valida o CPF
    //@CPF(message = "Informe um CPF válido")
    @NotBlank(message = "CPF requerido")
    @Size(min = 11, max = 11, message = "O CPF deve ter 11 caracteres")
    private String cpf;

    //pega data automático
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    private Status status;

    private BigDecimal valorTotal;
    private List<@Valid ItemDoPedidoDto> itens = new ArrayList<>();

    public PedidoDto(Pedido pedido){
        id = pedido.getId();
        nome = pedido.getNome();
        cpf = pedido.getCpf();
        data = pedido.getData();
        status = pedido.getStatus();
        valorTotal = pedido.getValorTotal();

        //para os itens do pedido;
        for(ItemDoPedido item : pedido.getItens()){
            ItemDoPedidoDto itemDto = new ItemDoPedidoDto(item);
            itens.add(itemDto);
        }


    }

}
