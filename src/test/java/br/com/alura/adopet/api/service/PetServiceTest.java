package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.model.abrigo.Abrigo;
import br.com.alura.adopet.api.model.pet.Pet;
import br.com.alura.adopet.api.model.pet.PetDTOCadastrar;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @InjectMocks
    private PetService petService;
    @Mock
    private PetRepository petRepository;
    @Mock
    private Abrigo abrigo;
    private PetDTOCadastrar petDTOCadastrar;
    @Captor
    private ArgumentCaptor<Pet> petArgumentCaptor;

    @Test
    @DisplayName("Deve cadastrar um pet no banco de dados")
    void cadastrarPet(){

        //Arrange

        this.petDTOCadastrar = new PetDTOCadastrar(TipoPet.CACHORRO, "nome", "raca", 1, "cor", 1.0f);

        //Act

        petService.cadastrarPet(petDTOCadastrar, abrigo);

        //Assert

        BDDMockito.then(petRepository).should().save(petArgumentCaptor.capture());
        Pet pet = petArgumentCaptor.getValue();
        Assertions.assertEquals(petDTOCadastrar.tipo(), pet.getTipo());
        Assertions.assertEquals(petDTOCadastrar.nome(), pet.getNome());
        Assertions.assertEquals(petDTOCadastrar.raca(), pet.getRaca());
        Assertions.assertEquals(petDTOCadastrar.idade(), pet.getIdade());
        Assertions.assertEquals(petDTOCadastrar.cor(), pet.getCor());
        Assertions.assertEquals(petDTOCadastrar.peso(), pet.getPeso());

    }

    @Test
    @DisplayName("Deve listar todos os pets disponiveis para adoção")
    void listarPetsDisponiveis () {

        //Arrange


        //Act

        petService.listarPetsDisponiveis();

        //Assert

        BDDMockito.then(petRepository).should().findAllByAdotadoFalse();

    }

}