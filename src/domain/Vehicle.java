package domain;

public abstract class Vehicle {

    protected int id;
    protected String model;
    protected double baseRate;
    protected boolean available;

    public Vehicle(int id, String model, double baseRate) {
        this.id = id;
        this.model = model;
        this.baseRate = baseRate;
        this.available = true;
    }

    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public double getBaseRate() {
        return baseRate;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean status) {
        this.available = status;
    }

    public abstract double calculateCost(int days);
}
