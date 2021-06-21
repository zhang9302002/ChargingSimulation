import javax.swing.*;
import java.awt.*;

/**
 * @author Zhanghaoji
 * @date 2021.06.2021/6/21 22:22
 */

public class Place {

    private String id;

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

    public static void main(String[] args) {

    }
}
