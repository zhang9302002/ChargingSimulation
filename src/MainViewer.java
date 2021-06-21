import fileReader.EFileDecoder;
import register.MapObjRegister;
import viewer.RegisterViewer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/**
 * @author Zhanghaoji
 * @date 2021.06.2021/6/21 20:54
 */
public class MainViewer extends JFrame {
    private HashMap<String, Place> placeMap = new HashMap<>();
    private HashMap<String, Car> carMap = new HashMap<>();
    private HashMap<String, Station> stationMap = new HashMap<>();

    private JPanel panelBase; // all
    private JPanel panel1; // left up
    private JPanel panel2; // right up
    private JPanel panel3; // left down
    private JPanel panel4; // right down
    private JTextArea textArea1; // left down
    private JTextArea textArea2; // right up
    private JTextArea textArea3; // right down
    private JButton playButton;
    private JButton pauseButton;
    private JButton endButton;

    private void loadDataToMap(MapObjRegister register) {
        /**
         * car data
         */
        HashMap<String, Object> hMap = register.getRegisteredDataObjs("Car");
        for(Object d: hMap.values()) {
            if(d == null)
                continue;
            HashMap<String, Object> dMap = (HashMap<String, Object>)d;
            Car car = new Car((String)dMap.get("id"), Double.parseDouble((String)dMap.get("capacity")), Double.parseDouble((String)dMap.get("speed")),
                    (String)dMap.get("homePlaceID"), (String)dMap.get("workPlaceID"));
            carMap.put(car.getId(), car);
        }
        /**
         * station data
         */
        hMap = register.getRegisteredDataObjs("Station");
        for(Object d: hMap.values()) {
            if(d == null)
                continue;
            HashMap<String, Object> dMap = (HashMap<String, Object>)d;
            Station sta = new Station((String)dMap.get("id"), Integer.parseInt((String)dMap.get("number")), Double.parseDouble((String)dMap.get("chargingSpeed")),
                    (String)dMap.get("placeID"));
            stationMap.put(sta.getId(), sta);
        }
        /**
         * place data
         */
        hMap = register.getRegisteredDataObjs("Place");
        for(Object d: hMap.values()) {
            if(d == null)
                continue;
            HashMap<String, Object> dMap = (HashMap<String, Object>)d;
            Place place = new Place((String)dMap.get("id"), (String)dMap.get("type"), Integer.parseInt((String)dMap.get("X-coordinate")), Integer.parseInt((String)dMap.get("Y-coordinate")));
            placeMap.put(place.getId(), place);
        }
    }

    private void setButton(MapObjRegister register) {
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = "充电站信息\n";
                str += "编号\t充电桩数量\t充电速度\t所在位置\n";
                for(int i = 1; stationMap.containsKey(String.valueOf(i)); ++i) {
                    Station sta = stationMap.get(String.valueOf(i));
                    Place p = placeMap.get(sta.getPlaceID());
                    str += String.format("%s\t%d\t%.2f\t(%.0f,%.0f)\n", sta.getId(), sta.getNumber(), sta.getChargingSpeed(), p.getX(), p.getY());
                }
                textArea1.setText(str);
            }
        });
    }

    public static void main(String[] args) {
        EFileDecoder decoder = new EFileDecoder();
        decoder.setConfigFile("lib" + File.separator + "NariEFormatReader.properties");
        decoder.setEPath(System.getProperty("user.dir"));
        decoder.setEFile("lib" + File.separator + "info.txt");
        try{
            decoder.decodeEfile();
            MapObjRegister register = decoder.getObjRegister();

            MainViewer frame = new MainViewer(register);
            frame.setContentPane(frame.panelBase);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setSize(1000, 800 + 28);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            Insets insets = frame.getInsets();// 得到窗口的边界区域。
            System.out.println("窗口边框上"+insets.top);//上
            System.out.println("窗口边框下"+insets.bottom);//下
            System.out.println("窗口边框左"+insets.left);//左
            System.out.println("窗口边框右"+insets.right);//右

            Dimension di = frame.getContentPane().getSize();//内容面板的大小
            System.out.println("内容面板宽度"+di.width);//宽
            System.out.println("内容面板的高度"+di.height);//高
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public MainViewer(MapObjRegister register) {
        loadDataToMap(register);
        /**
         * panelBase look like
         * -------------------
         * panel1   |   panel2
         * panel3   |   panel4
         * -------------------
         */
        panelBase = new JPanel();
        panelBase.setLayout(new GridBagLayout());

        /**
         * panel 1 at (0, 0)
         */
        panel1 = new DisplayPanel(placeMap);
        panel1.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panelBase.add(panel1, gbc);

        /**
         * panel 2 at (1, 0)
         */
        panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panelBase.add(panel2, gbc);

        final JScrollPane scrollPane1 = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel2.add(scrollPane1, gbc);

        textArea2 = new JTextArea();
        scrollPane1.setViewportView(textArea2);

        /**
         * panel 3 at (0, 1)
         */
        panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panelBase.add(panel3, gbc);

        final JToolBar toolBar1 = new JToolBar();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        panel3.add(toolBar1, gbc);

        playButton = new JButton();
        playButton.setText("开始模拟");
        toolBar1.add(playButton);

        pauseButton = new JButton();
        pauseButton.setText("暂停/继续");
        toolBar1.add(pauseButton);

        endButton = new JButton();
        endButton.setText("停止模拟");
        toolBar1.add(endButton);

        final JScrollPane scrollPane2 = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(scrollPane2, gbc);

        textArea1 = new JTextArea();
        scrollPane2.setViewportView(textArea1);

        /**
         * panel 4 at (1, 1)
         */
        panel4 = new JPanel();
        panel4.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panelBase.add(panel4, gbc);

        final JScrollPane scrollPane3 = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel4.add(scrollPane3, gbc);

        textArea3 = new JTextArea();
        textArea3.setText("");
        scrollPane3.setViewportView(textArea3);

        setButton(register);
    }


}

/**
 * DisplayPanel is the map of places
 */
class DisplayPanel extends JPanel {
    private HashMap<String, Place> placeMap;
    private final int offset = 100;

    public DisplayPanel(HashMap pm) {
        placeMap = pm;
        this.setSize(600, 600);
    }
    private void drawHome(Graphics2D g2d, double x, double y) {
        g2d.setColor(Color.GREEN);
        g2d.fillOval((int)(x*20 + offset), (int)(y*20), 50, 50);
    }
    private void drawIndu(Graphics2D g2d, double x, double y) {
        g2d.setColor(Color.BLACK);
        g2d.fillOval((int)(x*20 + offset), (int)(y*20), 50, 50);
    }
    private void drawBusi(Graphics2D g2d, double x, double y) {
        g2d.setColor(Color.RED);
        g2d.fillOval((int)(x*20 + offset), (int)(y*20), 50, 50);
    }
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 1. 绘制一个圆: 圆的外切矩形 左上角坐标为(0, 0), 宽高为100
        g2d.drawLine(25 + offset, 25, 15*20 + 25 + offset, 25);
        g2d.drawLine(25 + offset, 5*20 + 25, 15*20 + 25 + offset, 5*20 + 25);
        g2d.drawLine(25 + offset, 10*20 + 25, 15*20 + 25 + offset, 10*20 + 25);
        g2d.drawLine(25 + offset, 15*20 + 25, 15*20 + 25 + offset, 15*20 + 25);
        g2d.drawLine(25 + offset, 25, 25 + offset, 15*20 + 25);
        g2d.drawLine(5*20 + 25 + offset, 25, 5*20 + 25 + offset, 15*20 + 25);
        g2d.drawLine(10*20 + 25 + offset, 25, 10*20 + 25 + offset, 15*20 + 25);
        g2d.drawLine(15*20 + 25 + offset, 25, 15*20 + 25 + offset, 15*20 + 25);
        for(Place p: placeMap.values()) {
            switch (p.getType()) {
                case "living": drawHome(g2d, p.getX(), p.getY()); break;
                case "industrial": drawIndu(g2d, p.getX(), p.getY()); break;
                case "business": drawBusi(g2d, p.getX(), p.getY()); break;
                default:
            }
        }
        g2d.setColor(Color.WHITE);

        g2d.drawString("(0, 0)", 10 + offset, 30);
        g2d.drawString("(0, 5)", 10 + offset, 5*20 + 30);
        g2d.drawString("(0, 10)", 5 + offset, 10*20 + 30);
        g2d.drawString("(0, 15)", 5 + offset, 15*20 + 30);
        g2d.drawString("(5, 0)", 5*20 + 10 + offset, 30);
        g2d.drawString("(5, 5)", 5*20 + 10 + offset, 5*20 + 30);
        g2d.drawString("(5, 10)", 5*20 + 5 + offset, 10*20 + 30);
        g2d.drawString("(5, 15)", 5*20 + 5 + offset, 15*20 + 30);
        g2d.drawString("(10, 0)", 10*20 + 5 + offset, 30);
        g2d.drawString("(10, 5)", 10*20 + 5 + offset, 5*20 + 30);
        g2d.drawString("(10, 10)", 10*20 + 2 + offset, 10*20 + 30);
        g2d.drawString("(10, 15)", 10*20 + 2 + offset, 15*20 + 30);
        g2d.drawString("(15, 0)", 15*20 + 5 + offset, 30);
        g2d.drawString("(15, 5)", 15*20 + 5 + offset, 5*20 + 30);
        g2d.drawString("(15, 10)", 15*20 + 2 + offset, 10*20 + 30);
        g2d.drawString("(15, 15)", 15*20 + 2 + offset, 15*20 + 30);
        g2d.dispose();
        BufferedImage bufferedImage = (BufferedImage) this.createImage(this.getWidth(),this.getHeight());

        try {
            ImageIO.write(bufferedImage, "jpg", new File("test.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}