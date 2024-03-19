package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.model.tutor.Tutor;
import br.com.alura.adopet.api.model.tutor.TutorDTOCadastrar;
import br.com.alura.adopet.api.model.tutor.TutorDTOCompleto;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validation.tutor.ValidarCadastroTutor;
import br.com.alura.adopet.api.validation.tutor.ValidarEmailJaCadastradoParaTutor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TutorServiceTest {

    @InjectMocks
    private TutorService tutorService;
    @Mock
    private TutorRepository tutorRepository;
    @Mock
    private Tutor tutor;
    @Captor
    private ArgumentCaptor<Tutor> tutorArgumentCaptor;
    @Spy
    private List<ValidarCadastroTutor> validarCadastroTutor = new ArrayList<>();
    @Mock
    private ValidarEmailJaCadastradoParaTutor validacao1;
    @Mock
    private ValidarEmailJaCadastradoParaTutor validacao2;
    private TutorDTOCadastrar tutorDTOCadastrar;
    @Mock
    private TutorDTOCompleto tutorDTOCompleto;

    @Test
    @DisplayName("Deve cadastrar um novo tutor no banco de dados")
    void cadastrarTutor() {

        //Arange

        this.tutorDTOCadastrar = new TutorDTOCadastrar("exemplo", "11999999999", "exemplo@mail.com");

        //Act

        tutorService.cadastrar(tutorDTOCadastrar);

        //Assert

        BDDMockito.then(tutorRepository).should().save(tutorArgumentCaptor.capture());
        Tutor tutorSalvo = tutorArgumentCaptor.getValue();
        Assertions.assertEquals(tutorDTOCadastrar.nome(), tutorSalvo.getNome());
        Assertions.assertEquals(tutorDTOCadastrar.telefone(), tutorSalvo.getTelefone());
        Assertions.assertEquals(tutorDTOCadastrar.email(), tutorSalvo.getEmail());

    }

    @Test
    @DisplayName("Deve validar de o cadastro do tutor atende a todas as regras de negócio")
    void validarCadastroTutor() {

        //Arrange

        this.tutorDTOCadastrar = new TutorDTOCadastrar("exemplo", "11999999999", "exemplo@mail.com");

        validarCadastroTutor.add(validacao1);
        validarCadastroTutor.add(validacao2);

        //Act

        tutorService.cadastrar(tutorDTOCadastrar);

        //Assert

        BDDMockito.then(validacao1).should().validar(tutorDTOCadastrar);
        BDDMockito.then(validacao2).should().validar(tutorDTOCadastrar);

    }

    @Test
    @DisplayName("Deve atualizar o tutor")
    void atualizarTutor() {

        //Arrange

        BDDMockito.given(tutorRepository.getReferenceById(tutorDTOCompleto.id())).willReturn(tutor);

        //Act

        tutorService.atualizar(tutorDTOCompleto);

        //Assert

        BDDMockito.then(tutor).should().atualizarTutor(tutorDTOCompleto);

    }

}