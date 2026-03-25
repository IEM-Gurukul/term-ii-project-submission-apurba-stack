package domain;

public class Car extends Vehicle{
    public Car(int id,String model,double baseRate){
        super(id,model,baseRate);
    }
    @Override
    public double calculateCost(int days){
        return baseRate * days;
    }
}
