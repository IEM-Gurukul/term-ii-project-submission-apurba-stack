package exception;

public class InvalidDurationException extends RentalException {

    public InvalidDurationException() {
        super("Invalid rental duration. Days must be greater than 0.");
    }
}
