package br.com.alura.adopet.api.validation.adocao;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.adocao.AdocaoDTOSolicitacao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ValidarTutorComAdocaoEmAndamentoTest {

    @InjectMocks
    private ValidarTutorComAdocaoEmAndamento validacao;
    @Mock
    private AdocaoRepository repository;
    @Mock
    private AdocaoDTOSolicitacao dto;

    @Test
    @DisplayName("Verifica se não foi lançado ValidacaoException")
    void naoLancaExcecao() {

        //Arrange

        BDDMockito.given(repository.existsByTutorIdAndStatus(dto.idTutor(), StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(false);

        //Assert - Act

        Assertions.assertDoesNotThrow(() -> validacao.validar(dto));

    }

    @Test
    @DisplayName("Verifica se foi lançado ValidacaoException")
    void lancaExcecao() {

        //Arrange

        BDDMockito.given(repository.existsByTutorIdAndStatus(dto.idTutor(), StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(true);

        //Assert - Act

        Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(dto));

    }

}