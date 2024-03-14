package br.com.alura.adopet.api.model.adocao;

import jakarta.validation.constraints.NotNull;

public record AdocaoDTOAprovacao(
        @NotNull Long idAdocao
) {
}
