package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.model.abrigo.Abrigo;
import br.com.alura.adopet.api.model.abrigo.AbrigoDTOCadastrar;
import br.com.alura.adopet.api.model.pet.PetDTOCadastrar;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AbrigoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AbrigoService abrigoService;
    @MockBean
    private PetService petService;
    @Autowired
    private JacksonTester<AbrigoDTOCadastrar> jsonDTOCadastrar;
    @Autowired
    private JacksonTester<PetDTOCadastrar> jsonDTOCadastrarPet;
    @Mock
    private Abrigo abrigo;

    @Test
    @DisplayName("Recebe códiogo 200 para listar abrigos com sucesso")
    void listarCodigo200() throws Exception {

        //Arrange

        //Act

        MockHttpServletResponse response = mockMvc.perform(get("/abrigos"))
                .andReturn()
                .getResponse();

        //Assert

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    @DisplayName("Recebe códiogo 200 para uma abrigo cadastrado com sucesso")
    void cadastroCodigo200() throws Exception {

        //Arrange

        AbrigoDTOCadastrar dto = new AbrigoDTOCadastrar("nome", "11999999999", "exemplo@mail.com");

        //Act

        MockHttpServletResponse response = mockMvc.perform(post("/abrigos").content(jsonDTOCadastrar.write(dto).getJson()).contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //Assert

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    @DisplayName("Recebe códiogo 400 para uma abrigo não cadastrado com sucesso")
    void cadastroCodigo400() throws Exception {

        //Arrange

        String json = "{}";

        //Act

        MockHttpServletResponse response = mockMvc.perform(post("/abrigos").content(json).contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //Assert

        Assertions.assertEquals(400, response.getStatus());

    }

    @Test
    @DisplayName("Recebe códiogo 200 para listagem de pets por nome do abrigo")
    void listarPetsPorNomeCodigo200() throws Exception {

        //Arrange

        String nome = "nome";

        //Act

        MockHttpServletResponse response = mockMvc.perform(get("/abrigos/{nome}/pets", nome))
                .andReturn()
                .getResponse();

        //Assert

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    @DisplayName("Recebe códiogo 404 para listagem de pets por nome do abrigo com erro")
    void listarPetsPorNomeCodigo404() throws Exception {

        //Arrange

        String nome = "nome";
        BDDMockito.given(abrigoService.listarPetsDoAbrigo(nome)).willThrow(ValidacaoException.class);

        //Act

        MockHttpServletResponse response = mockMvc.perform(get("/abrigos/{nome}/pets", nome))
                .andReturn()
                .getResponse();

        //Assert

        Assertions.assertEquals(404, response.getStatus());

    }

    @Test
    @DisplayName("Recebe códiogo 200 para listagem de pets por id do abrigo")
    void listarPetsPorIdCodigo200() throws Exception {

        //Arrange

        String id = "1";

        //Act

        MockHttpServletResponse response = mockMvc.perform(get("/abrigos/{nome}/pets", id))
                .andReturn()
                .getResponse();

        //Assert

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    @DisplayName("Recebe códiogo 404 para listagem de pets por id do abrigo com erro")
    void listarPetsPorIdCodigo404() throws Exception {

        //Arrange

        String id = "1";
        BDDMockito.given(abrigoService.listarPetsDoAbrigo(id)).willThrow(ValidacaoException.class);

        //Act

        MockHttpServletResponse response = mockMvc.perform(get("/abrigos/{nome}/pets", id))
                .andReturn()
                .getResponse();

        //Assert

        Assertions.assertEquals(404, response.getStatus());

    }

    @Test
    @DisplayName("Recebe códiogo 200 para cadastro de pet por nome do abrigo")
    void cadastrarPetNomeAbrigoCodigo200() throws Exception {

        //Arrange

        String nome = "nome";
        PetDTOCadastrar dto = new PetDTOCadastrar(TipoPet.CACHORRO, "nome", "raca", 1, "cor", 10.0f);

        //Act

        MockHttpServletResponse response = mockMvc.perform(post("/abrigos/{nome}/pets", nome).content(jsonDTOCadastrarPet.write(dto).getJson()).contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();


        //Assert

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    @DisplayName("Recebe códiogo 400 para cadastro de pet por nome do abrigo com erro")
    void cadastrarPetNomeAbrigoCodigo400() throws Exception {

        //Arrange

        String nome = "nome";
        PetDTOCadastrar dto = new PetDTOCadastrar(TipoPet.CACHORRO, "nome", "raca", 1, "cor", 10.0f);
        BDDMockito.given(abrigoService.carregarAbrigo(nome)).willThrow(ValidacaoException.class);

        //Act

        MockHttpServletResponse response = mockMvc.perform(post("/abrigos/{nome}/pets", nome).content(jsonDTOCadastrarPet.write(dto).getJson()).contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();


        //Assert

        Assertions.assertEquals(400, response.getStatus());

    }

    @Test
    @DisplayName("Recebe códiogo 200 para cadastro de pet por id do abrigo")
    void cadastrarPetIdAbrigoCodigo200() throws Exception {

        //Arrange

        String id = "1";
        PetDTOCadastrar dto = new PetDTOCadastrar(TipoPet.CACHORRO, "nome", "raca", 1, "cor", 10.0f);

        //Act

        MockHttpServletResponse response = mockMvc.perform(post("/abrigos/{id}/pets", id).content(jsonDTOCadastrarPet.write(dto).getJson()).contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();


        //Assert

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    @DisplayName("Recebe códiogo 400 para cadastro de pet por id do abrigo com erro")
    void cadastrarPetIdAbrigoCodigo400() throws Exception {

        //Arrange

        String id = "1";
        PetDTOCadastrar dto = new PetDTOCadastrar(TipoPet.CACHORRO, "nome", "raca", 1, "cor", 10.0f);
        BDDMockito.given(abrigoService.carregarAbrigo(id)).willThrow(ValidacaoException.class);

        //Act

        MockHttpServletResponse response = mockMvc.perform(post("/abrigos/{id}/pets", id).content(jsonDTOCadastrarPet.write(dto).getJson()).contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();


        //Assert

        Assertions.assertEquals(400, response.getStatus());

    }

}