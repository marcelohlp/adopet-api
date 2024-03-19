package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.model.adocao.AdocaoDTOAprovacao;
import br.com.alura.adopet.api.model.adocao.AdocaoDTOReprovacao;
import br.com.alura.adopet.api.model.adocao.AdocaoDTOSolicitacao;
import br.com.alura.adopet.api.service.AdocaoService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AdocaoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AdocaoService adocaoService;
    @Autowired
    private JacksonTester<AdocaoDTOSolicitacao> jsonDtoSolicitacao;
    @Autowired
    private JacksonTester<AdocaoDTOAprovacao> jsonDtoAprovacao;
    @Autowired
    private JacksonTester<AdocaoDTOReprovacao> jsonDtoReprovacao;

    @Test
    @DisplayName("Recebe códiogo 200 para uma soliciatção com correta")
    void solicitacaoCodigo200() throws Exception {

        //Arrange

        AdocaoDTOSolicitacao dto = new AdocaoDTOSolicitacao(1L, 1L, "N/D");

        //Act

        MockHttpServletResponse response = mockMvc
                .perform(post("/adocoes").content(jsonDtoSolicitacao.write(dto).getJson()).contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //Assert

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    @DisplayName("Recebe códiogo 400 para uma soliciatção com erro")
    void solicitacaoCodigo400() throws Exception {

        //Arrange

        String json = "{}";

        //Act

        MockHttpServletResponse response = mockMvc
                .perform(post("/adocoes").content(json).contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //Assert

        Assertions.assertEquals(400, response.getStatus());

    }

    @Test
    @DisplayName("Recebe códiogo 200 para uma solicitação de aprovação correta")
    void aprovarCodigo200() throws Exception {

        //Arrange

        AdocaoDTOAprovacao dto = new AdocaoDTOAprovacao(1L);

        //Act

        MockHttpServletResponse response = mockMvc
                .perform(put("/adocoes/aprovar").content(jsonDtoAprovacao.write(dto).getJson()).contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //Assert

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    @DisplayName("Recebe códiogo 400 para uma solicitação de aprovação incorreta")
    void aprovarCodigo400() throws Exception {

        //Arrange

        String json = "{}";

        //Act

        MockHttpServletResponse response = mockMvc
                .perform(put("/adocoes/aprovar").content(json).contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //Assert

        Assertions.assertEquals(400, response.getStatus());

    }

    @Test
    @DisplayName("Recebe códiogo 200 para uma solicitação de reprovação correta")
    void reprovarCodigo200() throws Exception {

        //Arrange

        AdocaoDTOReprovacao dto = new AdocaoDTOReprovacao(1L, "N/D");

        //Act

        MockHttpServletResponse response = mockMvc
                .perform(put("/adocoes/reprovar").content(jsonDtoReprovacao.write(dto).getJson()).contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //Assert

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    @DisplayName("Recebe códiogo 400 para uma solicitação de reprovação incorreta")
    void reprovarCodigo400() throws Exception {

        //Arrange

        String json = "{}";

        //Act

        MockHttpServletResponse response = mockMvc
                .perform(put("/adocoes/reprovar").content(json).contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //Assert

        Assertions.assertEquals(400, response.getStatus());

    }

}