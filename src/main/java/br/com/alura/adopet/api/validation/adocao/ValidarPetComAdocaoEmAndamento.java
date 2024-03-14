package br.com.alura.adopet.api.validation.adocao;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.adocao.AdocaoDTOSolicitacao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarPetComAdocaoEmAndamento implements ValidarSolicitacaoAdocao {

    @Autowired
    private AdocaoRepository adocaoRepository;

    @Override
    public void validar(AdocaoDTOSolicitacao dto) {

        Boolean petTemAdocaoEmAndamento = adocaoRepository
                .existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO);

        if (petTemAdocaoEmAndamento) {
            throw new ValidacaoException("Pet já está aguardando avaliação para ser adotado!");
        }

    }
}
