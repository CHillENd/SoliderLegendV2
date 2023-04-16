import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private JButton gameButton;

    public MainFrame() {
//        JFrame frame = new JFrame("GUITest");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        setSize(10, 10);
        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setBackground(Color.blue);
        mainPanel.setPreferredSize(new Dimension(300, 300));

        getContentPane().add(mainPanel);
        pack();
        setVisible(true);
    }
}
