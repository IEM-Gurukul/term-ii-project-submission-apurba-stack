package domain;

public class Motorcycle extends Vehicle {

    public Motorcycle(int id, String model, double baseRate) {
        super(id, model, baseRate);
    }

    @Override
    public double calculateCost(int days) {
        return baseRate * days * 0.9;
    }
}
