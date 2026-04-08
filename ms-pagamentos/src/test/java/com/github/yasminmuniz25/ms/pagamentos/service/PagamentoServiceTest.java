package com.github.yasminmuniz25.ms.pagamentos.service;

import com.github.yasminmuniz25.ms.pagamentos.exceptions.ResourceNotFoundException;
import com.github.yasminmuniz25.ms.pagamentos.repositories.PagamentoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PagamentoServiceTest {

    @Mock
    private PagamentoRepository pagamentoRepository;


    @InjectMocks
    private PagamentoService pagamentoService;

    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setup(){
        existingId = 1L;
        nonExistingId = Long.MAX_VALUE;
    }

    @Test
    void deletePagamentoByIdShouldDeleteWhenIdExists(){
        //Arrange - prepara o comportamento do mock (stubbing)
        Mockito.when(pagamentoRepository.existsById(existingId)).thenReturn(true);

        pagamentoService.deletePagamentoById(existingId);

        //verify(...) = verifica se o mock foi chamado
        //verifica que o mock pagamentoRepository recebeu uma chamada ao metodo existsById
        Mockito.verify(pagamentoRepository).existsById(existingId);

        //Verifica se o metodo deleteById do repository foi chamado exatamente uma vez (padrão)
        Mockito.verify(pagamentoRepository, Mockito.times(1)).deleteById(existingId);
    }


    @Test
    @DisplayName("deletePagamentoById deveria lançar ResourceNotFoundException quando o Id não existir")
    void deletePagamentoByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){
        //Arrange
        Mockito.when(pagamentoRepository.existsById(nonExistingId)).thenReturn(false);

        //Act + assert
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> {
                    pagamentoService.deletePagamentoById(nonExistingId);
                });

        //Verificações (behavior)
        Mockito.verify(pagamentoRepository).existsById(nonExistingId);

        //never() = equivale a times(0) -> esse metodo não pode ter sido chamado nenhuma vez.
        //anyLong() é um matcher (coringa): aceita qualquer valor Long/long
        Mockito.verify(pagamentoRepository, Mockito.never()).deleteById(nonExistingId);

    }
}
