package dungeonmania.exceptions;

/**
 * A RuntimeException to throw when an invalid action is attempted (See Section 7.2).
 */
@SuppressWarnings("serial")
public class InvalidActionException extends RuntimeException {
    public InvalidActionException(String message) {
        super(message);
    }
}
