package br.com.alura.adopet.api.repository;

import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.adocao.Adocao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdocaoRepository extends JpaRepository<Adocao, Long> {

    Boolean existsByPetIdAndStatus(Long idPet, StatusAdocao status);

    Boolean existsByTutorIdAndStatus(Long idTutor, StatusAdocao status);

    List<Adocao> findAllByTutorIdAndStatus(Long idTutor, StatusAdocao status);

}
