import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

public class Chat {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Scanner clientInput;
    
    public Chat(String host, int port) throws IOException {
    socket=new Socket(host,port);
    System.out.println("Connected to server at "+host+":"+port);
    out =new PrintWriter(socket.getOutputStream(),true);
    in =new BufferedReader(new InputStreamReader(socket.getInputStream()));
    clientInput=new Scanner(System.in);
    
}
public void start() throws IOException {
    String message = "";
    while (!message.equalsIgnoreCase("quit")) {
        // Get message from client (you)
        message = clientInput.nextLine();

        // Send message to server
        out.println(message);

        // Read server's response
        String response = in.readLine();
        System.out.println("Server: " + response);
    }
    socket.close();
}

}