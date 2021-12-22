package exceptions;

/**
 * @author Negin Mousavi
 */
public class UserInputValidation extends RuntimeException {
    private final String message;

    public UserInputValidation(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getLocalizedMessage() {
        return message;
    }
}
