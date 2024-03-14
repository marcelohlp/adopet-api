package br.com.alura.adopet.api.validation.adocao;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.adocao.AdocaoDTOSolicitacao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarTutorComAdocaoEmAndamento implements ValidarSolicitacaoAdocao {

    @Autowired
    private AdocaoRepository adocaoRepository;

    @Override
    public void validar(AdocaoDTOSolicitacao dto) {

        Boolean tutorTemAdocaoEmAndamento = adocaoRepository
                .existsByTutorIdAndStatus(dto.idTutor(), StatusAdocao.AGUARDANDO_AVALIACAO);

        if (tutorTemAdocaoEmAndamento) {
            throw new ValidacaoException("Tutor já possui outra adoção aguardando avaliação!");
        }

    }
}
