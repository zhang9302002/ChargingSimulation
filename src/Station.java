/**
 * @author Zhanghaoji
 * @date 2021.06.2021/6/21 17:40
 */
public class Station {

    private String id;

    private int number; // amounts of charging points

    private double chargingSpeed; // speed of charging

    private String placeID;

    public Station(String id, int number, double chargingSpeed, String pID) {
        this.id = id;
        this.number = number;
        this.chargingSpeed = chargingSpeed;
        this.placeID = pID;
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

    public String getPlaceID() {
        return placeID;
    }

    public static void main(String[] args) {

    }
}
