import java.io.*;
import java.net.Socket;

public class Res implements Runnable{
    private Socket clientSocket;

    public Res(Socket socket) {
        this.clientSocket = socket;
    }
    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Client says: " + message);

                if ("quit".equalsIgnoreCase(message)) {
                    out.println("Connection closing...");
                    break;
                }

                out.println("I received: " + message);
            }
        } catch (IOException e) {
            System.err.println("Error communicating with client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException ignored) {}
        }
    }
}
