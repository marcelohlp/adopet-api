package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.model.tutor.TutorDTOCadastrar;
import br.com.alura.adopet.api.model.tutor.TutorDTOCompleto;
import br.com.alura.adopet.api.service.TutorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TutorControllerTest {

    @MockBean
    private TutorService tutorService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<TutorDTOCadastrar> jsonDTOTUtorCadastrar;
    @Autowired
    private JacksonTester<TutorDTOCompleto> jsonDTOTutorCompleto;

    @Test
    @DisplayName("Recebe códiogo 200 para cadastrar tutor com sucesso")
    void cadastrarTutorCodigo200() throws Exception {

        //Arrange

        TutorDTOCadastrar dto = new TutorDTOCadastrar("exemplo", "11999999999", "exemplo@mail.com");

        //Act

        MockHttpServletResponse response = mockMvc.perform(post("/tutores").content(jsonDTOTUtorCadastrar.write(dto).getJson()).contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //Assert

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    @DisplayName("Recebe códiogo 400 para cadastrar tutor com erro")
    void cadastrarTutorCodigo400() throws Exception {

        //Arrange

        String json = "{}";

        //Act

        MockHttpServletResponse response = mockMvc.perform(post("/tutores").content(json).contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //Assert

        Assertions.assertEquals(400, response.getStatus());

    }

    @Test
    @DisplayName("Recebe códiogo 200 para atualizar tutor com sucesso")
    void atualizarTutorCodigo200() throws Exception {

        //Arrange

        TutorDTOCompleto dto = new TutorDTOCompleto(1L, "exemplo", "11999999999", "exemplo@mail.com");

        //Act

        MockHttpServletResponse response = mockMvc.perform(post("/tutores").content(jsonDTOTutorCompleto.write(dto).getJson()).contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //Assert

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    @DisplayName("Recebe códiogo 400 para atualizar tutor com erro")
    void atualizarTutorCodigo400() throws Exception {

        //Arrange

        String json = "{}";

        //Act

        MockHttpServletResponse response = mockMvc.perform(post("/tutores").content(json).contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //Assert

        Assertions.assertEquals(400, response.getStatus());

    }

}