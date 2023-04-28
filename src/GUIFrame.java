import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIFrame {
    private JFrame frame;
    private JPanel startPanel;
    private GamePanel gamePanel;

    public GUIFrame() {
        frame = new JFrame("Twins Wars");

        startPanel = new JPanel();
        startPanel.setLayout(null);
        gamePanel = new GamePanel();
        gamePanel.setLayout(null);

        JButton startOnlineButton = new JButton("Start Online Game");
        JButton startOfflineButton = new JButton("Start Offline Game");

        startOnlineButton.setBounds(350, Sizes.WINDOW_MAX_HEIGHT / 2 - 50, 200, 100);
        startOfflineButton.setBounds(800, Sizes.WINDOW_MAX_HEIGHT / 2 - 50, 200, 100);

        startOnlineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(gamePanel);
                frame.revalidate();
                gamePanel.setPanelSettings();
                gamePanel.start(false);
            }
        });

        startOfflineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(gamePanel);
                frame.revalidate();
                gamePanel.setPanelSettings();
                gamePanel.start(true);

            }
        });

        startPanel.add(startOnlineButton);
        startPanel.add(startOfflineButton);

        frame.setContentPane(startPanel);

        frame.setSize(Sizes.WINDOW_MAX_WIDTH, Sizes.WINDOW_MAX_HEIGHT);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    }