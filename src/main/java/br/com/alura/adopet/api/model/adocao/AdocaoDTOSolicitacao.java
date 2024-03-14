package br.com.alura.adopet.api.model.adocao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdocaoDTOSolicitacao(
        @NotNull Long idPet,
        @NotNull Long idTutor,
        @NotBlank String motivo
) {
}
