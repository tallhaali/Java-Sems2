import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port;
    private int clientCount=0;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server running on port " + port + "...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientCount++;
                String IP=clientSocket.getInetAddress().getHostAddress();
                int clPort=clientSocket.getPort();
                System.out.println("Client " + clientCount + " connected from " +IP + ":" + clPort);

                Res clientHandler = new Res(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.println("Error in server: " + e.getMessage());
        }
    }
}
