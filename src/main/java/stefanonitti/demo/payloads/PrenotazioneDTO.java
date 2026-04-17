package stefanonitti.demo.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record PrenotazioneDTO(

        @NotNull(message = "Mi servirebbe l'id del dipendente...")
        UUID dipendenteId,

        @NotNull(message = "Eh mi serve anche l'id del viaggio...")
        Long viaggioId,

        @Size(max = 300, message = "Le note possono contenere al massimo 300 caratteri!")
        String note
) {}