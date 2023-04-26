import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class Opponent extends Thread implements Drawable {

    private double x, y;
    private double height, width;
    public LinkedList<Bullet> bullets;
    GamePanel gamePanel;
    private double gravity = 0.5;

    private Image opponentImage;
    double velY;

    public Opponent(GamePanel gamePanel){
        this.gamePanel = gamePanel;
//        client = new Client();
        x = 850;
        y = Sizes.GAME_GROUND_LEVEL - 100;
        width = 75;
        height = 100;
        velY = 0;
        bullets = new LinkedList<>();
        this.opponentImage = new ImageIcon("Images/img_4.png").getImage();

    }
    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    public double getWidth() { return this.width; }
    public double getHeight() { return this.height; }
    public void jump() {
        this.velY = -10;
    }


    public void run(){
        while (true){
            this.update();
            gamePanel.repaint();
            this.sleep(20);
        }
    }
    private int [] position()
    {
        int[] arr = new int [2];
        arr[0] = (int)this.x;
        arr[1] = (int)this.y;
        return arr;
    }
    private void update()
    {
//        this.x -= 10;
        this.velY += this.gravity;
        this.y += this.velY;
        if (this.y >= Sizes.GAME_GROUND_LEVEL - this.height) {
            this.y = Sizes.GAME_GROUND_LEVEL - this.height;
            this.velY = 0;
        }
//        client.sendPosition(position());
//        gamePanel.myPlayer.updatePlayerPos(message);
        sleep(300);

    }
    private void encodeMessage(String message){
        switch(message){
            case("jump"):
                this.x=100; break;
            case("shot"):
//                String getPos = client.readData();
//                String[] parts = getPos.split(",");
//                int newX = Integer.parseInt(parts[0]);
//                int newY = Integer.parseInt(parts[1]);
                shootBullet(gamePanel.myPlayer.getX(), gamePanel.myPlayer.getY());
                break;

            case("left"):
                x-=10; break;
            case("right"):
                x+=10; break;
        }
    }

    public void shootBullet(double targetX, double targetY) {
        Bullet bullet = new Bullet(gamePanel, (int) x, (int)y, targetX, targetY, true);
        bullets.add(bullet);
        new Thread(bullet).start();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(opponentImage, (int)this.x, (int)this.y, (int)this.width, (int)this.height, this.gamePanel);
        for(Bullet bullet:bullets){
            bullet.draw(g);
        }
    }
    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
