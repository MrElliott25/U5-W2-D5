package stefanonitti.demo.payloads;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorsDTO (String message, LocalDateTime timestamp, List<String> errors){
    public ErrorsDTO {
        if (errors == null) {
            errors = List.of();
        }
    }

    public ErrorsDTO(String message, LocalDateTime timestamp) {
        this(message, timestamp, List.of());
    }
}

