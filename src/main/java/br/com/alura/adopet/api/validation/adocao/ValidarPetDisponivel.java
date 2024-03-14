package br.com.alura.adopet.api.validation.adocao;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.pet.Pet;
import br.com.alura.adopet.api.model.adocao.AdocaoDTOSolicitacao;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarPetDisponivel implements ValidarSolicitacaoAdocao{

    @Autowired
    private PetRepository petRepository;

    @Override
    public void validar(AdocaoDTOSolicitacao dto) {
        Pet pet = petRepository.getReferenceById(dto.idPet());
        if (pet.getAdotado()) {
            throw new ValidacaoException("Pet já foi adotado!");
        }
    }
}
