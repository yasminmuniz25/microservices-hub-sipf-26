package com.github.yasminmuniz25.ms.pagamentos.repositories;

import com.github.yasminmuniz25.ms.pagamentos.entities.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
