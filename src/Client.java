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

    public Client() {
        try {
            socket = new Socket(HOST, PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected to server");

            // Request position from the server
            out.writeObject("getPosition");
            out.flush();

            // Wait for position from the server
            position = (int[]) in.readObject();
            System.out.println("Received position: " + Arrays.toString(position));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void sendPosition(int[] position) {
        try {
            out.writeObject(position);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
