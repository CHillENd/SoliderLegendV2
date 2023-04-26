import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener
{
    public Player myPlayer;
    public Player opponent;
    public LinkedList<Bullet> myPlayerBullets;
    public LinkedList<Bullet> opponentBullets;

    //    Opponent opponent;
    public List<Enemy> enemies;
    private Image backgroundImage;
    public Client client;
    private boolean isOffline;
//    private String id;

    public GamePanel(boolean isOffline) {
        setPanelSettings();

        myPlayer = new Player(this);
        opponent = new Player(this);
        myPlayerBullets = new LinkedList<>();
        opponentBullets = new LinkedList<>();

//        enemies = Collections.synchronizedList(new LinkedList<Enemy>());
        opponent.setX(myPlayer.getX() + 400);
        client = new Client();
        String id = client.getId();
        checkPlayerId(id);

        myPlayer.showHealthBar(true);
        opponent.showHealthBar(false);

        this.isOffline = isOffline;
    }

    public void start() {
        new Thread(this).start();

    }

    @Override
    public void run() {
        myPlayer.start();
//        if(isOffline){
//            addEnemies();
//            while (true) {
//
//                List<Bullet> bulletsCopy = new ArrayList<>(bullets);
//                for (Bullet bullet : bulletsCopy) {
//                    if (bullet.getX() >= Sizes.WINDOW_MAX_WIDTH || bullet.getX() < 0) {
//                        bullets.remove(bullet);
//                    }
//                }
//            }
//        }
//        else{
            opponent.start();

            while(true){
                client.sendPosition(myPlayer.position());
                String opponentMessage = client.readFromServer();
//                System.out.println(opponentMessage);

                String firstLetter = opponentMessage.substring(0, 1);
                switch (firstLetter){
                    case ("S"):
                        opponentIsShooting(opponentMessage.substring(2, opponentMessage.length() - 1));
                        break;
                    case("T"):
//                        System.out.println(opponentMessage);
                        System.out.println(Integer.parseInt(opponentMessage.substring(1)));
                        myPlayer.takeDamage(Integer.parseInt(opponentMessage.substring(1)));
                        break;
                    case("["):
                        setOpponentPosition(opponentMessage);
                        break;
                }


//                if(firstLetter.equals("S")) {
//                }

//                else{

//                }
            }
    }

    private void opponentIsShooting(String shootingTarget)
    {
        int [] target = convertStringToArray(shootingTarget);
        shootBullet(target[0], target[1], opponentBullets, opponent, false);
    }

    private void setOpponentPosition(String opponentPosition)
    {
        int [] arr = convertStringToArray(opponentPosition.substring(1, opponentPosition.length() - 1));
        opponent.setPosition(arr);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, Sizes.WINDOW_MAX_WIDTH, Sizes.WINDOW_MAX_HEIGHT, this);
        myPlayer.draw(g);
        opponent.draw(g);
//        for (Enemy enemy : enemies) {
//            enemy.draw(g);
//        }
        for (Bullet bullet : myPlayerBullets) {
            bullet.draw(g);
        }

        for (Bullet bullet : opponentBullets) {
            bullet.draw(g);
        }

    }


    public void shootBullet(double targetX, double targetY, LinkedList<Bullet> bullets, Player player, boolean isShotByPlayer) {
        Bullet bullet = new Bullet(this, (int) player.getX(), (int) player.getY(), targetX, targetY, isShotByPlayer);
        bullets.add(bullet);
        new Thread(bullet).start();
    }

//    public void shootOpponentBullet(double targetX, double targetY) {
//        Bullet bullet = new Bullet(this, (int) opponent.getX(), (int) opponent.getY(), targetX, targetY);
//        opponentBullets.add(bullet);
//        new Thread(bullet).start();
//    }

    private void addEnemies(){
        for(int i = 0; i < 7; i++) {
            Enemy enemy1 = new Enemy(this, i * 100 + 400);
            enemy1.start();
            enemies.add(enemy1);
        }

    }

    private void checkPlayerId(String id)
    {
        if(id.equals("P1")) return;
        Player tempPlayer = myPlayer;
        myPlayer = opponent;
        opponent = tempPlayer;
    }

    private void setPanelSettings()
    {
        backgroundImage = new ImageIcon("Images/img.png").getImage();
        this.setFocusable(true);        //move to setPanelSettings function
        this.requestFocusInWindow();
        this.addMouseListener(this);
        this.addKeyListener(this);

    }

    private int [] convertStringToArray(String strArray)
    {
        String[] arrStringParts = strArray.split(", ");
        int[] arr = new int[arrStringParts.length];

        for (int i = 0; i < arrStringParts.length; i++) {
            arr[i] = Integer.parseInt(arrStringParts[i]);
        }
        return arr;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
//        System.out.println("You Clicked: " + e.getKeyCode());
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
//            case(KeyEvent.VK_SPACE):
//                shootOpponentBullet(0,0);
        }
        //client.sendPosition(myPlayer.position());
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

        shootBullet(e.getX(), e.getY(), myPlayerBullets, myPlayer, true);
        int [] targetArray = {e.getX(), e.getY()};
        client.sendMessage("S"+Arrays.toString(targetArray));
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