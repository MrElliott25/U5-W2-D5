package stefanonitti.demo.exceptions;

import java.util.List;

public class ValidationException extends RuntimeException {
    private List<String> errors;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(List<String> errors) {
        super("Lista degli errori di validazione: ");
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
