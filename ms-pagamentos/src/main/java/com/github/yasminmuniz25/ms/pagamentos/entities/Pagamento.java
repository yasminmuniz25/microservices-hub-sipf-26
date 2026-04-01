package com.github.yasminmuniz25.ms.pagamentos.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_pagamento")
public class Pagamento {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
