package domain;

public class Truck extends Vehicle {

    public Truck(int id, String model, double baseRate) {
        super(id, model, baseRate);
    }

    @Override
    public double calculateCost(int days) {
        return (baseRate * days) + 100; 
    }
}
