import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class NavigationScreen extends JFrame implements ActionListener {
    private JButton onlineButton, offlineButton;
    private JPanel navigationPanel;
    private GamePanel gamePanel;

    public NavigationScreen() {
        super("Navigation Screen");

        // create the navigation panel with the two buttons
        navigationPanel = new JPanel(new GridLayout(2, 1));
        onlineButton = new JButton("Online");
        offlineButton = new JButton("Offline");
        onlineButton.addActionListener(this);
        offlineButton.addActionListener(this);
        onlineButton.setPreferredSize(new Dimension(100, 50)); // set preferred size of the buttons
        offlineButton.setPreferredSize(new Dimension(100, 50));
        navigationPanel.add(onlineButton);
        navigationPanel.add(offlineButton);

        // create the game panel
        gamePanel = new GamePanel();
        gamePanel.setBackground(Color.BLACK);

        // set the background of the JFrame to an image
        setContentPane(new JPanel() {
            Image backgroundImage = new ImageIcon("Images/img.png").getImage();

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        });

        // add the navigation panel to the JFrame
        add(navigationPanel, BorderLayout.CENTER);
        setSize(Sizes.WINDOW_MAX_WIDTH, Sizes.WINDOW_MAX_HEIGHT);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == onlineButton) {
            // switch to online game mode
            navigationPanel.setVisible(false);
            add(gamePanel, BorderLayout.CENTER);
            gamePanel.run();
            revalidate();
        } else if (e.getSource() == offlineButton) {
            // switch to offline game mode
            navigationPanel.setVisible(false);
            add(gamePanel, BorderLayout.CENTER);
            revalidate();
        }
    }
}