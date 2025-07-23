import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 9090);

        // Set up input/output streams
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Scanner clientInput = new Scanner(System.in);

        String message = "";
        System.out.println("Please say something to server (type 'quit' to exit):");

        // Send and receive loop
        while (!message.equalsIgnoreCase("quit")) {
            message = clientInput.nextLine();
            out.println(message); // send message to server

            String response = in.readLine(); // get response from server
            System.out.println("Server says: " + response);
        }

        socket.close();
    }
}
