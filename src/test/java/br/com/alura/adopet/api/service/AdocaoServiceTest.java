package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.abrigo.Abrigo;
import br.com.alura.adopet.api.model.adocao.Adocao;
import br.com.alura.adopet.api.model.adocao.AdocaoDTOAprovacao;
import br.com.alura.adopet.api.model.adocao.AdocaoDTOReprovacao;
import br.com.alura.adopet.api.model.adocao.AdocaoDTOSolicitacao;
import br.com.alura.adopet.api.model.pet.Pet;
import br.com.alura.adopet.api.model.tutor.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validation.adocao.ValidarSolicitacaoAdocao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class AdocaoServiceTest {

    @InjectMocks
    private AdocaoService adocaoService;
    @Mock
    private AdocaoRepository adocaoRepository;
    @Mock
    private PetRepository petRepository;
    @Mock
    private TutorRepository tutorRepository;
    @Mock
    private EmailService emailService;
    @Spy
    private List<ValidarSolicitacaoAdocao> validarSolicitacao = new ArrayList<>();
    @Mock
    private ValidarSolicitacaoAdocao validacao1;
    @Mock
    private ValidarSolicitacaoAdocao validacao2;
    @Spy
    private Adocao adocao;
    @Mock
    private Pet pet;
    @Mock
    private Tutor tutor;
    @Mock
    private Abrigo abrigo;
    @Captor
    private ArgumentCaptor<Adocao> adocaoCaptor;
    private AdocaoDTOSolicitacao adocaoDTOSolicitacao;
    @Mock
    private AdocaoDTOAprovacao adocaoDTOAprovacao;
    @Mock
    private AdocaoDTOReprovacao adocaoDTOReprovacao;


    @Test
    @DisplayName("Deve salvar a soliciação de adoção no banco de dados quando o método solicitar for chamado")
    void salvarSolicitacaoNoBanco () {

        //Arrange

        this.adocaoDTOSolicitacao = new AdocaoDTOSolicitacao(10L, 20L, "N/D");
        BDDMockito.given(petRepository.getReferenceById(adocaoDTOSolicitacao.idPet())).willReturn(pet);
        BDDMockito.given(tutorRepository.getReferenceById(adocaoDTOSolicitacao.idTutor())).willReturn(tutor);
        BDDMockito.given(pet.getAbrigo()).willReturn(abrigo);

        //Act

        adocaoService.solicitar(adocaoDTOSolicitacao);

        //Assert

        BDDMockito.then(adocaoRepository).should().save(adocaoCaptor.capture());
        Adocao adocao = adocaoCaptor.getValue();
        Assertions.assertEquals(pet, adocao.getPet());
        Assertions.assertEquals(tutor, adocao.getTutor());
        Assertions.assertEquals(adocaoDTOSolicitacao.motivo(), adocao.getMotivo());

    }

    @Test
    @DisplayName("Deve validar se uma solicitação de adoção atende a todos a todas as regras de negócio")
    void validarSolicitacao () {

        //Arrange

        this.adocaoDTOSolicitacao = new AdocaoDTOSolicitacao(10L, 20L, "N/D");
        BDDMockito.given(petRepository.getReferenceById(adocaoDTOSolicitacao.idPet())).willReturn(pet);
        BDDMockito.given(tutorRepository.getReferenceById(adocaoDTOSolicitacao.idTutor())).willReturn(tutor);
        BDDMockito.given(pet.getAbrigo()).willReturn(abrigo);

        validarSolicitacao.add(validacao1);
        validarSolicitacao.add(validacao2);

        //Act

        adocaoService.solicitar(adocaoDTOSolicitacao);

        //Assert

        BDDMockito.then(validacao1).should().validar(adocaoDTOSolicitacao);
        BDDMockito.then(validacao2).should().validar(adocaoDTOSolicitacao);

    }

    @Test
    @DisplayName("Deve enviar o email com mensagem de solicitação de adoção realizada com sucesso")
    void validarEnvioEmailSolicitacao () {

        //Arrange

        this.adocaoDTOSolicitacao = new AdocaoDTOSolicitacao(10L, 20L, "N/D");
        BDDMockito.given(petRepository.getReferenceById(adocaoDTOSolicitacao.idPet())).willReturn(pet);
        BDDMockito.given(tutorRepository.getReferenceById(adocaoDTOSolicitacao.idTutor())).willReturn(tutor);
        BDDMockito.given(pet.getAbrigo()).willReturn(abrigo);

        Adocao adocao = new Adocao(tutor, pet, "N/D");

        String suject = "Solicitação de adoção";
        String text = "Olá " + adocao.getPet().getAbrigo().getNome() + "!\n\nUma solicitação de adoção foi registrada hoje para o pet: " + adocao.getPet().getNome() + ". \nFavor avaliar para aprovação ou reprovação.";

        //Act

        adocaoService.solicitar(adocaoDTOSolicitacao);

        //Assert

        BDDMockito.then(emailService).should().enviarEmail(adocao.getTutor().getEmail(),suject,text);

    }

    @Test
    @DisplayName("Deve salvar a aprovação de uma adoção no banco de dados quando o método aprovar for chamado")
    void salvarAprovacaoNoBanco () {

        //Arrange

        BDDMockito.given(adocaoRepository.getReferenceById(adocaoDTOAprovacao.idAdocao())).willReturn(adocao);
        BDDMockito.given(adocao.getPet()).willReturn(pet);
        BDDMockito.given(pet.getAbrigo()).willReturn(abrigo);
        BDDMockito.given(adocao.getTutor()).willReturn(tutor);
        BDDMockito.given(adocao.getData()).willReturn(LocalDateTime.now());

        //Act

        adocaoService.aprovar(adocaoDTOAprovacao);

        //Assert

        BDDMockito.then(adocao).should().marcarComoAprovada();
        Assertions.assertEquals(StatusAdocao.APROVADO, adocao.getStatus());

    }

    @Test
    @DisplayName("Deve enviar o email com mensagem de aprovação da solicitação de adoção")
    void validarEnvioEmailAprovacao () {

        //Arrange

        BDDMockito.given(adocaoRepository.getReferenceById(adocaoDTOAprovacao.idAdocao())).willReturn(adocao);
        BDDMockito.given(adocao.getTutor()).willReturn(tutor);
        BDDMockito.given(adocao.getPet()).willReturn(pet);
        BDDMockito.given(adocao.getData()).willReturn(LocalDateTime.now());
        BDDMockito.given(pet.getAbrigo()).willReturn(abrigo);

        String suject = "Adoção aprovada";
        String text = "Parabéns " + adocao.getTutor().getNome() + "!\n\nSua adoção do pet " + adocao.getPet().getNome() + ", solicitada em " + adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + ", foi aprovada.\nFavor entrar em contato com o abrigo " + adocao.getPet().getAbrigo().getNome() + " para agendar a busca do seu pet.";

        //Act

        adocaoService.aprovar(adocaoDTOAprovacao);

        //Assert

        BDDMockito.then(emailService).should().enviarEmail(adocao.getTutor().getEmail(),suject,text);

    }

    @Test
    @DisplayName("Deve salvar a reprovação de adoção no banco de dados quando o método aprovar for chamado")
    void salvarReprovacaoNoBanco () {

        //Arrange

        BDDMockito.given(adocaoRepository.getReferenceById(adocaoDTOReprovacao.idAdocao())).willReturn(adocao);
        BDDMockito.given(adocao.getPet()).willReturn(pet);
        BDDMockito.given(pet.getAbrigo()).willReturn(abrigo);
        BDDMockito.given(adocao.getTutor()).willReturn(tutor);
        BDDMockito.given(adocao.getData()).willReturn(LocalDateTime.now());
        BDDMockito.given(adocaoDTOReprovacao.justificativa()).willReturn("N/D");

        //Act

        adocaoService.reprovar(adocaoDTOReprovacao);

        //Assert

        BDDMockito.then(adocao).should().marcarComoReprovada("N/D");
        Assertions.assertEquals(StatusAdocao.REPROVADO, adocao.getStatus());

    }

    @Test
    @DisplayName("Deve enviar o email com mensagem de reprovação da solicitação de adoção")
    void validarEnvioEmailReprovacao () {

        //Arrange

        BDDMockito.given(adocaoRepository.getReferenceById(adocaoDTOAprovacao.idAdocao())).willReturn(adocao);
        BDDMockito.given(adocao.getTutor()).willReturn(tutor);
        BDDMockito.given(adocao.getPet()).willReturn(pet);
        BDDMockito.given(adocao.getData()).willReturn(LocalDateTime.now());
        BDDMockito.given(pet.getAbrigo()).willReturn(abrigo);

        String suject = "Adoção reprovada";
        String text = "Olá " + adocao.getTutor().getNome() + "!\n\nInfelizmente sua adoção do pet " + adocao.getPet().getNome() + ", solicitada em " + adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + ", foi reprovada pelo abrigo " + adocao.getPet().getAbrigo().getNome() + " com a seguinte justificativa: " + adocao.getJustificativaStatus();

        //Act

        adocaoService.reprovar(adocaoDTOReprovacao);

        //Assert

        BDDMockito.then(emailService).should().enviarEmail(adocao.getTutor().getEmail(),suject,text);

    }

}