package stefanonitti.demo.payloads;


import stefanonitti.demo.enums.StatoViaggio;

import java.time.LocalDate;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ViaggioDTO(
        @NotBlank(message = "E se non mi dici dove vai che faccio?")
        @Size(min = 2, message = "Potevi scrivere anche 'la' e andava bene, 2 caratteri ti ho chiesto eh")
        String destinazione,

        @NotNull(message = "E quann o facimm stu viagg?")
        @FutureOrPresent(message = "La data del viaggio non può essere nel passato")
        LocalDate data,

        @NotNull(message = "Lo stato del viaggio è obbligatorio")
        StatoViaggio stato
) {}
