package com.github.yasminmuniz25.ms.pagamentos.service;

import com.github.yasminmuniz25.ms.pagamentos.dto.PagamentoDTO;
import com.github.yasminmuniz25.ms.pagamentos.entities.Pagamento;
import com.github.yasminmuniz25.ms.pagamentos.exceptions.ResourceNotFoundException;
import com.github.yasminmuniz25.ms.pagamentos.repositories.PagamentoRepository;
import com.github.yasminmuniz25.ms.pagamentos.tests.Factory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class PagamentoServiceTest {

    @Mock
    private PagamentoRepository pagamentoRepository;


    @InjectMocks
    private PagamentoService pagamentoService;

    private Long existingId;
    private Long nonExistingId;

    private Pagamento pagamento;

    @BeforeEach
    void setup(){
        existingId = 1L;
        nonExistingId = Long.MAX_VALUE;
        pagamento = Factory.createPagamento();
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

    @Test
    void findPagamentoByIdShouldReturnPagamentoDTOWhenIdExists(){
        //Arrange
        Mockito.when(pagamentoRepository
                .findById(existingId))
                .thenReturn(Optional.of(pagamento));

        //Act
        PagamentoDTO result = pagamentoService.findPagamentoById(existingId);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(pagamento.getId(),result.getId());
        Assertions.assertEquals(pagamento.getValor(), result.getValor());

        Mockito.verify(pagamentoRepository).findById(existingId);
        Mockito.verifyNoMoreInteractions(pagamentoRepository);

    }

    @Test
    void findPagamentoByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){

        Mockito.when(pagamentoRepository.findById(nonExistingId))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> pagamentoService.findPagamentoById(nonExistingId));

        Mockito.verify(pagamentoRepository).findById(nonExistingId);
        Mockito.verifyNoMoreInteractions(pagamentoRepository);
    }

    @Test
    void givenValidParamsAndIdIsNull_whenSave_thenShouldPersistPagamento(){

        pagamento.setId(null);
        //Arrange
        Mockito.when(pagamentoRepository.save(any(Pagamento.class)))
                .thenReturn(pagamento);
        PagamentoDTO imputDto = new PagamentoDTO(pagamento);

        //Act
        PagamentoDTO result = pagamentoService.savePagamento(imputDto);

        //Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(pagamento.getId(), result.getId());

        //Verify
        Mockito.verify(pagamentoRepository).save(any(Pagamento.class));
        Mockito.verifyNoMoreInteractions(pagamentoRepository);
        }


    @Test
    void updatePagamentoShouldReturnPagamentoDTOWhenIdExists(){
        //Arrange
        Long id = pagamento.getId();
        Mockito.when(pagamentoRepository.getReferenceById(id)).thenReturn(pagamento);
        Mockito.when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamento);

        //Act
        PagamentoDTO result = pagamentoService.updatePagamento(id, new PagamentoDTO(pagamento));

        //Assert e Vetify
        Assertions.assertNotNull(result);
        Assertions.assertEquals(id, result.getId());
        Assertions.assertEquals(pagamento.getValor(), result.getValor());
        Mockito.verify(pagamentoRepository).getReferenceById(id);
        Mockito.verify(pagamentoRepository).save(Mockito.any(Pagamento.class));
        Mockito.verifyNoMoreInteractions(pagamentoRepository);
    }


    @Test
    void updatePagamentoShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists(){

        Mockito.when(pagamentoRepository.getReferenceById(nonExistingId))
                .thenThrow(EntityNotFoundException.class);
        PagamentoDTO inputDto = new PagamentoDTO(pagamento);

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> pagamentoService.updatePagamento(nonExistingId, inputDto));
        Mockito.verify(pagamentoRepository).getReferenceById(nonExistingId);
        Mockito.verify(pagamentoRepository, Mockito.never()).save(Mockito.any(Pagamento.class));
        Mockito.verifyNoMoreInteractions(pagamentoRepository);
    }
}


