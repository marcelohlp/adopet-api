package br.com.alura.adopet.api.model.tutor;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record TutorDTOCadastrar(
        @NotBlank
        String nome,
        @NotBlank
        @Pattern(regexp = "\\(?\\d{2}\\)?\\d?\\d{4}-?\\d{4}")
        String telefone,
        @NotBlank
        @Email
        String email
) {
}
