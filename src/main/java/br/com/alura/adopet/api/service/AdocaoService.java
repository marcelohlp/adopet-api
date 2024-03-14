package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.model.pet.Pet;
import br.com.alura.adopet.api.model.tutor.Tutor;
import br.com.alura.adopet.api.model.adocao.Adocao;
import br.com.alura.adopet.api.model.adocao.AdocaoDTOAprovacao;
import br.com.alura.adopet.api.model.adocao.AdocaoDTOReprovacao;
import br.com.alura.adopet.api.model.adocao.AdocaoDTOSolicitacao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validation.adocao.ValidarSolicitacaoAdocao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AdocaoService {

    @Autowired
    private AdocaoRepository adocaoRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private List<ValidarSolicitacaoAdocao> validarSolicitacao;

    public void solicitar(AdocaoDTOSolicitacao dto) {
        
        validarSolicitacao.forEach(validador -> validador.validar(dto));

        Pet pet = petRepository.getReferenceById(dto.idPet());
        Tutor tutor = tutorRepository.getReferenceById(dto.idTutor());

        Adocao adocao = new Adocao(tutor, pet, dto.motivo());

        adocaoRepository.save(adocao);

        emailService.enviarEmail(
                adocao.getPet().getAbrigo().getEmail(),
                "Solicitação de adoção",
                "Olá " + adocao.getPet().getAbrigo().getNome() + "!\n\nUma solicitação de adoção foi registrada hoje para o pet: " + adocao.getPet().getNome() + ". \nFavor avaliar para aprovação ou reprovação."
        );

    }

    public void aprovar(AdocaoDTOAprovacao dto) {

        Adocao adocao = adocaoRepository.getReferenceById(dto.idAdocao());
        adocao.marcarComoAprovada();

        emailService.enviarEmail(
                adocao.getTutor().getEmail(),
                "Adoção aprovada",
                "Parabéns " + adocao.getTutor().getNome() + "!\n\nSua adoção do pet " + adocao.getPet().getNome() + ", solicitada em " + adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + ", foi aprovada.\nFavor entrar em contato com o abrigo " + adocao.getPet().getAbrigo().getNome() + " para agendar a busca do seu pet."
        );

    }

    public void reprovar(AdocaoDTOReprovacao dto) {

        Adocao adocao = adocaoRepository.getReferenceById(dto.idAdocao());
        adocao.marcarComoReprovada(dto.justificativa());

        emailService.enviarEmail(
                adocao.getTutor().getEmail(),
                "Adoção reprovada",
                "Olá " + adocao.getTutor().getNome() + "!\n\nInfelizmente sua adoção do pet " + adocao.getPet().getNome() + ", solicitada em " + adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + ", foi reprovada pelo abrigo " + adocao.getPet().getAbrigo().getNome() + " com a seguinte justificativa: " + adocao.getJustificativaStatus()
        );

    }

}
