import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        // Create server socket on port 9090
        ServerSocket serverSocket = new ServerSocket(9090);
        System.out.println("Server is running and waiting for client connection...");

        // Accept a client connection
        Socket clientSocket = serverSocket.accept();
        System.out.println("Kon bhonk raha hai?!");

        // Setup input and output streams
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        String message;

        // Loop to receive multiple messages until client sends "quit"
        while ((message = in.readLine()) != null) {
            System.out.println("Client says: " + message);

            // If client says "quit", break the loop
            if (message.equalsIgnoreCase("quit")) {
                out.println("Connection closing...");
                break;
            }

            // Respond to the client
            out.println("I received: " + message);
        }

        // Close connections
        clientSocket.close();
        serverSocket.close();
        System.out.println("Server stopped.");
    }
}
