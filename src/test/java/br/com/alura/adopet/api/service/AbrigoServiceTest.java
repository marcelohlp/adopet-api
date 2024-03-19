package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.abrigo.Abrigo;
import br.com.alura.adopet.api.model.abrigo.AbrigoDTOCadastrar;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.validation.abrigo.ValidarCadastroAbrigo;
import br.com.alura.adopet.api.validation.abrigo.ValidarEmailJaCadastrado;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {

    @InjectMocks
    private AbrigoService abrigoService;
    @Mock
    private AbrigoRepository abrigoRepository;
    @Mock
    private PetRepository petRepository;
    @Mock
    private Abrigo abrigo;
    @Mock
    private AbrigoDTOCadastrar abrigoDTOCadastrar;
    @Spy
    private List<ValidarCadastroAbrigo> validacoes = new ArrayList<>();
    @Mock
    private ValidarEmailJaCadastrado validacao1;
    @Mock
    private ValidarEmailJaCadastrado validacao2;


    @Test
    @DisplayName("Deve listar os abrigos disponíveis")
    void listar() {

        //Act

        abrigoService.listar();

        //Assert

        BDDMockito.then(abrigoRepository).should().findAll();

    }

    @Test
    @DisplayName("Deve cadastrar um abrigo")
    void cadastrar() {

        //Arrange

        this.abrigo = new Abrigo(abrigoDTOCadastrar.nome(), abrigoDTOCadastrar.telefone(), abrigoDTOCadastrar.email());

        //Act

        abrigoService.cadastrar(abrigoDTOCadastrar);

        //Assert

        BDDMockito.then(abrigoRepository).should().save(abrigo);

    }

    @Test
    @DisplayName("Deve validar o cadastro de um abrigo")
    void validarCadastro() {

        //Arrange

        validacoes.add(validacao1);
        validacoes.add(validacao2);

        //Act

        abrigoService.cadastrar(abrigoDTOCadastrar);

        //Assert

        BDDMockito.then(validacao1).should().validar(abrigoDTOCadastrar);
        BDDMockito.then(validacao2).should().validar(abrigoDTOCadastrar);

    }

    @Test
    @DisplayName("Deve retornar todos os pets cadastrados em um abrigo")
    void listarPetsDoAbrigo() {

        //Arrange

        String idOuNome = "nome";
        BDDMockito.given(abrigoRepository.findByNome(idOuNome)).willReturn(Optional.of(abrigo));

        //Act

        abrigoService.listarPetsDoAbrigo(idOuNome);

        //Assert

        BDDMockito.then(petRepository).should().findByAbrigo(abrigo);

    }

    @Test
    @DisplayName("Deve retornar um abrigo está cadastrado no banco de dados quando buscado por id")
    void carregarAbrigoId() {

        //Arrange

        String idOuNome = "1";
        Long id = Long.parseLong(idOuNome);
        BDDMockito.given(abrigoRepository.findById(id)).willReturn(Optional.of(abrigo));

        //Act

        abrigoService.carregarAbrigo(idOuNome);

        //Assert

        BDDMockito.then(abrigoRepository).should().findById(id);

    }

    @Test
    @DisplayName("Deve retornar um abrigo está cadastrado no banco de dados quando buscado por nome")
    void carregarAbrigoNome() {

        //Arrange

        String idOuNome = "nome";
        BDDMockito.given(abrigoRepository.findByNome(idOuNome)).willReturn(Optional.of(abrigo));

        //Act

        abrigoService.carregarAbrigo(idOuNome);

        //Assert

        BDDMockito.then(abrigoRepository).should().findByNome(idOuNome);

    }

    @Test
    @DisplayName("Deve lançar uma exceção ao tentar carregar um abrigo por id ou nome")
    void lancaExcecaoAoCarregarAbrigo() {

        //Assert - Act

        Assertions.assertThrows(ValidacaoException.class, () -> abrigoService.carregarAbrigo(null));

    }

}