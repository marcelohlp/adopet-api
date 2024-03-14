package br.com.alura.adopet.api.validation.abrigo;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.abrigo.AbrigoDTOCadastrar;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarTelefoneJaCadastrado implements ValidarCadastroAbrigo{

    @Autowired
    private AbrigoRepository abrigoRepository;

    @Override
    public void validar(AbrigoDTOCadastrar dto) {

        if (abrigoRepository.existsByTelefone(dto.telefone())) {
            throw new ValidacaoException("Telefone já cadastrados para outro abrigo!");
        }

    }
}
