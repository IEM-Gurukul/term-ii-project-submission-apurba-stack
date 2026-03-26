package domain;

public class Rental {

    private int rentalId;
    private Customer customer;
    private Vehicle vehicle;
    private int days;
    private double totalCost;

    public Rental(int rentalId, Customer customer, Vehicle vehicle, int days, double totalCost) {
        this.rentalId = rentalId;
        this.customer = customer;
        this.vehicle = vehicle;
        this.days = days;
        this.totalCost = totalCost;
    }

    public int getRentalId() {
        return rentalId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public int getDays() {
        return days;
    }

    public double getTotalCost() {
        return totalCost;
    }
}
