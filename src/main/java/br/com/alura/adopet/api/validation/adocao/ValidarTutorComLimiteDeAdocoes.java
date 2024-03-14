package br.com.alura.adopet.api.validation.adocao;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.adocao.Adocao;
import br.com.alura.adopet.api.model.adocao.AdocaoDTOSolicitacao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidarTutorComLimiteDeAdocoes implements ValidarSolicitacaoAdocao{

    @Autowired
    private AdocaoRepository adocaoRepository;
    @Autowired
    private TutorRepository tutorRepository;

    @Override
    public void validar(AdocaoDTOSolicitacao dto) {

        List<Adocao> adocoes = adocaoRepository
                .findAllByTutorIdAndStatus(dto.idTutor(), StatusAdocao.APROVADO);

        if (adocoes.size() >= 5) {
            throw new ValidacaoException("Tutor chegou ao limite máximo de 5 adoções!");
        }

    }
}
