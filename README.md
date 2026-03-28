[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/pG3gvzt-)
# PCCCS495 – Term II Project

## Project Title

Vehicle Rental Management System 

## Problem Statement (max 150 words)

This project addresses that need by developing a Vehicle Rental Management System, a 
desktop application that allows staff to manage a fleet of vehicles (cars, trucks, and 
motorcycles), register customers, process rentals, handle returns, and view live availability — 
all through a graphical user interface. 

## Target User

=> Rental Shop Staff 
=> Fleet Administrators 
=> Small Business Owners


## Core Features

* Fleet Management: Add, update, and categorize different vehicle types. 
* Dynamic Pricing: Automatic cost calculation based on vehicle type and rental duration. 
* Availability Tracking: Real-time status updates to prevent double-booking. 
* Transactional Safety: Custom exception handling for "Vehicle Not Found" or "Already Rented" 
scenarios. 

---

## OOP Concepts Used

- Abstraction: Vehicle is abstract; Car, Truck, Motorcycle extend it -RentalException is base; subclasses extend it 
- Inheritance: -Vehicle declares abstract calculateCost() — hides pricing details -RentalManager hides validation & state logic from the GUI 
- Polymorphism: CalculateCost() dispatched at runtime per vehicle type -RentalManager calls v.calculateCost(days) without knowing type
- Encapsulation : All Vehicle / Customer fields are private or protected -Access only through public getters and setters
- Exception Handling:  RentalException (base), AvailabilityException, InvalidDurationException, and VehicleNotFoundException form a typed exception hierarchy used throughout the service layer.
---

## Proposed Architecture Description

The system follows a Layered Architecture to ensure modularity and clean separation of 
concerns. 
I. Domain Layer (Entities) 
Contains the core business objects. The Vehicle abstract class serves as the root, ensuring all 
vehicles have an ID and a model, while allowing subclasses to define their own "Rental Logic." 
II. Service Layer (Logic) 
The RentalManager acts as the orchestrator. It contains the business rules: 
Checking if a customer is eligible. 
Verifying vehicle availability. 
---

## How to Run

Compile all .java files
Run GUIMain.java as the main class — it is inside the main package
The Vehicle Rental System window will open automatically

## Git Discipline Notes
Minimum 10 meaningful commits required.
