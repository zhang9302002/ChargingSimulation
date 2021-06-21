/**
 * @author Zhanghaoji
 * @date 2021.06.2021/6/21 17:40
 */
public class ChargingStation {

    private String id;

    private int numOfChargingPoints; // amounts of charging points

    private double chargingSpeed; // speed of charging

    public ChargingStation(String id, int numOfChargingPoints, double chargingSpeed) {
        this.id = id;
        this.numOfChargingPoints = numOfChargingPoints;
        this.chargingSpeed = chargingSpeed;
    }

    public static void main(String[] args) {

    }
}
