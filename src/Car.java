/**
 * Car is short for electric car
 * @author Zhanghaoji
 * @date 2021.06.2021/6/21 17:32
 *
 */
public class Car {

    private String id;

    private double capacity; // capacity of power

    private double power; // remaining power

    private double speed; // speed of driving

    private String homePlaceID;

    private String workPlaceID;

    private double[] currentPos = new double[2]; // (currentPos[0], currentPos[1]): (x,y) coordinates of car

    public Car(String id, double capacity, double speed, String hID, String wID) {
        this.id = id;
        this.capacity = capacity;
        this.power = capacity;
        this.speed = speed;
        this.homePlaceID = hID;
        this.workPlaceID = wID;
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

    public double[] getCurrentPos() {
        return currentPos;
    }

    public static void main(String[] args) {

    }
}
