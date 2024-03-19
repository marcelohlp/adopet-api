package br.com.alura.adopet.api.validation.adocao;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.adocao.Adocao;
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

import java.util.List;

@ExtendWith(MockitoExtension.class)
class ValidarTutorComLimiteDeAdocoesTest {

    @InjectMocks
    private ValidarTutorComLimiteDeAdocoes validacao;
    @Mock
    private AdocaoRepository repository;
    @Mock
    private AdocaoDTOSolicitacao dto;
    @Mock
    private List<Adocao> adocoes;

    @Test
    @DisplayName("Verifica se não foi lançado ValidacaoException")
    void naoLancaExcecao() {

        //Arrange

        BDDMockito.given(repository.findAllByTutorIdAndStatus(dto.idTutor(), StatusAdocao.APROVADO)).willReturn(adocoes);

        //ASSERT + ACT
        Assertions.assertDoesNotThrow(() -> validacao.validar(dto));

    }

    @Test
    @DisplayName("Verifica se foi lançado ValidacaoException")
    void lancaExcecao() {

        //Arrange

        BDDMockito.given(repository.findAllByTutorIdAndStatus(dto.idTutor(), StatusAdocao.APROVADO)).willReturn(adocoes);
        BDDMockito.given(adocoes.size()).willReturn(5);

        //ASSERT + ACT
        Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(dto));

    }

}