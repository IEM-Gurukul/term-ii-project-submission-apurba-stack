package gui;

import domain.*;
import service.RentalManager;
import exception.*;

import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    
    private RentalManager manager = new RentalManager();

    private List<Customer> customers = new ArrayList<>();

    private List<Rental> rentals = new ArrayList<>();

    private JPanel mainPanel;
    private CardLayout cardLayout;

    private JTextArea outputArea;

    private JTextField fleetIdField, fleetModelField, fleetRateField;
    private JComboBox<String> fleetTypeBox;

    private JTextField custIdField, custNameField;

    private JTextField rentCustIdField, rentVehicleIdField, rentDaysField;
    
    private JTextArea statusArea;

    // ─────────────────────────────────────────────────────────────────────────
    public MainFrame() {

        // Add default vehicles at startup
        manager.addVehicle(new Car(1, "Toyota Camry", 50));
        manager.addVehicle(new Car(2, "Honda Civic", 45));
        manager.addVehicle(new Truck(3, "Volvo FH", 120));
        manager.addVehicle(new Truck(4, "Tata Prima", 100));
        manager.addVehicle(new Motorcycle(5, "Yamaha MT-15", 30));
        manager.addVehicle(new Motorcycle(6, "Royal Enfield", 35));

        // Basic window settings
        setTitle("Vehicle Rental System");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Add three sections to the window
        add(buildMenuPanel(), BorderLayout.WEST);
        add(buildCardPanel(), BorderLayout.CENTER);
        add(buildOutputPanel(), BorderLayout.SOUTH);
    }

    
    // LEFT — Menu buttons (4 options)
    
    private JPanel buildMenuPanel() {

        // 4 rows, 1 column
        JPanel panel = new JPanel(new GridLayout(7, 2, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Menu"));
        panel.setPreferredSize(new Dimension(150, 0));

        JButton btnFleet = new JButton("Edit Car Fleet");
        JButton btnCustomer = new JButton("Add Customer");
        JButton btnRent = new JButton("Rent a Car");
        JButton btnReturn = new JButton("Return Vehicle");
        JButton btnStatus = new JButton("Car Status & Rates");

        panel.add(btnFleet);
        panel.add(btnCustomer);
        panel.add(btnRent);
        panel.add(btnStatus);

        panel.add(btnReturn);
        // Each button shows its matching card
        btnFleet.addActionListener(e -> cardLayout.show(mainPanel, "FLEET"));
        btnCustomer.addActionListener(e -> cardLayout.show(mainPanel, "CUSTOMER"));
        btnRent.addActionListener(e -> cardLayout.show(mainPanel, "RENT"));
        btnStatus.addActionListener(e -> {
            refreshStatusPanel();
            cardLayout.show(mainPanel, "STATUS");
        });
        btnReturn.addActionListener(e -> cardLayout.show(mainPanel, "RETURN"));
        return panel;
    }

    
    // CENTER — CardLayout holds all 4 panels
    
    private JPanel buildCardPanel() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(buildFleetPanel(), "FLEET");
        mainPanel.add(buildCustomerPanel(), "CUSTOMER");
        mainPanel.add(buildRentPanel(), "RENT");
        mainPanel.add(buildStatusPanel(), "STATUS");
        mainPanel.add(buildReturnPanel(), "RETURN");

        return mainPanel;
    }

    
    // CARD 1 — Edit Car Fleet
    
    private JPanel buildFleetPanel() {

        JPanel panel = new JPanel(new GridLayout(6, 2, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Edit Car Fleet"));

        fleetIdField = new JTextField();
        fleetModelField = new JTextField();
        fleetRateField = new JTextField();
        fleetTypeBox = new JComboBox<>(new String[] { "Car", "Truck", "Motorcycle" });

        JButton addBtn = new JButton("Add Vehicle");
        JButton removeBtn = new JButton("Remove Vehicle");

        panel.add(new JLabel("Vehicle ID:"));
        panel.add(fleetIdField);
        panel.add(new JLabel("Model Name:"));
        panel.add(fleetModelField);
        panel.add(new JLabel("Base Rate (Rs/day):"));
        panel.add(fleetRateField);
        panel.add(new JLabel("Vehicle Type:"));
        panel.add(fleetTypeBox);
        panel.add(addBtn);
        panel.add(removeBtn);

        // Add vehicle button
        addBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(fleetIdField.getText().trim());
                String model = fleetModelField.getText().trim();
                double rate = Double.parseDouble(fleetRateField.getText().trim());
                String type = (String) fleetTypeBox.getSelectedItem();

                if (model.isEmpty()) {
                    outputArea.append("ERROR: Model name cannot be empty.\n");
                    return;
                }

                if (manager.findVehicleById(id) != null) {
                    outputArea.append("ERROR: Vehicle ID " + id + " already exists.\n");
                    return;
                }

                // Create the correct vehicle type
                Vehicle v;
                if (type.equals("Truck"))
                    v = new Truck(id, model, rate);
                else if (type.equals("Motorcycle"))
                    v = new Motorcycle(id, model, rate);
                else
                    v = new Car(id, model, rate);

                manager.addVehicle(v);
                outputArea.append("Added: [" + id + "] " + model + " (" + type + ") Rs." + rate + "/day\n");

                fleetIdField.setText("");
                fleetModelField.setText("");
                fleetRateField.setText("");

            } catch (NumberFormatException ex) {
                outputArea.append("ERROR: ID and Rate must be numbers.\n");
            }
        });

        // Remove vehicle button
        removeBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(fleetIdField.getText().trim());
                Vehicle v = manager.findVehicleById(id);
                if (v == null) {
                    outputArea.append("ERROR: Vehicle ID " + id + " not found.\n");
                } else {
                    manager.removeVehicle(id);
                    outputArea.append("Removed: [" + id + "] " + v.getModel() + "\n");
                }
            } catch (NumberFormatException ex) {
                outputArea.append("ERROR: Enter a valid Vehicle ID.\n");
            }
        });

        return panel;
    }

    
    // CARD 2 — Add New Customer
    
    private JPanel buildCustomerPanel() {

        JPanel panel = new JPanel(new GridLayout(4, 2, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Add New Customer"));

        custIdField = new JTextField();
        custNameField = new JTextField();
        JButton addBtn = new JButton("Add Customer");

        panel.add(new JLabel("Customer ID:"));
        panel.add(custIdField);
        panel.add(new JLabel("Customer Name:"));
        panel.add(custNameField);
        panel.add(new JLabel(""));
        panel.add(addBtn);

        addBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(custIdField.getText().trim());
                String name = custNameField.getText().trim();

                if (name.isEmpty()) {
                    outputArea.append("ERROR: Name cannot be empty.\n");
                    return;
                }

                // Check for duplicate customer ID
                for (Customer c : customers) {
                    if (c.getCustomerId() == id) {
                        outputArea.append("ERROR: Customer ID " + id + " already exists.\n");
                        return;
                    }
                }

                customers.add(new Customer(id, name));
                outputArea.append("Customer added: [" + id + "] " + name + "\n");

                custIdField.setText("");
                custNameField.setText("");

            } catch (NumberFormatException ex) {
                outputArea.append("ERROR: Customer ID must be a number.\n");
            }
        });

        return panel;
    }

    // CARD 3 — Rent a Car

private JPanel buildRentPanel() {

        JPanel panel = new JPanel(new GridLayout(5, 2, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Rent a Car"));

        rentCustIdField = new JTextField();
        rentVehicleIdField = new JTextField();
        rentDaysField = new JTextField();
        JButton rentBtn = new JButton("Confirm Rental");

        panel.add(new JLabel("Customer ID:"));
        panel.add(rentCustIdField);
        panel.add(new JLabel("Vehicle ID:"));
        panel.add(rentVehicleIdField);
        panel.add(new JLabel("Number of Days:"));
        panel.add(rentDaysField);
        panel.add(new JLabel(""));
        panel.add(rentBtn);

        rentBtn.addActionListener(e -> {
            try {
                int custId = Integer.parseInt(rentCustIdField.getText().trim());
                int vehicleId = Integer.parseInt(rentVehicleIdField.getText().trim());
                int days = Integer.parseInt(rentDaysField.getText().trim());

                // Search for the customer in the list
                Customer customer = null;
                for (Customer c : customers) {
                    if (c.getCustomerId() == custId) {
                        customer = c;
                        break;
                    }
                }

                if (customer == null) {
                    outputArea.append("ERROR: Customer ID " + custId + " not found. Add customer first.\n");
                    return;
                }

                // rentVehicle throws RentalException if something is wrong
                double cost = manager.rentVehicle(vehicleId, days);
                Vehicle vehicle = manager.findVehicleById(vehicleId);

                // Create and store the rental record
                Rental rental = new Rental(rentals.size() + 1, customer, vehicle, days, cost);
                rentals.add(rental);

                outputArea.append("Rental SUCCESS: " + customer.getName()
                        + " rented " + vehicle.getModel()
                        + " for " + days + " days. Total: Rs." + cost + "\n");

                rentCustIdField.setText("");
                rentVehicleIdField.setText("");
                rentDaysField.setText("");

            } catch (RentalException ex) {
                outputArea.append("ERROR: " + ex.getMessage() + "\n");
            } catch (NumberFormatException ex) {
                outputArea.append("ERROR: All fields must be numbers.\n");
            }
        });

        return panel;
    }

    private JPanel buildReturnPanel() {

        JPanel panel = new JPanel(new GridLayout(3, 2, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Return Vehicle"));

        JTextField returnIdField = new JTextField();
        JButton returnBtn = new JButton("Return");

        panel.add(new JLabel("Vehicle ID:"));
        panel.add(returnIdField);
        panel.add(new JLabel(""));
        panel.add(returnBtn);

        returnBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(returnIdField.getText().trim());

                manager.returnVehicle(id);

                outputArea.append("Vehicle ID " + id + " returned successfully\n");

                returnIdField.setText("");
                refreshStatusPanel();

            } catch (RentalException ex) {
                outputArea.append("ERROR: " + ex.getMessage() + "\n");
            } catch (NumberFormatException ex) {
                outputArea.append("ERROR: Enter a valid Vehicle ID.\n");
            }
        });

        return panel;
    }

    // CARD 4 — Car Status & Rate List (combined)

    private JPanel buildStatusPanel() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Car Status & Rate List"));

        statusArea = new JTextArea();
        statusArea.setEditable(false);
        statusArea.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> refreshStatusPanel());

        panel.add(new JScrollPane(statusArea), BorderLayout.CENTER);
        panel.add(refreshBtn, BorderLayout.SOUTH);

        return panel;
    }

    // Fills the combined status + rate table
    private void refreshStatusPanel() {
        if (statusArea == null)
            return;
        statusArea.setText("");
        statusArea.append(String.format("%-5s %-20s %-12s %-15s %-10s%n",
                "ID", "Model", "Type", "Rate(Rs/day)", "Status"));
        statusArea.append("------------------------------------------------------------\n");
        for (Vehicle v : manager.getAllVehicles()) {
            statusArea.append(String.format("%-5d %-20s %-12s %-15s %-10s%n",
                    v.getId(),
                    v.getModel(),
                    v.getClass().getSimpleName(),
                    "Rs." + (int) v.getBaseRate(),
                    v.isAvailable() ? "Available" : "Rented"));
        }
    }

    // BOTTOM — Output log
    
    private JPanel buildOutputPanel() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Output"));
        panel.setPreferredSize(new Dimension(0, 120));

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JButton clearBtn = new JButton("Clear");
        clearBtn.addActionListener(e -> outputArea.setText(""));

        JPanel btnPanel = new JPanel(new GridLayout(1, 1));
        btnPanel.add(clearBtn);

        panel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.EAST);

        return panel;
    }

  
    // ENTRY POINT
    
    public static void main(String[] args) {
        // SwingUtilities.invokeLater ensures GUI runs on the correct thread
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
