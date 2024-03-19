package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.service.AbrigoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTest {

    @MockBean
    private AbrigoService abrigoService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Recebe códiogo 200 para listar pets com sucesso")
    void listarCodigo200() throws Exception {

        //Arrange


        //Act

        MockHttpServletResponse response = mockMvc.perform(get("/pets"))
                .andReturn()
                .getResponse();

        //Assert

        Assertions.assertEquals(200, response.getStatus());

    }

}