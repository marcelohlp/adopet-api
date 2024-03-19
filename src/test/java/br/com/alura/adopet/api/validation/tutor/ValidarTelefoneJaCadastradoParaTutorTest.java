package br.com.alura.adopet.api.validation.tutor;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.tutor.TutorDTOCadastrar;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ValidarTelefoneJaCadastradoParaTutorTest {

    @InjectMocks
    private ValidarTelefoneJaCadastradoParaTutor validacao;
    @Mock
    private TutorRepository repository;
    @Mock
    private TutorDTOCadastrar dto;

    @Test
    @DisplayName("Verifica se não foi lançado ValidacaoException")
    void naoLancaExcecao() {

        //Arrange

        BDDMockito.given(repository.existsByTelefone(dto.telefone())).willReturn(false);

        //Assert + Act

        Assertions.assertDoesNotThrow(() -> validacao.validar(dto));

    }

    @Test
    @DisplayName("Verifica se foi lançado ValidacaoException")
    void lancaExcecao() {

        //Arrange

        BDDMockito.given(repository.existsByTelefone(dto.telefone())).willReturn(true);

        //Assert + Act

        Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(dto));

    }


}