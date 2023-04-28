import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 9999;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private int[] position;
    private String id;

    public Client() {
        try {
            socket = new Socket(HOST, PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            id = in.readObject().toString();
            System.out.println(id);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Please try again later", "SERVER IS DOWN", JOptionPane.INFORMATION_MESSAGE);
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String getId() {
        return this.id;
    }

    public synchronized void sendMessage(String message) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendPosition(int[] position) {
        String positionString = Arrays.toString(position);
        sendMessage(positionString);
    }

    public String readFromServer()
    {
        try {
            return in.readObject().toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}