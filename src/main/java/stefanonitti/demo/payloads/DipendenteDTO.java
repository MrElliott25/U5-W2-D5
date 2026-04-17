package stefanonitti.demo.payloads;

import jakarta.validation.constraints.*;

public record DipendenteDTO(
        @NotBlank(message = "Username obbligatorio!")
        @Size(min = 3, max = 20, message = "Lo username deve essere tra 3 e 20 caratteri")
        String username,

        @NotBlank(message = "Inseriscimi un nome!")
        String nome,

        @NotBlank(message = "Non dirmi che non hai un cognome..")
        String cognome,

        @Email(message = "Inserire un indirizzo email valido!")
        @NotBlank(message = "Senza mail come ti avviso del viaggio?")
        String email
) {}
