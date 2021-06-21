import javax.swing.*;
import java.awt.*;

/**
 * @author Zhanghaoji
 * @date 2021.06.2021/6/21 20:54
 */
public class MainViewer {
    private JPanel panelBase;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JTextArea textArea2;
    private JTextArea textArea3;
    private JTextArea textArea1;
    private JButton playButton;
    private JButton pauseButton;
    private JButton endButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainViewer");
        frame.setContentPane(new MainViewer().panelBase);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1000, 600 + 28);
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
    }

    public MainViewer() {
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
        panel1 = new JPanel();
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

    }
}
