package br.com.alura.adopet.api.validation.abrigo;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.abrigo.AbrigoDTOCadastrar;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarNomeJaCadastrado implements ValidarCadastroAbrigo{

    @Autowired
    private AbrigoRepository abrigoRepository;

    @Override
    public void validar(AbrigoDTOCadastrar dto) {

        if (abrigoRepository.existsByNome(dto.nome())) {
            throw new ValidacaoException("Nome já cadastrados para outro abrigo!");
        }

    }
}
