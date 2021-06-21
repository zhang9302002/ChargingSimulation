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

    private double consumeSpeed; // speed of consumption of electricity

    private double[] currentPos; // (currentPos[0], currentPos[1]): (x,y) coordinates of car

    public Car(String id, double capacity, double power, double speed, double consumeSpeed, double x, double y) {
        this.id = id;
        this.capacity = capacity;
        this.power = power;
        this.speed = speed;
        this.consumeSpeed = consumeSpeed;
        this.currentPos = new double[2];
        this.currentPos[0] = x;
        this.currentPos[1] = y;
    }

    public static void main(String[] args) {

    }
}
