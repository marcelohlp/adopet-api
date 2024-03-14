package br.com.alura.adopet.api.validation.abrigo;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.abrigo.AbrigoDTOCadastrar;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarEmailJaCadastrado implements ValidarCadastroAbrigo{

    @Autowired
    private AbrigoRepository abrigoRepository;

    @Override
    public void validar(AbrigoDTOCadastrar dto) {

        if (abrigoRepository.existsByEmail(dto.email())) {
            throw new ValidacaoException("Email já cadastrados para outro abrigo!");
        }

    }

}
