package model;

/**
 * Place is the location of station, car or districts
 * @author Zhanghaoji
 * @date 2021.06.2021/6/21 22:22
 */
public class Place {

    private String id;

    /**
     * type can be
     * living  居住区
     * industrial  工业区
     * business  商业区
     * cur  移动中
     */
    private String type;

    private double X;

    private double Y;

    public Place(String id, String type, double x, double y) {
        this.id = id;
        this.type = type;
        X = x;
        Y = y;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public static double getDistanceOf(Place a, Place b) {
        double dis2 = (a.X - b.X) * (a.X - b.X) + (a.Y - b.Y) * (a.Y - b.Y);
        return Math.sqrt(dis2);
    }

    public static void main(String[] args) {

    }
}
