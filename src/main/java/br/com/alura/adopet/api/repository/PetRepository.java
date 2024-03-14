package br.com.alura.adopet.api.repository;

import br.com.alura.adopet.api.model.abrigo.Abrigo;
import br.com.alura.adopet.api.model.pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByAbrigo(Abrigo abrigo);

    List<Pet> findAllByAdotadoFalse();

}
