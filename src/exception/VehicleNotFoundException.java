package exception;

public class VehicleNotFoundException extends RentalException {

    public VehicleNotFoundException() {
        super("Vehicle not found");
    }
}
