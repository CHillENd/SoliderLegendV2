import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;

public class Game {
    public static void main(String[] args) {
        JFrame gameFrame = new JFrame("Legend Solider 3");
        GamePanel gamePanel = new GamePanel(false);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(Sizes.WINDOW_MAX_WIDTH,Sizes.WINDOW_MAX_HEIGHT);
        gameFrame.setResizable(true);
        gameFrame.setVisible(true);
        gameFrame.add(gamePanel);
        gamePanel.run();
//------------------------------------------------------------------------------------------///////////////


    }

}
