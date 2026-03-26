package service;

import domain.Vehicle;
import exception.*;

import java.util.ArrayList;
import java.util.List;

public class RentalManager {

    // Simple ArrayList to store all vehicles — no repository needed
    private List<Vehicle> vehicles = new ArrayList<>();

    // Add a vehicle to the list
    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    // Remove a vehicle by ID
    public void removeVehicle(int id) {
        vehicles.removeIf(v -> v.getId() == id);
    }

    // Find a vehicle by ID — returns null if not found
    public Vehicle findVehicleById(int id) {
        for (Vehicle v : vehicles) {
            if (v.getId() == id)
                return v;
        }
        return null;
    }

    // Get all vehicles
    public List<Vehicle> getAllVehicles() {
        return vehicles;
    }

    // Rent a vehicle — throws exception if invalid
    public double rentVehicle(int vehicleId, int days) throws RentalException {

        if (days <= 0)
            throw new InvalidDurationException();

        Vehicle vehicle = findVehicleById(vehicleId);

        if (vehicle == null)
            throw new VehicleNotFoundException();

        if (!vehicle.isAvailable())
            throw new AvailabilityException();

        vehicle.setAvailable(false);
        return vehicle.calculateCost(days);
    }

    public void returnVehicle(int vehicleId) throws RentalException {
        Vehicle v = findVehicleById(vehicleId);

        if (v == null)
            throw new VehicleNotFoundException();

        if (v.isAvailable())
            throw new RentalException("Vehicle is already available");

        v.setAvailable(true);
    }

}
