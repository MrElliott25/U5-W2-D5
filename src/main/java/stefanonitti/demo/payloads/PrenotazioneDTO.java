package stefanonitti.demo.payloads;

import java.util.UUID;

public record PrenotazioneDTO(
        UUID dipendenteId,
        Long viaggioId,
        String note
) {}
