import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;

public class ClientTest extends JFrame {
    private static final String HOST = "localhost";
    private static final int PORT = 9999;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private JTextArea chatArea;
    private JTextField inputField;

    public ClientTest() {
        // Set up the GUI
        setTitle("Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        inputField = new JTextField();
        inputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage(inputField.getText());
                inputField.setText("");
            }
        });
        add(inputField, BorderLayout.SOUTH);

        pack();
        setVisible(true);

        // Connect to the server
        try {
            socket = new Socket(HOST, PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected to server");

            // Send a "ready" message to the server
            out.writeObject("ready");
            out.flush();

            // Wait for messages from the server
            while (true) {
                String message = (String) in.readObject();
                chatArea.append("Server: " + message + "\n");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            out.writeObject(message);
            out.flush();
            chatArea.append("Client: " + message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendMessage(int[] position) {
        sendMessage(Arrays.toString(position));
    }

    public static void main(String[] args) {
        // Create a new client instance
        ClientTest client = new ClientTest();
    }
}

