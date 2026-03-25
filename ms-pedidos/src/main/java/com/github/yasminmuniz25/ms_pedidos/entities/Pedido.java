package com.github.yasminmuniz25.ms_pedidos.entities;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column define características da coluna do BD
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 11)
    private String cpf;
    private LocalDate data;

    //garante que o campo status será armazenado como texto no banco (ex.: "CRIADO", "PAGO").
    @Enumerated(EnumType.STRING)
    private Status status;

    //valor calculado
    private BigDecimal valorTotal;


    //relacionamento
    @OneToMany(mappedBy = "pedido",
            cascade = CascadeType.ALL, orphanRemoval = true)
    // serve para controlar o comportamento dos filhos, qnd removidos da coleção
    //o JPA entende que esse objeto não deve mais existir no banco de dados.
    private List<ItemDoPedido> itens = new ArrayList<>();



    //implementando método calcularValorTotalDoPedido
    public void calcularValorTotalDoPedido(){
        this.valorTotal = this.itens.stream()
                .map(i -> i.getPrecoUnitario()
                        .multiply(BigDecimal.valueOf(i.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
