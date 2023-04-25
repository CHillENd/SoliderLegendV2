import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT = 9999;
    private ServerSocket serverSocket;
    private List<ObjectOutputStream> clientsOut;
    private List<ObjectInputStream> clientsIn;
    public String [] clientsNames = {"P1", "P2"};
    int clientNamesPointer = 0;

    public Server() {
        clientsOut = new ArrayList<>();
        clientsIn = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);

            // Wait for clients to connect
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostName());

                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                clientsOut.add(out);
                clientsIn.add(in);
                out.writeObject(clientsNames[clientNamesPointer]);
                clientNamesPointer++;
                // Start a new thread to handle the client
                ClientHandler handler = new ClientHandler(clientSocket, out);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcastMessage(String message, ObjectOutputStream sender) {
        for (ObjectOutputStream clientOut : clientsOut) {
            if (clientOut != sender) {
                try {
                    clientOut.writeObject(message);
                    clientOut.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class ClientHandler implements Runnable {
        private Socket clientSocket;
        private ObjectOutputStream out;

        public ClientHandler(Socket clientSocket, ObjectOutputStream out) {
            this.clientSocket = clientSocket;
            this.out = out;
        }

        public void run() {
            try {
                while (true) {
                    String message = (String) clientsIn.get(clientsOut.indexOf(out)).readObject();
                    System.out.println("Received message from client: " + message);
                    broadcastMessage(message, out);
                }
            } catch (IOException | ClassNotFoundException e) {
                // Client disconnected, remove from client list
                clientsOut.remove(out);
                clientsIn.remove(out);
                System.out.println("Client disconnected: " + clientSocket.getInetAddress().getHostName());
            }
        }
    }
}
