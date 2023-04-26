import javax.swing.*;


public class Game {
    public static void main(String[] args) {
        JFrame gameFrame = new JFrame("Twins War");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(Sizes.WINDOW_MAX_WIDTH,Sizes.WINDOW_MAX_HEIGHT);
        gameFrame.setResizable(true);

//        gamePanel.setFocusable(true);
//        gameFrame.requestFocusInWindow();

        GamePanel gamePanel = new GamePanel(false);
        gameFrame.add(gamePanel);

        gameFrame.setVisible(true);
        gamePanel.start();
//------------------------------------------------------------------------------------------///////////////


    }

}
