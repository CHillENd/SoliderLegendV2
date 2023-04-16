import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game {
    public static void main(String[] args) {
        JFrame gameFrame = new JFrame("Legend Solider 3");
        JButton offlineButton = new JButton("Offline");
        JButton onlineButton = new JButton("Online");

//        GamePanel gamePanel = new GamePanel(false);
        Panel gamePanel = new Panel();
        offlineButton.setPreferredSize(new Dimension(200, 120));
        onlineButton.setPreferredSize(new Dimension(200, 120));

        offlineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                offlineClick();
            }
        });

        onlineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onlineClick();
            }
        });

        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(Sizes.WINDOW_MAX_WIDTH,Sizes.WINDOW_MAX_HEIGHT);
        gameFrame.setResizable(true);
        gameFrame.setVisible(true);

        gamePanel.add(offlineButton);
        gamePanel.add(onlineButton);
        gameFrame.add(gamePanel);
//        gamePanel.run();
//        MainFrame mainPanel = new MainFrame();
//        SwingUtilities.invokeLater(MainFrame::new);


    }
    public static void offlineClick(){
        System.out.println("Offline!");
    }
    public static void onlineClick(){
        System.out.println("Online!");
    }
}