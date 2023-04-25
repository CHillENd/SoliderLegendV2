import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;

//public class Client {
//    private static final String HOST = "localhost";
//    private static final int PORT = 9999;
//    private Socket socket;
//    private ObjectOutputStream out;
//    private ObjectInputStream in;
//    private int[] position;
//
//    public Client() {
//        try {
//            socket = new Socket(HOST, PORT);
//            out = new ObjectOutputStream(socket.getOutputStream());
//            in = new ObjectInputStream(socket.getInputStream());
//            System.out.println("Connected to server");
//
//            // Request position from the server
//            out.writeObject("getPosition");
//            out.flush();
//
//            // Wait for position from the server
//            position = (int[]) in.readObject();
//            System.out.println("Received position: " + Arrays.toString(position));
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void sendPosition(int[] position) {
//        try {
//            out.writeObject(position);
//            out.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
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
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
//        try {
//            System.out.println("Connected to server");
//
//            // Request position from the server
//            out.writeObject("Hello There");
//            out.flush();
//
//            // Wait for position from the server
//            position = (int[]) in.readObject();
//            System.out.println("Received position: " + Arrays.toString(position));
//        } catch (IOException \| ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    public String getId() {
        return this.id;
    }

    public void sendMessage(String message) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPosition(int[] position) {
        try {
            String positionString = Arrays.toString(position);
            out.writeObject(positionString);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
//
//    public static void main(String[] args) {
//        // Create a new client instance
//        Client client = new Client();
//
//        // Send a position to the server
//        int[] newPosition = {1, 2, 3}; // Replace with your desired position data
//        client.sendPosition(newPosition);
//
//        // Close the socket and streams
//        try {
//            client.out.close();
//            client.in.close();
//            client.socket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
