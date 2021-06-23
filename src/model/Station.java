package model;

import java.util.Vector;

/**
 * Station is short for "Electric Vehicle Charging Station"
 * @author Zhanghaoji
 * @date 2021.06.2021/6/21 17:40
 */
public class Station {

    private String id;

    private int number; // amounts of charging points

    private double chargingSpeed; // speed of charging

    private Place place; // place of the station

    private Vector<Car> carInLine = new Vector<>(); // the waiting line of cars

    private final double eps = 1e-2;

    public Station(String id, int number, double chargingSpeed, Place p) {
        this.id = id;
        this.number = number;
        this.chargingSpeed = chargingSpeed;
        this.place = p;
    }

    public String getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public double getChargingSpeed() {
        return chargingSpeed;
    }

    public Place getPlace() {
        return place;
    }

    public int getLineSize() {
        return carInLine.size();
    }

    public boolean addACar(Car car) {
        if(Place.getDistanceOf(car.getCurPlace(), place) > eps)
            return false;
        carInLine.add(car);
        return true;
    }

    public static void main(String[] args) {

    }
}
