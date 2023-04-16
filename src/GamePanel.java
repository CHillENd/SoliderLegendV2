import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener
{
    public Player myPlayer;
    public LinkedList<Bullet> bullets;
    Opponent opponent;
    public List<Enemy> enemies;
    private Image backgroundImage;
    private Client client;
    private boolean isOffline;

    public GamePanel(boolean isOffline) {
        myPlayer = new Player(this);
        bullets = new LinkedList<>();
        backgroundImage = new ImageIcon("Images/img.png").getImage();

        enemies = Collections.synchronizedList(new LinkedList<Enemy>());
        this.setFocusable(true);        //move to setPanelSettings function
        this.requestFocus();
        this.addMouseListener(this);
        this.addKeyListener(this);

        client = new Client();
        if(!isOffline)
            opponent = new Opponent(this);
        this.isOffline = isOffline;
        new Thread(this).start();

    }

    @Override
    public void run() {
        myPlayer.start();
        if(isOffline){
            addEnemies();
            while (true) {

                List<Bullet> bulletsCopy = new ArrayList<>(bullets);
                for (Bullet bullet : bulletsCopy) {
                    if (bullet.getX() >= Sizes.WINDOW_MAX_WIDTH || bullet.getX() < 0) {
                        bullets.remove(bullet);
                    }
                }
            }
        }
        else{
            opponent.start();

            while(true){
//                opponent.client.writeData(myPlayer.position());

            }
        }


    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, Sizes.WINDOW_MAX_WIDTH, Sizes.WINDOW_MAX_HEIGHT, this);
        myPlayer.draw(g);
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
        if(!isOffline){
            opponent.draw(g);
        }


    }


    public void shootBullet(double targetX, double targetY) {
        Bullet bullet = new Bullet(this, (int) this.myPlayer.getX(), (int) this.myPlayer.getY(), targetX, targetY);
        bullets.add(bullet);
        new Thread(bullet).start();
    }

    private void addEnemies(){
        for(int i = 0; i < 7; i++) {
            Enemy enemy1 = new Enemy(this, i * 100 + 400);
            enemy1.start();
            enemies.add(enemy1);
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case (KeyEvent.VK_D):
                this.myPlayer.setAccX(0.1);
                break;
            case (KeyEvent.VK_A):
                this.myPlayer.setAccX(-0.1);
                break;
            case (KeyEvent.VK_W):
                if (e.isAltDown()) {
                    this.myPlayer.setAccX(-0.1);
                    this.myPlayer.setVelX(-5);
                }
                this.myPlayer.jump();
                break;

        }
        client.sendPosition(myPlayer.position());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A && e.isAltDown()) {
            this.myPlayer.setVelX(-5);
        } else {
            this.myPlayer.setAccX(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_D && e.isAltDown()) {
            this.myPlayer.setVelX(5);
        } else {
            this.myPlayer.setAccX(0);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        shootBullet(e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}