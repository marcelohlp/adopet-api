package br.com.alura.adopet.api.validation.tutor;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.tutor.TutorDTOCadastrar;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarTelefoneJaCadastrado implements ValidarCadastroTutor {

    @Autowired
    private TutorRepository tutorRepository;

    @Override
    public void validar(TutorDTOCadastrar dto) {

        if (tutorRepository.existsByTelefone(dto.telefone())) {
            throw new ValidacaoException("Telefone já cadastrado para outro tutor.");
        }

    }

}
