package br.com.alura.adopet.api.model.adocao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdocaoDTOReprovacao(
        @NotNull Long idAdocao,
        @NotBlank String justificativa
) {
}
