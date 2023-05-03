import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class chooseLevelPanel extends JPanel
{
    private JButton easy;

    private JButton medium;
    private JButton hard;

    public chooseLevelPanel(GamePanel gamePanel, JFrame frame)
    {
        easy = new JButton("Easy Mode");
        medium = new JButton("Medium Mode");
        hard = new JButton("Hard Mode");

        easy.setPreferredSize(new Dimension(200, 100));
        medium.setPreferredSize(new Dimension(200, 100));
        hard.setPreferredSize(new Dimension(200, 100));

        easy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gamePanel.setDifficulty(1);
                frame.setContentPane(gamePanel);
                frame.revalidate();
                gamePanel.setPanelSettings();
                gamePanel.start(true);

            }
        });

        medium.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gamePanel.setDifficulty(2);
                frame.setContentPane(gamePanel);
                frame.revalidate();
                gamePanel.setPanelSettings();
                gamePanel.start(true);

            }
        });

        hard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gamePanel.setDifficulty(3);
                frame.setContentPane(gamePanel);
                frame.revalidate();
                gamePanel.setPanelSettings();
                gamePanel.start(true);

            }
        });
        setLayout(new FlowLayout(FlowLayout.CENTER, 50, 300));

        add(easy);
        add(medium);
        add(hard);
    }


}
