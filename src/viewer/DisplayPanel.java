package viewer;

import model.Place;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Display Panel is the viewer of places and roads
 * drawn by Graphics2D
 * static picture
 * @author Zhanghaoji
 * @date 2021.06.2021/6/21 20:54
 */
class DisplayPanel extends JPanel {
    private HashMap<String, Place> placeMap;
    private final int offset = 200;

    public DisplayPanel(HashMap pm) {
        placeMap = pm;
        this.setSize(600, 600);
    }

    private void drawHome(Graphics2D g2d, double x, double y) {
        g2d.setColor(Color.GREEN);
        g2d.fillOval((int) (x * 20 + offset), (int) (y * 20), 50, 50);
    }

    private void drawIndu(Graphics2D g2d, double x, double y) {
        g2d.setColor(Color.BLACK);
        g2d.fillOval((int) (x * 20 + offset), (int) (y * 20), 50, 50);
    }

    private void drawBusi(Graphics2D g2d, double x, double y) {
        g2d.setColor(Color.RED);
        g2d.fillOval((int) (x * 20 + offset), (int) (y * 20), 50, 50);
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 1. 绘制一个圆: 圆的外切矩形 左上角坐标为(0, 0), 宽高为100
        g2d.drawLine(25 + offset, 25, 15 * 20 + 25 + offset, 25);
        g2d.drawLine(25 + offset, 5 * 20 + 25, 15 * 20 + 25 + offset, 5 * 20 + 25);
        g2d.drawLine(25 + offset, 10 * 20 + 25, 15 * 20 + 25 + offset, 10 * 20 + 25);
        g2d.drawLine(25 + offset, 15 * 20 + 25, 15 * 20 + 25 + offset, 15 * 20 + 25);
        g2d.drawLine(25 + offset, 25, 25 + offset, 15 * 20 + 25);
        g2d.drawLine(5 * 20 + 25 + offset, 25, 5 * 20 + 25 + offset, 15 * 20 + 25);
        g2d.drawLine(10 * 20 + 25 + offset, 25, 10 * 20 + 25 + offset, 15 * 20 + 25);
        g2d.drawLine(15 * 20 + 25 + offset, 25, 15 * 20 + 25 + offset, 15 * 20 + 25);
        for (Place p : placeMap.values()) {
            switch (p.getType()) {
                case "living":
                    drawHome(g2d, p.getX(), p.getY());
                    break;
                case "industrial":
                    drawIndu(g2d, p.getX(), p.getY());
                    break;
                case "business":
                    drawBusi(g2d, p.getX(), p.getY());
                    break;
                default:
            }
        }
        g2d.setColor(Color.WHITE);

        g2d.drawString("(0, 0)", 10 + offset, 30);
        g2d.drawString("(0, 5)", 10 + offset, 5 * 20 + 30);
        g2d.drawString("(0, 10)", 5 + offset, 10 * 20 + 30);
        g2d.drawString("(0, 15)", 5 + offset, 15 * 20 + 30);
        g2d.drawString("(5, 0)", 5 * 20 + 10 + offset, 30);
        g2d.drawString("(5, 5)", 5 * 20 + 10 + offset, 5 * 20 + 30);
        g2d.drawString("(5, 10)", 5 * 20 + 5 + offset, 10 * 20 + 30);
        g2d.drawString("(5, 15)", 5 * 20 + 5 + offset, 15 * 20 + 30);
        g2d.drawString("(10, 0)", 10 * 20 + 5 + offset, 30);
        g2d.drawString("(10, 5)", 10 * 20 + 5 + offset, 5 * 20 + 30);
        g2d.drawString("(10, 10)", 10 * 20 + 2 + offset, 10 * 20 + 30);
        g2d.drawString("(10, 15)", 10 * 20 + 2 + offset, 15 * 20 + 30);
        g2d.drawString("(15, 0)", 15 * 20 + 5 + offset, 30);
        g2d.drawString("(15, 5)", 15 * 20 + 5 + offset, 5 * 20 + 30);
        g2d.drawString("(15, 10)", 15 * 20 + 2 + offset, 10 * 20 + 30);
        g2d.drawString("(15, 15)", 15 * 20 + 2 + offset, 15 * 20 + 30);
        g2d.dispose();
        BufferedImage bufferedImage = (BufferedImage) this.createImage(this.getWidth(), this.getHeight());

        try {
            ImageIO.write(bufferedImage, "jpg", new File("test.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
