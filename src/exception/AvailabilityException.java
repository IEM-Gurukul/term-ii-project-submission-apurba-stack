package exception;

public class AvailabilityException extends RentalException {

    public AvailabilityException() {
        super("Vehicle already rented");
    }
}
