package stefanonitti.demo.payloads;


import stefanonitti.demo.enums.StatoViaggio;

import java.time.LocalDate;

public record ViaggioDTO(
        String destinazione,
        LocalDate data,
        StatoViaggio stato
) {}
