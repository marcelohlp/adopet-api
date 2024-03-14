package br.com.alura.adopet.api.model.pet;

import br.com.alura.adopet.api.model.TipoPet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PetDTOListar(
        Long id,
        TipoPet tipo,
        String nome,
        String raca,
        Integer idade,
        String cor,
        Float peso
) {

    public PetDTOListar(Pet pet) {
        this(pet.getId(), pet.getTipo(), pet.getNome(), pet.getRaca(), pet.getIdade(), pet.getCor(), pet.getPeso());
    }

}
