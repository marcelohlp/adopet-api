package br.com.alura.adopet.api.repository;

import br.com.alura.adopet.api.model.abrigo.Abrigo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AbrigoRepository extends JpaRepository<Abrigo, Long> {
    boolean existsByNome(String nome);

    boolean existsByTelefone(String telefone);

    boolean existsByEmail(String email);

    Optional<Abrigo> findByNome(String nome);
}
