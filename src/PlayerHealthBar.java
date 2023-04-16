import java.awt.*;

public class PlayerHealthBar implements Drawable{
    private int x, y;
    private int width, height;
    private int maxHealth;
    private int currentHealth;
    private Color barColor;
    private Color backgroundColor;

    public PlayerHealthBar() {
        this.x = 100;
        this.y = 35;
        this.width = 450;
        this.height = 50;
        this.maxHealth = 100;
        this.currentHealth = 100;
        this.barColor = Color.red;
        this.backgroundColor = Color.gray;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getCurrentHealth(){
        return this.currentHealth;
    }

    public void takeDamage(int damage){
        this.currentHealth -= damage;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(backgroundColor);
        g.fillRect(x, y, width, height);

        int barWidth = (int) (width * (double)currentHealth / maxHealth);
        g.setColor(barColor);
        g.fillRect(x, y, barWidth, height);
    }


}
