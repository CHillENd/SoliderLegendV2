import javax.swing.*;
import java.util.Scanner;

public class TestClass {

    public static Scanner reader = new Scanner(System.in);

    public static void main(String[] args) {
//        Client client;
//        client = new Client();
        JFrame gameFrame = new JFrame("Legend Solider 3");

        GamePanel gamePanel = new GamePanel(true);

        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(Sizes.WINDOW_MAX_WIDTH,Sizes.WINDOW_MAX_HEIGHT);
        gameFrame.setResizable(true);
        gameFrame.setVisible(true);

        gameFrame.add(gamePanel);
        gamePanel.run();
    }

}


