import javax.swing.*;
import java.awt.*;

public class Enemy extends Thread implements Drawable {
    private double x, y;
    private double height, width;
    private double velX = 0;
    private int healthBar = 10;
    private GamePanel gamePanel;
//    private Rectangle hitbox;
    private Image enemyImage;
    public Enemy(GamePanel gamePanel, int xSpown) {
        this.gamePanel = gamePanel;
        this.x = xSpown;
        this.y = Sizes.GAME_GROUND_LEVEL - 100;
        this.width = 75;
        this.height = 100;

        this.enemyImage = new ImageIcon("Images/img_4.png").getImage();

//        this.hitbox = new Rectangle((int) playerXPos, (int) playerYPos, (int) playerWidth, (int) playerHeight);
    }

    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    public double getWidth() { return this.width; }
    public double getHeight() { return this.height; }

    @Override
    public void draw(Graphics g) {
        g.drawImage(enemyImage, (int)this.x, (int)this.y, (int)this.width, (int)this.height, this.gamePanel);

//        g.setColor(Color.RED);
//        g.drawRect((int) this.playerXPos, (int) this.playerYPos, (int) this.playerWidth, (int) this.playerHeight);
    }
    public Rectangle getRectangle() {
//        System.out.println(x +", "+ y +", "+ width +", "+ height);
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }


    public void run() {
        while (isEnemyAlive()) {
            this.update();
            gamePanel.repaint();
            this.sleep(20);
        }
        gamePanel.enemies.remove(this);
    }

    private void update() {     //runs after the player
        this.x += 2 * ((this.gamePanel.myPlayer.getX() >= this.x) ? 1 : -1) ;
        if(isCollidingWith(gamePanel.myPlayer)){
            gamePanel.myPlayer.takeDamage(1);
            sleep(500); //give some time to run away
        }

    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private boolean checkWindowCollision() {
        return (this.x + this.width + this.velX >= Sizes.WINDOW_MAX_WIDTH || this.x + this.velX <= 0);
    }

     private boolean isEnemyAlive(){
        return (this.healthBar > 0);
    }

    public void takeDamage(int damage){
        this.healthBar -= damage;
    }

    public boolean isShot(Bullet bullet){
        if (bullet!=null)
        {
            System.out.println("here");
            if (Math.abs(this.x - bullet.getX()) < 15  &&
                    Math.abs(this.y - bullet.getY()) < 15)
                return true;
        }
        return false;
    }

    public boolean isCollidingWith(Player player) {
        double left1 = x;              //replace with interacts with Rectangle
        double left2 = player.getX();
        double right1 = x + width;
        double right2 = player.getX() + player.getWidth();
        double top1 = y;
        double top2 = player.getY();
        double bottom1 = y + height;
        double bottom2 = player.getY() + player.getHeight();

        if (bottom1 < top2 || top1 > bottom2 || right1 < left2 || left1 > right2)
                return false;
        return true;
    }

}
