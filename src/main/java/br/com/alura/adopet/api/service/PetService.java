package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.model.abrigo.Abrigo;
import br.com.alura.adopet.api.model.pet.Pet;
import br.com.alura.adopet.api.model.pet.PetDTOCadastrar;
import br.com.alura.adopet.api.model.pet.PetDTOListar;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public void cadastrarPet(PetDTOCadastrar dto, Abrigo abrigo) {

        Pet pet = new Pet(dto, abrigo);
        petRepository.save(pet);

    }

    public List<PetDTOListar> listarPetsDisponiveis() {

        List<Pet> pets = petRepository.findAllByAdotadoFalse();

        return pets
                .stream()
                .map(PetDTOListar::new)
                .toList();

    }

}
