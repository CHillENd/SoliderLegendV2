import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private final Server server;
    private final Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private int[] position;

    public ClientHandler(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void send(int[] data) {
        try {
            out.writeObject(data);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Client connected: " + socket.getInetAddress());

            while (true) {
                String request = (String) in.readObject();
                if (request.equals("getPosition")) {
                    // Send position to the client
                    out.writeObject(position);
                    out.flush();
                } else {
                    // Receive position from the client
                    int[] newPosition = (int[]) in.readObject();
                    position = newPosition;
//                    server.broadcast(position);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            server.removeClient(this);
            System.out.println("Client disconnected: " + socket.getInetAddress());
        }
    }
}
