package model;

/**
 * Car is short for "Electric Vehicle"
 * @author Zhanghaoji
 * @date 2021.06.2021/6/21 17:32
 */
public class Car {

    private String id;

    private double capacity; // capacity of power, unit: km

    private double power; // remaining power, unit: km

    private double speed; // speed of driving, unit: km/h

    private String homePlaceID; // everyday needs to go home

    private String workPlaceID; // nearly everyday needs to go work

    private Place curPlace; // current place of the car

    public Car(String id, double capacity, double speed, String homePlaceID, String workPlaceID, Place curPlace) {
        this.id = id;
        this.capacity = capacity;
        this.power = capacity;
        this.speed = speed;
        this.homePlaceID = homePlaceID;
        this.workPlaceID = workPlaceID;
        this.curPlace = curPlace;
    }

    public String getId() {
        return id;
    }

    public double getCapacity() {
        return capacity;
    }

    public double getPower() {
        return power;
    }

    public double getSpeed() {
        return speed;
    }

    public String  getHomePlaceID() {
        return homePlaceID;
    }

    public String getWorkPlaceID() {
        return workPlaceID;
    }

    public Place getCurPlace() {
        return curPlace;
    }

    public static void main(String[] args) {

    }
}
