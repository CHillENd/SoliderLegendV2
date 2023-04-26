import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Bullet implements Runnable{
    private double x, y;
    private int bulletDamage;
    private int width, height;
    private double valX, valY;
    private GamePanel gamePanel;
    private Image bulletImage;
    private boolean isCollidingWithPlayer;
    private boolean isCollidingWithOpponent;

    public Bullet(GamePanel gamePanel, double x, double y , double targetX, double targetY) {
        bulletDamage = 10;
        this.gamePanel = gamePanel;
        this.x = x;
        this.y = y;
        this.width = 25;
        this.height = 17;
        double deltaX = targetX - x;
        double deltaY = targetY - y;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        this.valX = deltaX / distance * 10;
        this.valY = deltaY / distance * 10;

        isCollidingWithPlayer = isCollidingWith(gamePanel.myPlayer);
        isCollidingWithOpponent = isCollidingWith(gamePanel.opponent);

        this.bulletImage = new ImageIcon("Images/img_3.png").getImage();
    }

    public void draw(Graphics g) {
        g.drawImage(bulletImage, (int)this.x, (int)this.y, (int)this.width, (int)this.height, this.gamePanel);

//        g.setColor(Color.BLACK);
//        g.fillRect((int)x, (int)y, this.width, this.height);
    }

    public Rectangle getRectangle() {
//        System.out.println(x+", "+y+", "+width+", "+ height);
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

    public double getX(){
        return x;
    }

    @Override
    public void run() {
        while (isCollidingWithOpponent || checkCollisions()){
//        while (true){
            update();
            gamePanel.repaint();
            this.sleep(10);
        }

    }
    public void update(){

        this.x += this.valX;
        this.y += this.valY;

//        isCollidingWithPlayer = isCollidingWith(gamePanel.myPlayer);
//        isCollidingWithOpponent = isCollidingWith(gamePanel.opponent);
    }

    private void sleep(int millis)      //add an interface
    {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private boolean checkWindowCollision(){
        return (!((this.y + this.height <= 0) || (this.y >= Sizes.WINDOW_MAX_HEIGHT) || (this.x + this.width <= 0) || (this.x >= Sizes.WINDOW_MAX_WIDTH)));
    }
    private boolean checkCollisions(){
        if(isCollidingWith(gamePanel.opponent)){
//            gamePanel.enemies.get(1).takeDamage(5);
            return false;
        }

        return (checkWindowCollision());
    }

    private boolean isCollidingWith(Player player){
        if(this.getRectangle().intersects(player.getRectangle())){
//            player.takeDamage(10);
            gamePanel.client.sendMessage("T" + bulletDamage);

            return true;
        }
        return false;
    }

    public boolean isCollidingWith(List<Enemy> enemies) {
        for(Enemy enemy : enemies) {
            if(this.getRectangle().intersects(enemy.getRectangle())){
                gamePanel.myPlayerBullets.remove(this);
                enemy.takeDamage(5);
                return true;
            }
        }
        return false;
    }

    public double getY()
    {return this.y;
    }
}
