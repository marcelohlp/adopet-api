package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.model.tutor.Tutor;
import br.com.alura.adopet.api.model.tutor.TutorDTOCadastrar;
import br.com.alura.adopet.api.model.tutor.TutorDTOCompleto;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validation.tutor.ValidarCadastroTutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private List<ValidarCadastroTutor> validarCadastroTutors;

    public void cadastrar(TutorDTOCadastrar dto) {

        validarCadastroTutors.forEach(validacao -> validacao.validar(dto));

        Tutor tutor = new Tutor(dto.nome(), dto.telefone(), dto.email());

        tutorRepository.save(tutor);

    }

    public void atualizar(TutorDTOCompleto dto) {

        Tutor tutor = tutorRepository.getReferenceById(dto.id());

        tutor.atualizarTutor(dto);

    }

}
