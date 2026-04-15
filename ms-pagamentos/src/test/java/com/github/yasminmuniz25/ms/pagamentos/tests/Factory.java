package com.github.yasminmuniz25.ms.pagamentos.tests;

import com.github.yasminmuniz25.ms.pagamentos.entities.Pagamento;
import com.github.yasminmuniz25.ms.pagamentos.entities.Status;

import java.math.BigDecimal;

public class Factory {
    public static Pagamento createPagamento(){

        Pagamento pagamento = new Pagamento(
                1L, BigDecimal.valueOf(32.25), "Brienne de Tarth",
                "3654789650152365", "07/15", "354", Status.CRIADO, 1l );
        return pagamento;
    }

    public static Pagamento createPagamentoSemId(){
        Pagamento pagamento = createPagamento();
        pagamento.setId(null);
        return pagamento;
    }
}
