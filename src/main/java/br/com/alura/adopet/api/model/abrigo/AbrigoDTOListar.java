package br.com.alura.adopet.api.model.abrigo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AbrigoDTOListar(
        Long id,
        String nome,
        String telefone,
        String email
) {

    public AbrigoDTOListar(Abrigo abrigo) {
        this(abrigo.getId(), abrigo.getNome(), abrigo.getTelefone(), abrigo.getEmail());
    }

}
